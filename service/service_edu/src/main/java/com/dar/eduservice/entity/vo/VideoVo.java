package com.dar.eduservice.entity.vo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * @author :wx
 * @description :
 * @create :2022-09-29 16:01:00
 */
@ApiModel(value = "课时信息")
@Data
public class VideoVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private Boolean free;
    @ApiModelProperty(value = "云服务器上存储的视频文件名称")
    private String videoOriginalName;
    private String videoSourceId;
}
