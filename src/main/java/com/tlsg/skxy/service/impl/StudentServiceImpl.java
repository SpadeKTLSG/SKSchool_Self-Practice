package com.tlsg.skxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tlsg.skxy.dto.LoginForm;
import com.tlsg.skxy.mapper.StudentMapper;
import com.tlsg.skxy.pojo.Student;
import com.tlsg.skxy.service.StudentService;
import com.tlsg.skxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("studentService")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    //登录-查询学生
    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));


        return baseMapper.selectOne(queryWrapper);
    }

    //根据id查询学生
    @Override
    public Student getStudentById(Long userId) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        return baseMapper.selectOne(queryWrapper);
    }

    //分页查询学生
    @Override
    public IPage<Student> getStudentByOpr(Page<Student> pageParam, Student student) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(student.getName())) {
            studentQueryWrapper.like("name", student.getName());
        }

        if (!StringUtils.isEmpty(student.getClazzName())) {
            studentQueryWrapper.like("clazz_name", student.getClazzName());
        }

        studentQueryWrapper.orderByDesc("id");


        return baseMapper.selectPage(pageParam, studentQueryWrapper);
    }
}
