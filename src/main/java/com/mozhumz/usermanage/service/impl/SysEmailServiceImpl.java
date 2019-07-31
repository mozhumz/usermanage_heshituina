package com.mozhumz.usermanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyj.util.exception.BaseException;
import com.hyj.util.exception.ErrorInfo;
import com.hyj.util.param.CheckParamsUtil;
import com.hyj.util.web.JsonResponse;
import com.mozhumz.usermanage.enums.ErrorCode;
import com.mozhumz.usermanage.mapper.ISysEmailMapper;
import com.mozhumz.usermanage.model.entity.SysEmail;
import com.mozhumz.usermanage.model.qo.EmailQo;
import com.mozhumz.usermanage.service.ISysEmailService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 系统邮箱设置 服务实现类
 * </p>
 *
 * @author lshaci
 * @since 2019-07-24
 */
@Service
public class SysEmailServiceImpl extends ServiceImpl<ISysEmailMapper, SysEmail> implements ISysEmailService {
    @Resource
    private ISysEmailMapper sysEmailMapper;

    /**
     * 获取正常和禁用的列表
     * @param emailQo
     * @return
     */
    @Override
    public JsonResponse getList(EmailQo emailQo) {
        Page page=new Page(emailQo.getPage(),emailQo.getSize());
        Page<SysEmail>data=sysEmailMapper.findList(page,emailQo);
        return JsonResponse.success(data);
    }

    @Override
    @Transactional
    public JsonResponse addEmail(SysEmail sysEmail) {
        CheckParamsUtil.checkObj(sysEmail);

        sysEmail.setCreateDate(new Date());
        sysEmail.setUpdateDate(new Date());
        try {
            sysEmail.insert();

        }catch (DuplicateKeyException e){
            throw new BaseException(ErrorCode.EMAIL_EXIST_ERR.desc);
        }

        return JsonResponse.success(sysEmail);
    }


    @Override
    @Transactional
    public JsonResponse updateEmail(SysEmail sysEmail) {
        CheckParamsUtil.checkObj(sysEmail);
        if(sysEmail.getId()==null){
            throw new BaseException(ErrorInfo.PARAMS_ERROR.desc);
        }
        sysEmail.setUpdateDate(new Date());
        try {

            sysEmail.updateById();
        }catch (DuplicateKeyException e){
            throw new BaseException(ErrorCode.EMAIL_EXIST_ERR.desc);
        }

        return JsonResponse.success(null);
    }
}
