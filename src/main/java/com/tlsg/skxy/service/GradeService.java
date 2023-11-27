package com.tlsg.skxy.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tlsg.skxy.pojo.Grade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GradeService extends IService<Grade> {


    IPage<Grade> getGradeByOpr(Page<Grade> pageParam, String gradeName);

    List<Grade> getGrades();
}
