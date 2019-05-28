package com.mozhumz.usermanage.model.qo;

import com.hyj.util.param.CheckParamsUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("应用查询")
public class AppQo extends BaseQo {

    @ApiModelProperty(value = "应用名称")
    private String keyword;
    @ApiModelProperty(value = "登录用户角色id")
    private Long roleId;


    public void setKeyword(String keyword) {
        if(CheckParamsUtil.check(keyword))
        this.keyword = "%"+keyword+"%";
    }
}
