package com.mozhumz.usermanage.service.impl;

import com.hyj.util.param.CheckParamsUtil;
import com.mozhumz.usermanage.enums.ErrorCode;
import com.mozhumz.usermanage.mapper.IRoleMapper;
import com.mozhumz.usermanage.mapper.IUserRoleMapper;
import com.mozhumz.usermanage.model.dto.AddUserDto;
import com.mozhumz.usermanage.model.dto.ChangePwdDto;
import com.mozhumz.usermanage.model.dto.SessionUser;
import com.mozhumz.usermanage.model.entity.Role;
import com.mozhumz.usermanage.model.entity.User;
import com.mozhumz.usermanage.mapper.IUserMapper;
import com.mozhumz.usermanage.model.entity.UserRole;
import com.mozhumz.usermanage.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mozhumz.usermanage.utils.MD5Util;
import com.mozhumz.usermanage.utils.SessionUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.lshaci.framework.common.exception.BaseException;

import javax.annotation.Resource;
import java.util.Date;

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
    /**
     * 新增用户
     *
     * @return
     */
    @Override
    @Transactional
    public User addUser(AddUserDto addUserDto) {
        if(addUserDto==null||!CheckParamsUtil.check(addUserDto.getUsername())
                ||!CheckParamsUtil.checkList_boolean(addUserDto.getRoleIds())){
            throw new BaseException(ErrorCode.PARAM_ERR.desc);
        }

        //t_user
        User user=new User();
        BeanUtils.copyProperties(addUserDto,user);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setPassword(MD5Util.getDefaultPwd());
        userMapper.insert(user);
        //t_user_role
        for(Long id:addUserDto.getRoleIds()){
            Role role=roleMapper.selectById(id);
            if(role==null){
                throw new BaseException(ErrorCode.ROLE_ERR.desc);
            }
            UserRole userRole=new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(id);
            userRole.setCreateDate(new Date());
            userRole.setUpdateDate(new Date());
            userRoleMapper.insert(userRole);
        }

        return user.setPassword(null);
    }

    /**
     * 修改密码
     */
    @Override
    public void changePwd(ChangePwdDto changePwdDto) {
        if(changePwdDto==null){
            throw new BaseException(ErrorCode.PWD_ERR.desc);
        }
        String oldPwd=changePwdDto.getOldPwd();
        String newPwd=changePwdDto.getNewPwd();
        if(!CheckParamsUtil.check(oldPwd,newPwd)){
            throw new BaseException(ErrorCode.PWD_ERR.desc);
        }
        SessionUser sessionUser=SessionUtil.getLoginUser();
        User user=userMapper.selectById(sessionUser.getId());
        if(user==null){
            throw new BaseException(ErrorCode.USER_ERR.desc);
        }
        if(!MD5Util.checkPwd(oldPwd,user.getPassword())){
            throw new BaseException(ErrorCode.PWD_ERR.desc);
        }
        //修改密码
        user.setPassword(MD5Util.getPwd(newPwd));
        userMapper.updateById(user);

    }
}
