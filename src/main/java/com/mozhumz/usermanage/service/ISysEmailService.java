package com.mozhumz.usermanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyj.util.web.JsonResponse;
import com.mozhumz.usermanage.model.entity.SysEmail;
import com.mozhumz.usermanage.model.qo.EmailQo;


/**
 * <p>
 * 系统邮箱设置 服务类
 * </p>
 *
 * @author lshaci
 * @since 2019-07-24
 */
public interface ISysEmailService extends IService<SysEmail> {
    JsonResponse getList(EmailQo emailQo);
    JsonResponse addEmail(SysEmail sysEmail);
    JsonResponse updateEmail(SysEmail sysEmail);

}
