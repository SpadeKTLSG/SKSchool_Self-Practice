package com.tlsg.skxy.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;


@Data
@Tag(name = "LoginForm", description = "登录表单")
public class LoginForm {

    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;

}
