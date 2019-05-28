package com.mozhumz.usermanage.service;

import com.mozhumz.usermanage.model.entity.App;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mozhumz.usermanage.model.qo.AppQo;
import top.lshaci.framework.web.model.JsonResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lshaci
 * @since 2019-05-28
 */
public interface IAppService extends IService<App> {
    JsonResponse getAppList(AppQo appQo);
}
