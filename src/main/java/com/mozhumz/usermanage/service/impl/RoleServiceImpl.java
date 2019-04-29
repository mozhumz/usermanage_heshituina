package com.mozhumz.usermanage.service.impl;

import com.mozhumz.usermanage.model.entity.Role;
import com.mozhumz.usermanage.mapper.IRoleMapper;
import com.mozhumz.usermanage.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author lshaci
 * @since 2019-04-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<IRoleMapper, Role> implements IRoleService {

}
