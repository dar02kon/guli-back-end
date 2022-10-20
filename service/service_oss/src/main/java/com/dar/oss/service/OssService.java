package com.dar.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author :wx
 * @description :
 * @create :2022-01-28 15:32:00
 */
public interface OssService {
    //上传头像
    String uploadFileAvatar(MultipartFile file);
}
