package com.tlsg.skxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tlsg.skxy.common.Result;
import com.tlsg.skxy.pojo.Teacher;
import com.tlsg.skxy.service.TeacherService;
import com.tlsg.skxy.util.MD5;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Teacher", description = "教师控制器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    //分页查询
    @Operation(summary = "分页带条件查询教师信息", description = "分页带条件查询教师信息", parameters = {
            @Parameter(name = "pageNo", description = "页码数", required = true),
            @Parameter(name = "pageSize", description = "页大小", required = true),
            @Parameter(name = "teacher", description = "查询的条件", required = true)
    })
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result<Object> getTeachers(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize, Teacher teacher) {
        Page<Teacher> paraParam = new Page<>(pageNo, pageSize);

        IPage<Teacher> page = teacherService.getTeachersByOpr(paraParam, teacher);

        return Result.ok(page);
    }


    //添加或修改
    @Operation(summary = "保存或修改教师", description = "保存或者修改教师信息", parameters = {@Parameter(name = "teacher", description = "要保存或修改的教师JSON", required = true)})
    @PostMapping("/saveOrUpdateTeacher")
    public Result<Object> saveOrUpdateTeacher(@RequestBody Teacher teacher) {

        // 如果是新增,要对密码进行加密
        if (teacher.getId() == null || teacher.getId() == 0) {
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }

        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }


    //删除
    @Operation(summary = "删除教师", description = "删除单个或者多个教师信息", parameters = {@Parameter(name = "ids", description = "要删除的教师编号的JSON数组", required = true)})
    @DeleteMapping("/deleteTeacher")
    public Result<Object> deleteTeacher(@RequestBody List<Integer> ids) {
        teacherService.removeByIds(ids);
        return Result.ok();
    }

}

