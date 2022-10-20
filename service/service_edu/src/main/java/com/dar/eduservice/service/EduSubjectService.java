package com.dar.eduservice.service;

import com.dar.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dar.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author wx
 * @since 2022-01-28
 */
public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
