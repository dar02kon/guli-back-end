package com.dar.cmsservice.controller;

import com.dar.cmsservice.entity.CrmBanner;
import com.dar.cmsservice.service.CrmBannerService;
import com.dar.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-10-09 16:28:00
 */

@RestController
@RequestMapping("/educms/bannerfront")
@Api(description = "网站首页Banner列表")
//@CrossOrigin //跨域
public class BannerApiController {
    @Autowired
    private CrmBannerService bannerService;

    //查询所有banner
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("bannerList",list);
    }
}
