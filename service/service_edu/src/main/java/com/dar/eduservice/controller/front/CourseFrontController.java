package com.dar.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dar.commonutils.R;
import com.dar.commonutils.ordervo.CourseWebVoOrder;
import com.dar.commonutils.util.JwtUtils;
import com.dar.eduservice.client.OrderClient;
import com.dar.eduservice.entity.EduCourse;
import com.dar.eduservice.entity.vo.ChapterVo;
import com.dar.eduservice.entity.vo.CourseFrontVo;
import com.dar.eduservice.entity.vo.CourseQueryVo;
import com.dar.eduservice.service.EduChapterService;
import com.dar.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author :wx
 * @description :
 * @create :2022-10-12 14:13:00
 */
@RestController
@RequestMapping("/eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQueryVo courseQuery) {
        Page<EduCourse> pageParam = new Page<EduCourse>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageParam, courseQuery);
        return R.ok().data(map);
    }


    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "{courseId}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId, HttpServletRequest request) {
        //查询课程信息和讲师信息
        CourseFrontVo courseWebVo = courseService.selectInfoWebById(courseId);
        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.chapterList(courseId);
        //远程调用，判断课程是否被购买
        boolean buyCourse = orderClient.isBuyCourse(JwtUtils.getMemberIdByJwtToken(request), courseId);
        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList).data("isbuyCourse",buyCourse);
    }

    //根据课程id查询课程信息
    @GetMapping("getDto/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        CourseFrontVo courseInfo = courseService.selectInfoWebById(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
