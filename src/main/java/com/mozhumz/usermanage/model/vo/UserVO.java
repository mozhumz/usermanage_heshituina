package com.mozhumz.usermanage.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.mozhumz.usermanage.model.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author lshaci
 * @since 2019-04-29
 */
@Data
@ApiModel(value="User对象", description="用户表")
public class UserVO  {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "账号")
    private String username;

//    @ApiModelProperty(value = "密码")
//    private String password;
//
//    private String balancePwd;

    @ApiModelProperty(value = "拥有的角色id 对应t_role 如',1,2,3,'")
    private String roleIdStr;

    private Date createDate;

    private Date updateDate;

    @ApiModelProperty(value = "1 正常 2禁用")
    private Integer state;

    @ApiModelProperty(value = "用户姓名")
    private String realName;

    @ApiModelProperty(value = "用户手机")
    private String phone;

    private String roleNameStr;

    private List<Role> roleList;

    private Integer gender;

    @ApiModelProperty(value = "登录密码是否为初始密码：1是 2否")
    private Integer is0pwd;
    @ApiModelProperty(value = "操作密码是否为初始密码：1是 2否")
    private Integer is0bpwd;

    private String email;

}
