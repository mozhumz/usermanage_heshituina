package com.mozhumz.usermanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyj.util.web.JsonResponse;
import com.mozhumz.usermanage.model.dto.AddUserDto;
import com.mozhumz.usermanage.model.dto.ChangePwdDto;
import com.mozhumz.usermanage.model.dto.ResetPwdDto;
import com.mozhumz.usermanage.model.dto.SaveUserDto;
import com.mozhumz.usermanage.model.entity.ManageTest;
import com.mozhumz.usermanage.model.entity.User;
import com.mozhumz.usermanage.model.qo.UserQo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author lshaci
 * @since 2019-04-29
 */
public interface ITestService  {
    JsonResponse add();
}
