package com.tlsg.skxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

@Data
@TableName("tb_grade")
@Tag(name = "Grade", description = "年级")
public class Grade {

    //年级信息
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(name = "id", type = "Integer", description = "年级Id", example = "1")
    private Integer id;             //年级ID

    @Schema(name = "name", type = "String", description = "年级名称", example = "软件工程")
    private String name;            //年级名称

    @Schema(name = "introducation", type = "String", description = "年级介绍", example = "软件工程")
    private String introducation;   //年级介绍

    //年级主任信息

    @Schema(name = "manager", type = "String", description = "年级主任姓名", example = "张三")
    private String manager;         //年级主任姓名

    @Schema(name = "email", type = "String", description = "年级主任邮箱", example = "2421361985")
    private String email;           //年级主任邮箱

    @Schema(name = "telephone", type = "String", description = "年级主任电话", example = "18888888888")
    private String telephone;       //年级主任电话

}
