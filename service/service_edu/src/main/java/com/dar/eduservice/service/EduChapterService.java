package com.dar.eduservice.service;

import com.dar.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dar.eduservice.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wx
 * @since 2022-04-03
 */
public interface EduChapterService extends IService<EduChapter> {
    List<ChapterVo> chapterList(String courseId);

    boolean removeChapterById(String id);

    /**
     * 删除chapter记录
     * @param courseId
     * @return
     */
    boolean removeByCourseId(String courseId);
}
