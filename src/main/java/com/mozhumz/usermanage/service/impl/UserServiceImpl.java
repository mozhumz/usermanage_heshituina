package com.mozhumz.usermanage.service.impl;

import com.mozhumz.usermanage.model.entity.User;
import com.mozhumz.usermanage.mapper.IUserMapper;
import com.mozhumz.usermanage.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
