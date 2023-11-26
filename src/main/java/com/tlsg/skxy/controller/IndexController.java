package com.tlsg.skxy.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Tag(name = "测试OPENAPI3.0", description = "Gold Experience")
//这里是另外一个任务: 上手Spring文档注解, 支持Swagger文档, 使用OpenAPI3.0规范
//暂时删除其他类的OpenApi注解, 以免冲突
public class IndexController {

    @GetMapping("/hello")
    @Operation(summary = "根据ID，查询用户",
            parameters = {
                    @Parameter(name = "id", required = true, in = ParameterIn.PATH) //@Parameter注解用于方法参数, 用于描述参数信息
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "成功", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "错误", content = @Content(mediaType = "application/json"))
            }
    )
    public void index(@Parameter(name = "name", description = "名称") String name) {

        System.out.println("hello world" + name);
    }

}