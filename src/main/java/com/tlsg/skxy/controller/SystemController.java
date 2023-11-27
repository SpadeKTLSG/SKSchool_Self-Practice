package com.tlsg.skxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tlsg.skxy.common.Result;
import com.tlsg.skxy.common.ResultCodeEnum;
import com.tlsg.skxy.dto.LoginForm;
import com.tlsg.skxy.pojo.Admin;
import com.tlsg.skxy.pojo.Student;
import com.tlsg.skxy.pojo.Teacher;
import com.tlsg.skxy.service.AdminService;
import com.tlsg.skxy.service.StudentService;
import com.tlsg.skxy.service.TeacherService;
import com.tlsg.skxy.util.CreateVerifiCodeImage;
import com.tlsg.skxy.util.JwtHelper;
import com.tlsg.skxy.util.MD5;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@Tag(name = "System", description = "系统控制器")
public class SystemController {

    //! README : 需要验证码, Postman不好调试,建议使用浏览器调试
    //TODO : 掌握Postman->Session控制内容...

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;


    //? 登录
    @Operation(summary = "登录", description = "登录接口", parameters = {
            @Parameter(name = "loginForm", description = "登录表单", required = true),
            @Parameter(name = "request", description = "请求", required = true)}
    )
    @PostMapping("/login")
    public Result<Object> login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        // 校验验证码
        HttpSession session = request.getSession(); // 获取session
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode();

        if ("".equals(sessionVerifiCode) || null == sessionVerifiCode) {
            return Result.fail().message("验证码失效,请刷新后重试");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(loginVerifiCode)) {
            return Result.fail().message("验证码有误,请小心输入后重试");
        }
        // 从session域中移除现有验证码
        session.removeAttribute("verifiCode");

        // 分用户类型进行校验
        Map<String, Object> map = new LinkedHashMap<>();    // 准备一个map用户存放响应的数据
        switch (loginForm.getUserType()) {
            case 1 -> {
                try {
                    Admin admin = adminService.login(loginForm);
                    if (null != admin) {
                        // 用户的类型和用户id转换成一个密文,以token的名称向客户端反馈
                        map.put("token", JwtHelper.createToken(admin.getId().longValue(), 1));
                    } else {
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    log.info("登录失败");
                    return Result.fail().message(e.getMessage());
                }
            }
            case 2 -> {
                try {
                    Student student = studentService.login(loginForm);
                    if (null != student) {
                        // 用户的类型和用户id转换成一个密文,以token的名称向客户端反馈
                        map.put("token", JwtHelper.createToken(student.getId().longValue(), 2));
                    } else {
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    log.info("登录失败");
                    return Result.fail().message(e.getMessage());
                }
            }
            case 3 -> {
                try {
                    Teacher teahcer = teacherService.login(loginForm);
                    if (null != teahcer) {
                        // 用户的类型和用户id转换成一个密文,以token的名称向客户端反馈
                        map.put("token", JwtHelper.createToken(teahcer.getId().longValue(), 3));
                    } else {
                        throw new RuntimeException("用户名或者密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    log.info("登录失败");
                    return Result.fail().message(e.getMessage());
                }
            }
        }
        return Result.fail().message("查无此用户");
    } //http://localhost:8080/login


    //? 获取验证码图片
    @Operation(summary = "获取验证码图片", description = "获取验证码图片接口",
            parameters = {
                    @Parameter(name = "request", description = "请求", required = true),
                    @Parameter(name = "response", description = "响应", required = true)}
    )
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        // 获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        // 获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        // 将验证码文本放入session域,为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode", verifiCode);
        // 将验证码图片响应给浏览器

        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            log.info("验证码图片获取失败");
        }
    }// http://localhost:8080/getVerifiCodeImage


    //? 修改密码
    @Operation(summary = "修改密码", description = "修改密码接口",
            parameters = {
                    @Parameter(name = "oldPwd", description = "旧密码", required = true),
                    @Parameter(name = "newPwd", description = "新密码", required = true),
                    @Parameter(name = "token", description = "token", required = true)}
    )
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result<Object> updatePwd(@RequestHeader("token") String token, @PathVariable("oldPwd") String oldPwd, @PathVariable("newPwd") String newPwd) { // 修改密码
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            // token过期
            return Result.fail().message("token失效,请重新登录后修改密码");
        }
        // 获取用户ID和用类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);

        switch (userType) {
            case 1 -> {
                QueryWrapper<Admin> queryWrapper1 = new QueryWrapper<>();
                if (userId != null) {
                    queryWrapper1.eq("id", userId.intValue());
                }
                queryWrapper1.eq("password", oldPwd);
                Admin admin = adminService.getOne(queryWrapper1);
                if (admin != null) {
                    // 修改
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                } else {
                    return Result.fail().message("原密码有误!");
                }
            }
            case 2 -> {
                QueryWrapper<Student> queryWrapper2 = new QueryWrapper<>();
                if (userId != null) {
                    queryWrapper2.eq("id", userId.intValue());
                }
                queryWrapper2.eq("password", oldPwd);
                Student student = studentService.getOne(queryWrapper2);
                if (student != null) {
                    // 修改
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                } else {
                    return Result.fail().message("原密码有误!");
                }
            }
            case 3 -> {
                QueryWrapper<Teacher> queryWrapper3 = new QueryWrapper<>();
                if (userId != null) {
                    queryWrapper3.eq("id", userId.intValue());
                }
                queryWrapper3.eq("password", oldPwd);
                Teacher teacher = teacherService.getOne(queryWrapper3);
                if (teacher != null) {
                    // 修改
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                } else {
                    return Result.fail().message("原密码有误!");
                }
            }
        }
        return Result.ok();
    }// http://localhost:8080/updatePwd/123456/123456


