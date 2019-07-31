package com.mozhumz.usermanage.model.dto;

import com.mozhumz.usermanage.model.entity.User;
import com.mozhumz.usermanage.model.entity.UserRole;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserRbtDto implements Serializable {
    private User user;
    private List<UserRole> roleList;
    /**
     * 1新增 2 修改
     */
    private Integer type;
}
