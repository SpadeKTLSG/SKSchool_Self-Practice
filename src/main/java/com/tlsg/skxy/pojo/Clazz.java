package com.tlsg.skxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;


@Data
@TableName("tb_clazz")
@Tag(name = "Clazz", description = "班级")
public class Clazz {

    //班级信息

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(name = "id", type = "Integer", description = "班级Id", example = "1")
    private Integer id;             //班级Id

    @Schema(name = "name", type = "String", description = "班级名称", example = "软件工程1班")
    private String name;            //班级名称

    @Schema(name = "number", type = "String", description = "班级人数", example = "50")
    private String number;          //班级人数

    @Schema(name = "introducation", type = "String", description = "班级介绍", example = "软件工程1班")
    private String introducation;   //班级介绍


    //班主任信息
    @Schema(name = "headmaster", type = "String", description = "班主任姓名", example = "张三")
    private String headmaster;      //班主任姓名

    @Schema(name = "telephone", type = "String", description = "班主任电话", example = "18888888888")
    private String telephone;       //班主任电话

    @Schema(name = "email", type = "String", description = "班主任邮箱", example = "2421361985")
    private String email;           //班主任邮箱


    //所属年级
    @Schema(name = "gradeName", type = "String", description = "班级所属年级", example = "软件工程")
    private String gradeName;      //班级所属年级


}
