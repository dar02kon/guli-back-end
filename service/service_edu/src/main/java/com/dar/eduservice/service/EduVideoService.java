package com.dar.eduservice.service;

import com.dar.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dar.eduservice.entity.form.VideoInfoForm;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author wx
 * @since 2022-04-03
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean getCountByChapterId(String chapterId);

    /**
     * 课时保存
     * @param videoInfoForm
     */
    void saveVideoInfo(VideoInfoForm videoInfoForm);

    /**
     * 根据ID查询课时
     * @param id
     * @return
     */
    VideoInfoForm getVideoInfoFormById(String id);

    /**
     * 更新课时
     * @param videoInfoForm
     */
    void updateVideoInfoById(VideoInfoForm videoInfoForm);

    /**
     * 删除课时
     * @param id
     * @return
     */
    boolean removeVideoById(String id);

    /**
     * 删除video记录
     * @param courseId
     * @return
     */
    boolean removeByCourseId(String courseId);
}
