package com.mozhumz.usermanage.model.dto;

import com.hyj.util.anno.IsNeed;
import com.mozhumz.usermanage.model.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javafx.collections.transformation.SortedList;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;

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
public class SaveUserDto {

    private static final long serialVersionUID = 1L;

    @IsNeed(flag = true)
    private Long id;

    @ApiModelProperty(value = "账号")
    @IsNeed(flag = true)
    private String username;

//    @ApiModelProperty(value = "密码")
//    private String password;
//
//    private String balancePwd;

    @ApiModelProperty(value = "拥有的角色id 对应t_role 如',1,2,3,'")
    private String roleIdStr;


    private Date updateDate;

    @ApiModelProperty(value = "1 正常 2禁用")
    private Integer state;

    @ApiModelProperty(value = "用户姓名")
    private String realName;

    @ApiModelProperty(value = "用户手机")
    private String phone;

    @IsNeed(flag = true)
    private List<Role> roleList;

    private Integer gender;

    private String email;

    public void setRoleList(List<Role> roleList) {
        if(!CollectionUtils.isEmpty(roleList)){
            this.roleList = roleList;
            this.roleList.sort(Comparator.comparing(role1 -> role1.getId()));
//            StringJoiner stringJoiner=new StringJoiner(",");
            roleIdStr=",";
            for(Role role:roleList){
                roleIdStr+=role.getId()+",";
            }
        }
    }

}
