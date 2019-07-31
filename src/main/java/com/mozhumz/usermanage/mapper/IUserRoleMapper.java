package com.mozhumz.usermanage.mapper;

import com.mozhumz.usermanage.model.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author lshaci
 * @since 2019-04-29
 */
public interface IUserRoleMapper extends BaseMapper<UserRole> {
    int addOne(UserRole userRole);

}