    //? 上传图片
    @Operation(summary = "上传图片", description = "上传图片接口",
            parameters = {
                    @Parameter(name = "multipartFile", description = "图片文件", required = true),
                    @Parameter(name = "request", description = "请求", required = true)}
    )
    @PostMapping("/headerImgUpload")
    public Result<Object> headerImgUpload(MultipartFile multipartFile, HttpServletRequest request) {

        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        if (multipartFile == null) {
            return Result.fail().message("上传失败");
        }
        String originalFilename = multipartFile.getOriginalFilename();
        int i = 0;
        if (originalFilename != null) {
            i = originalFilename.lastIndexOf(".");
        }
        String newFileName = null;
        if (originalFilename != null) {
            newFileName = uuid.concat(originalFilename.substring(i));
        }

        // 保存文件 将文件发送到第三方/独立的图片服务器上,
        String portraitPath = null;
        if (newFileName != null) {
            portraitPath = "C:/code/myzhxy/target/classes/public/upload/".concat(newFileName);
        }
        try {
            if (portraitPath != null) {
                multipartFile.transferTo(new File(portraitPath));
            }
        } catch (IOException e) {
            log.info("上传失败");
        }


        // 响应图片的路径
        String path = null;
        if (newFileName != null) {
            path = "upload/".concat(newFileName);
        }
        return Result.ok(path);
    }// http://localhost:8080/headerImgUpload


    //? 获取用户信息
    @Operation(summary = "获取用户信息", description = "通过token口令获取当前登录的用户信息",
            parameters = {
                    @Parameter(name = "token", description = "token", required = true)}
    )
    @GetMapping("/getInfo")
    public Result<Object> getInfoByToken(String token) {

        //判断token是否存在
        if (token == null || token.isEmpty()) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }

        boolean expiration = JwtHelper.isExpiration(token);

        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }

        //从token中解析出 用户id 和用户的类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);


        Map<String, Object> map = new LinkedHashMap<>();
        switch (userType) {
            case 1 -> {
                Admin admin = adminService.getAdminById(userId);
                map.put("userType", 1);
                map.put("user", admin);
            }
            case 2 -> {
                Student student = studentService.getStudentById(userId);
                map.put("userType", 2);
                map.put("user", student);
            }
            case 3 -> {
                Teacher teacher = teacherService.getByTeacherById(userId);
                map.put("userType", 3);
                map.put("user", teacher);
            }
        }
        return Result.ok(map);
    }// http://localhost:8080/getInfo


}
