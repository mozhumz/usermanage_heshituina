package com.mozhumz.usermanage.service;

import com.mozhumz.usermanage.model.dto.AddUserDto;
import com.mozhumz.usermanage.model.dto.ChangePwdDto;
import com.mozhumz.usermanage.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
