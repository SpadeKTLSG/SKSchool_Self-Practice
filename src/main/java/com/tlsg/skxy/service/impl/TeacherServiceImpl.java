package com.tlsg.skxy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tlsg.skxy.mapper.TeacherMapper;
import com.tlsg.skxy.dto.LoginForm;
import com.tlsg.skxy.pojo.Teacher;
import com.tlsg.skxy.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("teacherService")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Override
    public Teacher login(LoginForm loginForm) {
        return null;
    }

    @Override
    public Teacher getByTeacherById(Long userId) {
        return null;
    }

    @Override
    public IPage<Teacher> getTeachersByOpr(Page<Teacher> paraParam, Teacher teacher) {
        return null;
    }
}
