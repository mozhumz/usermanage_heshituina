package com.mozhumz.usermanage.model.qo;

import lombok.Data;

import java.util.Date;

/**
 * @author huyuanjia
 * @date 2019/5/27 14:16
 */
@Data
public class BaseQo {
    private Integer size=20;
    private Integer page=1;
    private Date startDate;
    private Date endDate;

    public void setSize(Integer size) {
        if(size!=null&&size>0)
        this.size = size;
    }

    public void setPage(Integer page) {
        if(page!=null&&page>0)
        this.page = page;
    }
}
