package com.mozhumz.usermanage.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

/**
 * <p>
 * 
 * </p>
 *
 * @author lshaci
 * @since 2019-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_app")
@ApiModel(value="App对象", description="")
public class App extends Model<App> {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String url;


    private String remark;

    @TableField("createDate")
    private Date createDate;

    @TableField("updateDate")
    private Date updateDate;

    @TableField(exist=false)
    private boolean status=false;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
