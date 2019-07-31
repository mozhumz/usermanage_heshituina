package com.mozhumz.usermanage.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.hyj.util.anno.IsNeed;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 系统邮箱设置
 * </p>
 *
 * @author lshaci
 * @since 2019-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_email")
@ApiModel(value="SysEmail对象", description="系统邮箱设置")
public class SysEmail extends Model<SysEmail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "qq邮箱")
    @IsNeed(flag = true)
    private String email;

    @ApiModelProperty(value = "授权码")
    @IsNeed(flag = true)
    private String pwd;

    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField("createDate")
    private Date createDate;

    @TableField("updateDate")
    private Date updateDate;

    private Integer state;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysEmail sysEmail = (SysEmail) o;
        return getId().equals(sysEmail.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
