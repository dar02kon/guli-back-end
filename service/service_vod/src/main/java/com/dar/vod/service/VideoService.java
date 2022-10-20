package com.dar.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-10-12 16:14:00
 */
public interface VideoService {
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
     * 批量删除视频
     * @param videoIdList
     */
    void removeVideoList(List<String> videoIdList);
}
