package com.tlsg.skxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;


@Data
@TableName("tb_student")
@Tag(name = "Student", description = "学生")
public class Student {

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(name = "id", type = "Integer", description = "学生Id", example = "1")
    private Integer id;

    @Schema(name = "sno", type = "String", description = "学生学号", example = "2018111111")
    private String sno;

    @Schema(name = "name", type = "String", description = "学生姓名", example = "张三")
    private String name;

    @Schema(name = "gender", type = "char", description = "学生性别", example = "男")
    private char gender = '男';//default

    @Schema(name = "password", type = "String", description = "学生密码", example = "123456")
    private String password;

    @Schema(name = "email", type = "String", description = "学生邮箱", example = "2421361985")
    private String email;

    @Schema(name = "telephone", type = "String", description = "学生电话", example = "18888888888")
    private String telephone;

    @Schema(name = "address", type = "String", description = "学生地址", example = "广东省广州市")
    private String address;

    @Schema(name = "introducation", type = "String", description = "学生介绍", example = "我是一个学生")
    private String introducation;

    @Schema(name = "portrait", type = "String", description = "学生头像", example = "http://localhost:8080/sms/studentController/getPortrait/1")
    private String portraitPath;//存储头像的项目路径

    @Schema(name = "portraitPath", type = "String", description = "学生头像路径", example = "http://localhost:8080/sms/studentController/getPortrait/1")
    private String clazzName;//班级名称
    
}
