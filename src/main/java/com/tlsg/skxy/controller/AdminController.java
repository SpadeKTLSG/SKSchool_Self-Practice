package com.tlsg.skxy.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tlsg.skxy.common.Result;
import com.tlsg.skxy.pojo.Admin;
import com.tlsg.skxy.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin", description = "管理员控制器")
@RestController // @Controller + @ResponseBody, 让每个方法都返回JSON格式的数据
@RequestMapping("/sms/adminController") //通用的一级路径
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Operation(summary = "分页查管理员", description = "分页带条件查询管理员信息", parameters = {
            @Parameter(name = "pageNo", description = "页码数", required = true),
            @Parameter(name = "pageSize", description = "页大小", required = true),
            @Parameter(name = "adminName", description = "管理员名字", required = false)
    })
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result<Object> getAllAdmin(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize, String adminName) {
        Page<Admin> pageParam = new Page<Admin>(pageNo, pageSize);

        IPage<Admin> iPage = adminService.getAdminsByOpr(pageParam, adminName);
        return Result.ok(iPage);
    }


    @Operation(summary = "增加或修改管理员", description = "增加或修改管理员信息", parameters = {
            @Parameter(name = "admin", description = "JSON格式的Admin对象", required = true)
    })
    @PostMapping("/saveOrUpdateAdmin")
    public Result<Object> saveOrUpdateAdmin(@RequestBody List<Integer> ids) {
        adminService.removeByIds(ids);
        return Result.ok();
    }


}
