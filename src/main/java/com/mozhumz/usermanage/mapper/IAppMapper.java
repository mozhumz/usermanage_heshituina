package com.mozhumz.usermanage.mapper;

import com.mozhumz.usermanage.model.entity.App;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mozhumz.usermanage.model.qo.AppQo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lshaci
 * @since 2019-05-28
 */
public interface IAppMapper extends BaseMapper<App> {
    List<App> findAppList(AppQo appQo);
}
