package com.dar.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dar.eduservice.client.VodClient;
import com.dar.eduservice.entity.EduVideo;
import com.dar.eduservice.entity.form.VideoInfoForm;
import com.dar.eduservice.mapper.EduVideoMapper;
import com.dar.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dar.servicebase.exceptionhandler.GuliException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author wx
 * @since 2022-04-03
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public boolean getCountByChapterId(String chapterId) {
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("chapter_id", chapterId);
        Integer count = baseMapper.selectCount(queryWrapper);
        return null != count && count > 0;
    }

    @Override
    public void saveVideoInfo(VideoInfoForm videoInfoForm) {
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm, video);
        boolean result = this.save(video);
        if (!result) {
            throw new GuliException(20001, "课时信息保存失败");
        }
    }

    @Override
    public VideoInfoForm getVideoInfoFormById(String id) {
        //从video表中取数据
        EduVideo video = this.getById(id);
        if (video == null) {
            throw new GuliException(20001, "数据不存在");
        }
        //创建videoInfoForm对象
        VideoInfoForm videoInfoForm = new VideoInfoForm();
        BeanUtils.copyProperties(video, videoInfoForm);
        return videoInfoForm;
    }

    @Override
    public void updateVideoInfoById(VideoInfoForm videoInfoForm) {
        //保存课时基本信息
        EduVideo video = new EduVideo();
        BeanUtils.copyProperties(videoInfoForm, video);
        boolean result = this.updateById(video);
        if (!result) {
            throw new GuliException(20001, "课时信息保存失败");
        }
    }

    @Override
    public boolean removeVideoById(String id) {
        //查询云端视频id
        EduVideo video = baseMapper.selectById(id);
        String videoSourceId = video.getVideoSourceId();
        //删除视频资源
        if(!StringUtils.isEmpty(videoSourceId)){
            vodClient.removeVideo(videoSourceId);
        }
        int result = baseMapper.deleteById(id);
        return result > 0;
    }

    @Override
    public boolean removeByCourseId(String courseId) {

        //根据课程id查询所有视频列表
        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.select("video_source_id");
        List<EduVideo> videoList = baseMapper.selectList(queryWrapper);
        //得到所有视频列表的云端原始视频id
        List<String> videoSourceIdList = new ArrayList<>();
        for (EduVideo video : videoList) {
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoSourceIdList.add(videoSourceId);
            }
        }
        //调用vod服务删除远程视频
        if(videoSourceIdList.size() > 0){
            vodClient.removeVideoList(videoSourceIdList);
        }

        //删除video表示的记录
        QueryWrapper<EduVideo> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", courseId);
        int count = baseMapper.delete(queryWrapper);
        return count > 0;
    }
}
