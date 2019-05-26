package com.mozhumz.usermanage.model.dto;

import lombok.Data;

@Data
public class ChangePwdDto {
    private String oldPwd;
    private String newPwd;
}
