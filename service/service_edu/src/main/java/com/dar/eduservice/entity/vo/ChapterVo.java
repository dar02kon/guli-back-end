package com.dar.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * @author :wx
 * @description :
 * @create :2022-09-29 16:00:00
 */
@ApiModel(value = "章节信息")
@Data
public class ChapterVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private List<VideoVo> children = new ArrayList<>();
}
