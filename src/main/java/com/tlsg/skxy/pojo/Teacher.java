package com.tlsg.skxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;


@Data
@TableName("tb_teacher")
@Tag(name = "Teacher", description = "教师")
public class Teacher {

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(name = "id", type = "Integer", description = "教师Id", example = "1")
    private Integer id;

    @Schema(name = "tno", type = "String", description = "教师工号", example = "1001")
    private String tno;

    @Schema(name = "name", type = "String", description = "教师姓名", example = "张三")
    private String name;

    @Schema(name = "gender", type = "char", description = "教师性别", example = "男")
    private char gender;

    @Schema(name = "password", type = "String", description = "教师密码", example = "123456")
    private String password;

    @Schema(name = "email", type = "String", description = "教师邮箱", example = "2421361985")
    private String email;

    @Schema(name = "telephone", type = "String", description = "教师电话", example = "18888888888")
    private String telephone;

    @Schema(name = "address", type = "String", description = "教师地址", example = "广东省广州市")
    private String address;

    @Schema(name = "portrait", type = "String", description = "教师头像", example = "http://localhost:8080/sms/teacherController/getPortrait/1")
    private String clazzName;

    @Schema(name = "portraitPath", type = "String", description = "教师头像路径", example = "http://localhost:8080/sms/teacherController/getPortrait/1")
    private String portraitPath;//存储头像的项目路径

    @TableLogic
    @Schema(name = "isDeleted", type = "Integer", description = "逻辑删除", example = "0")
    private Integer isDeleted; //逻辑删除
}
