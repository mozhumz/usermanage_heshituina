package com.mozhumz.usermanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.util.exception.BaseException;
import com.hyj.util.param.CheckParamsUtil;
import com.hyj.util.web.GsonUtil;
import com.hyj.util.web.JsonResponse;
import com.mozhumz.usermanage.config.RabbitConfig;
import com.mozhumz.usermanage.config.RabbitProducer;
import com.mozhumz.usermanage.enums.ErrorCode;
import com.mozhumz.usermanage.enums.PwdEnum;
import com.mozhumz.usermanage.enums.SaveUserEnum;
import com.mozhumz.usermanage.mapper.IRoleMapper;
import com.mozhumz.usermanage.mapper.IUserRoleMapper;
import com.mozhumz.usermanage.model.dto.*;
import com.mozhumz.usermanage.model.entity.Role;
import com.mozhumz.usermanage.model.entity.User;
import com.mozhumz.usermanage.mapper.IUserMapper;
import com.mozhumz.usermanage.model.entity.UserRole;
import com.mozhumz.usermanage.model.qo.UserQo;
import com.mozhumz.usermanage.model.vo.UserVO;
import com.mozhumz.usermanage.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mozhumz.usermanage.utils.MD5Util;
import com.mozhumz.usermanage.utils.SessionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author lshaci
 * @since 2019-04-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, User> implements IUserService {

    @Resource
    private IUserMapper userMapper;
    @Resource
    private IRoleMapper roleMapper;
    @Resource
    private IUserRoleMapper userRoleMapper;
    @Resource
    private RabbitProducer rabbitProducer;

    /**
     * 新增用户
     *
     * @return
     */
    @Override
    @Transactional
    public User addUser(AddUserDto addUserDto) {
        if (addUserDto == null || !CheckParamsUtil.check(addUserDto.getUsername())
                || !CheckParamsUtil.checkList_boolean(addUserDto.getRoleIds())) {
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }

        //t_user
        User user = new User();
        BeanUtils.copyProperties(addUserDto, user);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setPassword(MD5Util.getDefaultPwd());
        user.setBalancePwd(MD5Util.getDefaultPwd());
        userMapper.insert(user);

        //t_user_role
        List<UserRole> roleList = new ArrayList<>();
        for (Long id : addUserDto.getRoleIds()) {
            Role role = roleMapper.selectById(id);
            if (role == null) {
                throw new BaseException(ErrorCode.ROLE_ERR.desc);
            }
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(id);
            userRole.setCreateDate(new Date());
            userRole.setUpdateDate(new Date());
            userRoleMapper.insert(userRole);
            roleList.add(userRole);
        }

        //将消息发送给各个应用，通知添加用户
        rbtSaveUser(user, roleList, SaveUserEnum.add);


        return user.setPassword(null);
    }

    private void rbtSaveUser(User user, List<UserRole> roleList, SaveUserEnum saveUserEnum) {
        UserRbtDto userRbtDto = new UserRbtDto();
        userRbtDto.setType(saveUserEnum.code);
        userRbtDto.setUser(user);
        userRbtDto.setRoleList(roleList);
        rabbitProducer.sendExchangeMsg(RabbitConfig.fanoutExchange, "", GsonUtil.gson.toJson(userRbtDto));
    }

    /**
     * 修改密码
     */
    @Override
    @Transactional
    public void changePwd(ChangePwdDto changePwdDto) {
        if (changePwdDto == null) {
            throw new BaseException(ErrorCode.PWD_ERR.desc);
        }
        CheckParamsUtil.checkObj(changePwdDto);
        User user;
        if(changePwdDto.getIsLogin()){
            SessionUser sessionUser = SessionUtil.getLoginUser();
            user = userMapper.selectById(sessionUser.getId());
            if (user == null) {
                throw new BaseException(ErrorCode.USERNAME_ERR.desc);
            }
            if(!changePwdDto.getEmail().equals(user.getEmail())){
                throw new BaseException(ErrorCode.USERNAME_ERR.desc);
            }
        }else {
            if(!CheckParamsUtil.check(changePwdDto.getUsername(),changePwdDto.getEmail())){
                throw new BaseException(ErrorCode.PARAM_ERR.desc);
            }
            QueryWrapper<User> queryWrapper=new QueryWrapper();
            queryWrapper.eq("username",changePwdDto.getUsername());
            user=userMapper.selectOne(queryWrapper);
            if(user==null||!changePwdDto.getEmail().equals(user.getEmail())){
                throw new BaseException(ErrorCode.USER_EMAIL_ERR.desc);
            }
        }
        //验证码校验
        String code = SessionUtil.getUserCode(user.getUsername());
        if(!changePwdDto.getCode().equals(code)){
            throw new BaseException(ErrorCode.EMAIL_CODE_ERR.desc);
        }
        String newPwd = changePwdDto.getNewPwd();
        if (!CheckParamsUtil.check( newPwd)) {
            throw new BaseException(ErrorCode.PWD_ERR.desc);
        }

        if(changePwdDto.getType()== PwdEnum.login.code){
            //修改登录密码
            user.setPassword(MD5Util.getPwd(newPwd));
            user.setIs0pwd(2);
        }else if(changePwdDto.getType()== PwdEnum.balance.code){
            //修改操作密码
            user.setBalancePwd(MD5Util.getPwd(newPwd));
            user.setIs0bpwd(2);
        }else {
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }

        user.setUpdateDate(new Date());
        userMapper.updateById(user);
        rbtSaveUser(user,null,SaveUserEnum.update);

    }


    /**
     * 重置密码
     * @param resetPwdDto
     */
    @Override
    @Transactional
    public void resetPwd(ResetPwdDto resetPwdDto) {
        CheckParamsUtil.checkObj(resetPwdDto);
        User user=userMapper.selectById(resetPwdDto.getUserId());
        if(user==null){
            throw new BaseException(ErrorCode.USER_NOT_EXIST_ERR.desc);
        }
        user.setUpdateDate(new Date());
        if(PwdEnum.login.code==resetPwdDto.getType()){
            user.setPassword(MD5Util.getDefaultPwd());
        }else {
            user.setBalancePwd(MD5Util.getDefaultPwd());
        }
        user.updateById();

        rbtSaveUser(user,null,SaveUserEnum.update);
    }

    @Override
    public JsonResponse getUserList(UserQo userQo) {
        Page page = new Page(userQo.getPage(), userQo.getSize());
        Page<UserVO> pageList = userMapper.findUserVOList(page, userQo);
        List<UserVO> list = pageList.getRecords();
        if (!CollectionUtils.isEmpty(pageList.getRecords())) {
            list.forEach(item -> {
                StringJoiner stringJoiner = new StringJoiner(",");
                item.getRoleList().forEach(roleVO -> {
                    stringJoiner.add(roleVO.getName());
                });
                item.setRoleNameStr(stringJoiner.toString());

            });
        }
        return JsonResponse.success(pageList);
    }

    @Override
    public JsonResponse getUserList2(UserQo userQo) {

        if(CheckParamsUtil.check(userQo.getRoleName())){
            QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("name",userQo.getRoleName());
            Role role=roleMapper.selectOne(queryWrapper);
            if(role!=null){
                userQo.setRoleIdStr("%,"+role.getId()+",%");
            }
        }
        return JsonResponse.success(userMapper.findUserVOList2(userQo));
    }

    @Override
    public JsonResponse getOne(UserQo userQo) {
        if(userQo.getId()==null){
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }
        UserVO user=userMapper.getOneVO(userQo.getId());
        if(user==null){
            throw new BaseException(ErrorCode.USER_NOT_EXIST_ERR.desc);
        }
        List<Role>roleList=user.getRoleList();
        if(!CollectionUtils.isEmpty(roleList)){
            StringJoiner stringJoiner=new StringJoiner(",");
            for(Role role:roleList){
                stringJoiner.add(role.getName());
            }
            user.setRoleNameStr(stringJoiner.toString());
        }
        return JsonResponse.success(user);
    }

    @Override
    @Transactional
    public JsonResponse saveUser(SaveUserDto saveUserDto) {
        CheckParamsUtil.checkObj(saveUserDto);

        User user=new User();
        BeanUtils.copyProperties(saveUserDto,user);
        //不能冻结自己
        if(saveUserDto.getState()!=null&&saveUserDto.getState()!=1){
            if(saveUserDto.getId().equals(SessionUtil.getLoginUser().getId())){
                throw new BaseException(ErrorCode.FREEZE_SELF_ERR.desc);
            }
        }
        user.setUpdateDate(new Date());
        userMapper.updateById(user);

        QueryWrapper<UserRole> queryWrapper=new QueryWrapper();
        queryWrapper.eq("userId",user.getId());
        userRoleMapper.delete(queryWrapper);

        List<UserRole> userRoleList=new ArrayList<>();
        for(Role role:saveUserDto.getRoleList()){
            UserRole userRole=new UserRole();
            userRole.setRoleId(role.getId());
            userRole.setUserId(user.getId());
            userRole.setCreateDate(new Date());
            userRole.setUpdateDate(new Date());
            userRoleMapper.insert(userRole);
            userRoleList.add(userRole);
        }

        //发消息通知其他应用同步修改
        rbtSaveUser(user, userRoleList, SaveUserEnum.update);

        return JsonResponse.success(null);
    }

}
