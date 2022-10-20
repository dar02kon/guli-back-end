package com.dar.eduservice.mapper;

import com.dar.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dar.eduservice.entity.vo.CourseFrontVo;
import com.dar.eduservice.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author wx
 * @since 2022-04-03
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getCoursePublishVoById(String id);
    CourseFrontVo selectInfoWebById(String courseId);

}
