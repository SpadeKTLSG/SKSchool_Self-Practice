package com.tlsg.skxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tlsg.skxy.common.Result;
import com.tlsg.skxy.pojo.Student;
import com.tlsg.skxy.service.StudentService;
import com.tlsg.skxy.util.MD5;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Student", description = "学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;

    //?删除
    @Operation(summary = "删除学生", description = "删除单个或者多个学生信息", parameters = {@Parameter(name = "ids", description = "要删除的学生编号的JSON数组", required = true)})
    @DeleteMapping("/delStudentById")
    public Result<Object> delStudentById(@RequestBody List<Integer> ids) {
        studentService.removeByIds(ids);
        return Result.ok();
    }//http://localhost:8080/sms/studentController/delStudentById

    //?增加或修改
    @Operation(summary = "保存或者修改学生信息", description = "保存或者修改学生信息", parameters = {@Parameter(name = "student", description = "要保存或修改的学生JSON", required = true)})
    @PostMapping("/addOrUpdateStudent")
    public Result<Object> addOrUpdateStudent(@RequestBody Student student) {
        Integer id = student.getId();
        if (null == id || 0 == id) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }//http://localhost:8080/sms/studentController/addOrUpdateStudent

    //?分页查询
    @Operation(summary = "分页带条件查询学生信息", description = "分页带条件查询学生信息", parameters = {
            @Parameter(name = "pageNo", description = "页码数", required = true),
            @Parameter(name = "pageSize", description = "页大小", required = true),
            @Parameter(name = "student", description = "查询的条件", required = true)
    })
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result<Object> getStudentBuOpr(@PathVariable("pageNo") Integer pageNo, @PathVariable("pageSize") Integer pageSize, Student student) {
        //分页信息封装Page对象
        Page<Student> pageParam = new Page<>(pageNo, pageSize);
        // 进行查询
        IPage<Student> studentPage = studentService.getStudentByOpr(pageParam, student);

        return Result.ok(studentPage);
    }//http://localhost:8080/sms/studentController/getStudentByOpr/1/5

}
