package com.mozhumz.usermanage.service.impl;

import com.mozhumz.usermanage.model.entity.UserRole;
import com.mozhumz.usermanage.mapper.IUserRoleMapper;
import com.mozhumz.usermanage.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author lshaci
 * @since 2019-04-29
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<IUserRoleMapper, UserRole> implements IUserRoleService {

}
