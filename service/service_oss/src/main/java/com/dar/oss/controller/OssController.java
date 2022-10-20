package com.dar.oss.controller;

import com.dar.commonutils.R;
import com.dar.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author :wx
 * @description :
 * @create :2022-01-28 15:31:00
 */
@Api(description="阿里云文件管理")
@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R uploadOssFile(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file){
        //返回图片路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().message("文件上传成功").data("url",url);
    }

}
