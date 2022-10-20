package com.dar.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-10-01 16:50:00
 */
public interface VodService {

    /**
     * 上传视频
     * @param file
     * @return
     */
    String uploadVideo(MultipartFile file);

    /**
     * 删除视频
     * @param videoId
     */
    void removeVideo(String videoId);

    /**
     * 删除多个视频
     * @param videoIdList
     */
    void removeVideoList(List<String> videoIdList);
}
