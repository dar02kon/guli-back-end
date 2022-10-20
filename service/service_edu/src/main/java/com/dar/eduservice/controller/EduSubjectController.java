package com.dar.eduservice.controller;


import com.dar.commonutils.R;
import com.dar.eduservice.entity.subject.OneSubject;
import com.dar.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author wx
 * @since 2022-01-28
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传文件，读取内容
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }

    //课程分类信息显示（树形）
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}

