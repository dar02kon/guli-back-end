package com.dar.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.dar.commonutils.R;
import com.dar.vod.constant.ConstantPropertiesUtil;
import com.dar.vod.service.VideoService;
import com.dar.vod.util.AliyunVodSDKUtils;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-10-01 16:49:00
 */
@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class VodController {

    @Autowired
    private VideoService videoService;

    @GetMapping("get-play-auth/{videoId}")
    public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws Exception {
        //获取阿里云存储相关常量
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        //初始化
        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);
        //请求
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(videoId);
        //响应
        System.out.println(videoId);
        GetVideoPlayAuthResponse response = client.getAcsResponse(request);
        //得到播放凭证
        String playAuth = response.getPlayAuth();
        //返回结果
        return R.ok().message("获取凭证成功").data("playAuth", playAuth);
    }

    @PostMapping("uploadVideo")
    public R uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            String videoId = videoService.uploadVideo(file);
            return R.ok().message("视频上传成功").data("videoId", videoId);
        } catch (Exception e) {
            return R.error().message("视频上传失败");
        }

    }


    @DeleteMapping("{videoId}")
    public R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
                         @PathVariable String videoId) {
        try {
            videoService.removeVideo(videoId);
            return R.ok().message("视频删除成功");
        } catch (Exception e) {
            return R.error().message("视频删除失败");
        }
    }

    /**
     * 批量删除视频
     * @param videoIdList
     * @return
     */
    @DeleteMapping("delete-batch")
    public R removeVideoList(
            @ApiParam(name = "videoIdList", value = "云端视频id", required = true)
            @RequestParam("videoIdList") List<String> videoIdList) {
        videoService.removeVideoList(videoIdList);
        return R.ok().message("视频删除成功");
    }

}
