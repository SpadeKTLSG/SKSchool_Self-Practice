package com.tlsg.skxy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController // @Controller + @ResponseBody, 让每个方法都返回JSON格式的数据
@Tag(name = "管理员Controller", description = "黄金体验")
@RequestMapping("/sms/adminController") //通用的一级路径
public class AdminController {


}
