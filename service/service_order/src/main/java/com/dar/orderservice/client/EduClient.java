package com.dar.orderservice.client;

import com.dar.commonutils.ordervo.CourseWebVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author :wx
 * @description :
 * @create :2022-10-15 13:59:00
 */
@Component
@FeignClient("service-edu")
public interface EduClient {

    //根据课程id查询课程信息
    @GetMapping("/eduservice/coursefront/getDto/{courseId}")
    public CourseWebVoOrder getCourseInfoDto(@PathVariable("courseId") String courseId);

}
