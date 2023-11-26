package com.tlsg.skxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_admin") // 指定表名tb_admin -> Admin
@Tag(name = "Admin", description = "管理员")
public class Admin {

    @TableId(value = "id", type = IdType.AUTO) // 指定自增策略
    @Schema(name = "id", type = "Integer", description = "管理员id", example = "1")
    private Integer id;

    @Schema(name = "name", type = "String", description = "姓名", example = "张三")
    private String name;

    @Schema(name = "gender", type = "char", description = "性别", example = "男")
    private char gender;

    @Schema(name = "password", type = "String", description = "密码", example = "123456")
    private String password;

    @Schema(name = "email", type = "String", description = "邮箱", example = "2425398986@qq.com")
    private String email;

    @Schema(name = "telephone", type = "String", description = "电话", example = "18888888888")
    private String telephone;

    @Schema(name = "address", type = "String", description = "地址", example = "广东省广州市天河区")
    private String address;

    @Schema(name = "portraitPath", type = "String", description = "头像的图片路径", example = "http://localhost:8080/img/1.jpg")
    private String portraitPath;// 头像的图片路径
}
