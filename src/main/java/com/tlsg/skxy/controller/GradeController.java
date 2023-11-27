package com.tlsg.skxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tlsg.skxy.common.Result;
import com.tlsg.skxy.pojo.Grade;
import com.tlsg.skxy.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Grade", description = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;


    //?查全部年级
    @Operation(summary = "查询所有年级", description = "查询所有年级信息", parameters = {})
    @GetMapping("/getGrades")
    public Result<Object> getGrades() {

        List<Grade> grades = gradeService.getGrades();
        return Result.ok(grades);
    }//http://localhost:8080/sms/gradeController/getGrades


    //?删除单个和多个年级
    @Operation(summary = "删除年级", description = "删除单个和多个年级", parameters =
    @Parameter(name = "ids", description = "要删除的多个年级的ID的JSON数组", required = true))
    @DeleteMapping("/deleteGrade")
    public Result<Object> deleteGrade(@RequestBody List<Integer> ids) {

        gradeService.removeByIds(ids);
        return Result.ok();
    }//http://localhost:8080/sms/gradeController/deleteGrade


    //?增加或修改年级
    @Operation(summary = "增加或修改年级", description = "增加或者修改年级信息", parameters =
    @Parameter(name = "grade", description = "JSON格式的年级信息", required = true))
    @PostMapping("/saveOrUpdateGrade")
    public Result<Object> saveOrUpdateGrade(@RequestBody Grade grade) {

        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }//http://localhost:8080/sms/gradeController/saveOrUpdateGrade


    //?年级名称模糊查询, 带分页
    @Operation(summary = "分页查年级", description = "分页带条件查询年级信息", parameters = {
            @Parameter(name = "pageNo", description = "当前页码", required = true),
            @Parameter(name = "pageSize", description = "每页显示条数", required = true),
            @Parameter(name = "gradeName", description = "年级名称", required = true)
    })
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result<Object> getGrades(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize, String gradeName) {
        // 分页 带条件查询
        Page<Grade> page = new Page<>(pageNo, pageSize);
        // 通过服务层
        IPage<Grade> pageRs = gradeService.getGradeByOpr(page, gradeName);

        return Result.ok(pageRs);
    }//http://localhost:8080/sms/gradeController/getGrades/1/5?gradeName=一


}
