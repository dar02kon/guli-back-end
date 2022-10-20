package com.dar.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dar.commonutils.R;
import com.dar.eduservice.entity.EduCourse;
import com.dar.eduservice.entity.vo.CourseInfoVo;
import com.dar.eduservice.entity.vo.CoursePublishVo;
import com.dar.eduservice.entity.vo.CourseQuery;
import com.dar.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author wx
 * @since 2022-04-03
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService eduCourseService;

    //添加课程基本信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("course-info/{id}")
    public R getCourseInfo(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfoFormById(id);
        return R.ok().data("item", courseInfoVo);
    }

    @ApiOperation(value = "更新课程")
    @PutMapping("update-course-info/{id}")
    public R updateCourseInfoById(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoVo courseInfoForm,
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        eduCourseService.updateCourseInfoById(courseInfoForm);
        return R.ok().data("courseId", id);
    }


    @ApiOperation(value = "根据ID获取课程发布信息")
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishVoById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        CoursePublishVo courseInfoForm = eduCourseService.getCoursePublishVoById(id);
        return R.ok().data("item", courseInfoForm);
    }

    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publish-course/{id}")
    public R publishCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        eduCourseService.publishCourseById(id);
        return R.ok();
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    CourseQuery courseQuery) {
        Page<EduCourse> pageParam = new Page<>(page, limit);
        eduCourseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        boolean result = eduCourseService.removeCourseById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("删除失败");
        }
    }

}

