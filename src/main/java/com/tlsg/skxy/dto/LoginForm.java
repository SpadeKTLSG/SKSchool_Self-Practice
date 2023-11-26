package com.tlsg.skxy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;


@Data
@Tag(name = "LoginForm", description = "登录表单")
public class LoginForm {

    @Schema(name = "username", type = "String", description = "用户名", example = "admin")
    private String username;

    @Schema(name = "password", type = "String", description = "密码", example = "123456")
    private String password;

    @Schema(name = "verifiCode", type = "String", description = "验证码", example = "1234")
    private String verifiCode;

    @Schema(name = "userType", type = "Integer", description = "用户类型", example = "1")
    private Integer userType;

}
