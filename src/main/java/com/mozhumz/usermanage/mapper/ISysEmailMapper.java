package com.mozhumz.usermanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mozhumz.usermanage.model.entity.SysEmail;
import com.mozhumz.usermanage.model.qo.EmailQo;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 系统邮箱设置 Mapper 接口
 * </p>
 *
 * @author lshaci
 * @since 2019-07-24
 */
public interface ISysEmailMapper extends BaseMapper<SysEmail> {
    Page<SysEmail> findList(Page page, @Param("qo")EmailQo emailQo);
}
