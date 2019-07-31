package com.mozhumz.usermanage.model.qo;

import com.hyj.util.param.CheckParamsUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmailQo extends BaseQo {

    @ApiModelProperty(value = "邮箱账号")
    private String keyword;

    private Integer state;

    public void setKeyword(String keyword) {
        if(CheckParamsUtil.check(keyword)){
            this.keyword ="%"+ keyword+"%";
        }
    }
}
