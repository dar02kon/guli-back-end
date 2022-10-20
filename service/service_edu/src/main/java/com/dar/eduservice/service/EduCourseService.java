package com.dar.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dar.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dar.eduservice.entity.vo.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wx
 * @since 2022-04-03
 */
public interface EduCourseService extends IService<EduCourse> {

    // 添加课程基本信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据ID获取课程信息
     */
    CourseInfoVo getCourseInfoFormById(String id);

    /**
     * 修改课程信息
     */
    void updateCourseInfoById(CourseInfoVo courseInfoVo);

    /**
     * 根据ID获取课程发布信息
     *
     * @param id
     * @return
     */
    CoursePublishVo getCoursePublishVoById(String id);

    /**
     * 发布课程
     *
     * @param id
     * @return
     */
    boolean publishCourseById(String id);

    /**
     * 分页查询课程
     *
     * @param pageParam
     * @param courseQuery
     */
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    /**
     * 删除课程
     *
     * @param id
     * @return
     */
    boolean removeCourseById(String id);

    /**
     * 根据讲师id查询讲师所讲课程列表
     *
     * @param teacherId
     * @return
     */
    List<EduCourse> selectByTeacherId(String teacherId);

    /**
     * 课程列表
     *
     * @param pageParam
     * @param courseQuery
     * @return
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseQueryVo courseQuery);

    /**
     * 获取课程信息
     *
     * @param id
     * @return
     */
    CourseFrontVo selectInfoWebById(String id);

    /**
     * 更新课程浏览数
     *
     * @param id
     */
    void updatePageViewCount(String id);
}
