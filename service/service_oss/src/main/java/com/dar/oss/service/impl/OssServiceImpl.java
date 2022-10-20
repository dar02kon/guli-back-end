package com.dar.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.dar.oss.service.OssService;
import com.dar.oss.utils.ConstantPropertiesUtil;
import com.dar.servicebase.exceptionhandler.GuliException;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author :wx
 * @description :
 * @create :2022-01-28 15:32:00
 */
@Service
public class OssServiceImpl implements OssService {
    //上传头像
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        //获取阿里云存储相关常量
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        try {
            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)) {
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置oss实例的访问权限：公共读
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            //获取上传文件流
            InputStream inputStream = file.getInputStream();
            //获取文件名
            String fileName = file.getOriginalFilename();
            //构建日期路径：/2019/02/26/文件名
            // 文件名：uuid.扩展名
            //生成随机的uuid
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid + fileName;
            //按照日期分类
            String dateOath = new DateTime().toString("yyyy/MM/dd");
            fileName = dateOath + "/" + fileName;
            //文件上传至阿里云
            ossClient.putObject(bucketName, fileName, inputStream);
            // 关闭OSSClient。
            ossClient.shutdown();
            //获取url地址 https://comegulioss.oss-cn-beijing.aliyuncs.com/avatar.jpg
            return "https://" + bucketName + "." + endPoint + "/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
