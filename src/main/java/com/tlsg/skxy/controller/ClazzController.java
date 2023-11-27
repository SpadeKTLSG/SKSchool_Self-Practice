package com.tlsg.skxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tlsg.skxy.common.Result;
import com.tlsg.skxy.pojo.Clazz;
import com.tlsg.skxy.service.ClazzService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Clazz", description = "班级控制器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    //?查全部班级信息
    //NOTE : 哎呀前面查全部和这个的接口名称不一样, Service的命名也有点乱呢...
    @Operation(summary = "查询所有班级", description = "查询所有班级信息", parameters = {})
    @GetMapping("/getClazzs")
    public Result<Object> getClazzs() {
        List<Clazz> clazzes = clazzService.getClazzs();
        return Result.ok(clazzes);
    }
    //http://localhost:8080/sms/clazzController/getClazzs

    //?删除单个和多个班级
    //
    @Operation(summary = "删除班级", description = "删除单个和多个班级", parameters = {
            @Parameter(name = "ids", description = "要删除的多个班级的ID的JSON数组", required = true)})
    @DeleteMapping("/deleteClazz")
    public Result<Object> deleteClazz(@RequestBody List<Integer> ids) {
        clazzService.removeByIds(ids);
        return Result.ok();
    }
    //http://localhost:8080/sms/clazzController/deleteClazz?ids=1


    //增加或者修改班级信息
    @Operation(summary = "增加或者修改班级", description = "增加或者修改班级信息", parameters = {
            @Parameter(name = "clazz", description = "JSON格式的班级信息", required = true)})
    @PostMapping("/saveOrUpdateClazz")
    public Result<Object> saveOrUpdateClazz(@RequestBody Clazz clazz) {
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }//http://localhost:8080/sms/clazzController/saveOrUpdateClazz


    //分页带条件查询班级信息
    @Operation(summary = "分页查班级", description = "分页带条件查询班级信息", parameters = {
            @Parameter(name = "pageNo", description = "当前页码", required = true),
            @Parameter(name = "pageSize", description = "每页显示条数", required = true),
            @Parameter(name = "clazz", description = "班级信息", required = true)
    })
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result<Object> getClazzByOpr(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize, Clazz clazz) {
        Page<Clazz> page = new Page<>(pageNo, pageSize);

        IPage<Clazz> iPage = clazzService.getClazzsByOpr(page, clazz);

        return Result.ok(iPage);
    }


}
