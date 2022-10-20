package com.dar.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dar.commonutils.R;
import com.dar.eduservice.entity.EduCourse;
import com.dar.eduservice.entity.EduTeacher;
import com.dar.eduservice.service.EduCourseService;
import com.dar.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-10-09 16:39:00
 */

@RestController
@RequestMapping("/eduservice/index")
//@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;
    //查询前8条热门课程，查询前4条名师

    @GetMapping
    public R index() {

        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduList = courseService.list(wrapper);

        //查询前4条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByDesc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);
        return R.ok().data("eduList", eduList).data("teacherList", teacherList);
    }

}
