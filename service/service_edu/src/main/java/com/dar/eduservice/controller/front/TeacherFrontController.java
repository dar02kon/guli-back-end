package com.dar.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dar.commonutils.R;
import com.dar.eduservice.entity.EduCourse;
import com.dar.eduservice.entity.EduTeacher;
import com.dar.eduservice.service.EduCourseService;
import com.dar.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author :wx
 * @description :
 * @create :2022-10-12 13:33:00
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
//@CrossOrigin
public class TeacherFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "分页讲师列表")
    @GetMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<EduTeacher> pageParam = new Page<EduTeacher>(page, limit);
        Map<String, Object> map = teacherService.pageListWeb(pageParam);
        return R.ok().data(map);
    }

    //2 讲师详情的功能
    @GetMapping("{teacherId}")
    public R getTeacherFrontInfo(@PathVariable String teacherId) {
        //1 根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = teacherService.getById(teacherId);
        //2 根据讲师id查询所讲课程
        List<EduCourse> courseList = courseService.selectByTeacherId(teacherId);
        return R.ok().data("teacher",eduTeacher).data("courseList",courseList);
    }

}
