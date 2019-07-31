package com.mozhumz.usermanage.model.dto;

import com.hyj.util.anno.IsNeed;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("重置密码")
public class ResetPwdDto {
    @IsNeed(flag = true)
    private Long userId;
    /**
     * 1 登录密码 2 扣款密码
     */
    @IsNeed(flag = true)
    private Integer type;
}
