package com.mozhumz.usermanage.service.impl;

import com.hyj.util.web.JsonResponse;
import com.mozhumz.usermanage.model.entity.App;
import com.mozhumz.usermanage.mapper.IAppMapper;
import com.mozhumz.usermanage.model.qo.AppQo;
import com.mozhumz.usermanage.service.IAppService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mozhumz.usermanage.utils.SessionUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lshaci
 * @since 2019-05-28
 */
@Service
public class AppServiceImpl extends ServiceImpl<IAppMapper, App> implements IAppService {
    @Resource
    private IAppMapper appMapper;

    public JsonResponse  getAppList(AppQo appQo) {
//        appQo.setRoleId(SessionUtil.getLoginUser().getRole().getId());
        return JsonResponse .success(appMapper.findAppList(appQo));
    }
}
