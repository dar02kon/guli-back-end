package com.dar.eduservice.client;

import com.dar.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-10-08 16:57:00
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{
    @Override
    public R removeVideo(String videoId) {
        return R.error().message("time out");
    }

    @Override
    public R removeVideoList(List<String> videoIdList) {
        return R.error().message("time out");
    }
}
