package com.dar.vod.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.dar.servicebase.exceptionhandler.GuliException;
import com.dar.vod.config.VodConfig;
import com.dar.vod.constant.UrlConstant;
import com.dar.vod.service.VodService;
import com.dar.vod.util.VodUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author :wx
 * @description :
 * @create :2022-10-01 16:50:00
 */
@Service
@Slf4j
public class VodServiceImpl implements VodService {


    private final CloseableHttpClient httpClient;
    @Autowired
    public VodServiceImpl(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public String uploadVideo(MultipartFile file) {
        HttpPost httpPost = new HttpPost(UrlConstant.UPLOAD_VIDEO);
        try {
            String originalFilename = file.getOriginalFilename();
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.addTextBody("writetoken", VodConfig.WRITE_TOKEN);
            String JSONRPC = "{\"title\": \""+ title+ "\", \"tag\":\"video\",\"desc\":\"课程视频\"}";
            entityBuilder.addTextBody("JSONRPC",JSONRPC);
            FileBody fileBody = new FileBody(Objects.requireNonNull(VodUtil.MultipartFileToFile(file)));
            entityBuilder.addPart("Filedata",fileBody);
            HttpEntity httpEntity = entityBuilder.build();
            httpPost.setEntity(httpEntity);
            return httpClient.execute(httpPost, new AbstractResponseHandler<String>() {
                @Override
                public String handleEntity(HttpEntity httpEntity1) throws IOException {
                    String json = EntityUtils.toString(httpEntity1, StandardCharsets.UTF_8);
                    JSONObject jsonObject = JSONObject.parseObject(json);
                    if(jsonObject.getString("error").equals("0")){
                        return jsonObject.getJSONArray("data").getJSONObject(0).getString("vid");
                    } else {
                        log.error("视频上传出错");
                        log.error("返回信息【{}】",json);
                        throw new GuliException(20001, "guli vod 服务上传失败");
                    }
                }
            });
        } catch (Exception e){
            throw new GuliException(20001, "guli vod 服务上传失败");
        } finally {
            httpPost.releaseConnection();
        }
    }

    @Override
    public void removeVideo(String videoId) {
        deleteVideos(videoId);
    }

    @Override
    public void removeVideoList(List<String> videoIdList) {
        StringBuilder vids= new StringBuilder();
        for (String s : videoIdList) {
            vids.append(",");
            vids.append(s);
        }
        deleteVideos(vids.toString().replaceFirst(",", ""));
    }

    public void deleteVideos(String vids){
        HttpPost httpPost = new HttpPost(UrlConstant.DELETE_VIDEO);
        try{
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            String userId = VodConfig.USER_ID;
            String ptime = String.valueOf(System.currentTimeMillis());
            multipartEntityBuilder.addTextBody("userId",userId);
            multipartEntityBuilder.addTextBody("ptime",ptime);
            Map<String, String> requestMap = new HashMap<>();
            requestMap.put("userId", userId);
            requestMap.put("ptime", ptime);
            requestMap.put("vids", vids);
            multipartEntityBuilder.addTextBody("sign",VodUtil.getSign(requestMap,VodConfig.SECRET_KEY));
            multipartEntityBuilder.addTextBody("vids",vids);

            httpPost.setEntity(multipartEntityBuilder.build());
            httpClient.execute(httpPost, new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                    String json = EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8);
                    JSONObject jsonObject = JSONObject.parseObject(json);
                    if (jsonObject.getString("code").equals("200")){
                        log.info("视频删除成功【{}】",vids);
                    } else {
                        throw new GuliException(20001, "guli vod 视频删除失败");
                    }
                    return null;
                }
            });
        } catch (Exception e){
            throw new GuliException(20001, "guli vod 视频删除失败");
        }
    }
}
