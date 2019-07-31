package com.mozhumz.usermanage.service;

import com.hyj.util.web.JsonResponse;
import com.mozhumz.usermanage.model.dto.AddUserDto;
import com.mozhumz.usermanage.model.dto.ChangePwdDto;
import com.mozhumz.usermanage.model.dto.ResetPwdDto;
import com.mozhumz.usermanage.model.dto.SaveUserDto;
import com.mozhumz.usermanage.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mozhumz.usermanage.model.qo.UserQo;
import com.mozhumz.usermanage.model.vo.UserVO;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author lshaci
 * @since 2019-04-29
 */
public interface IUserService extends IService<User> {
    User addUser(AddUserDto user);
    void changePwd(ChangePwdDto changePwdDto);
    void resetPwd(ResetPwdDto resetPwdDto);

    JsonResponse getUserList(UserQo userQo);

    /**
     * 不分页
     * @param userQo
     * @return
     */
    JsonResponse getUserList2(UserQo userQo);
    JsonResponse getOne(UserQo userQo);
    JsonResponse saveUser(SaveUserDto saveUserDto);
}
