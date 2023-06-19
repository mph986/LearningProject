package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * @author mph
 * @version 1.0
 * @date 2023/6/18 10:47
 * @description 修改课程模型
 */
@Data
@ApiModel(value = "EditCourseDto",description = "修改课程基本信息")
public class EditCourseDto extends AddCourseDto{

    @ApiModelProperty(value = "课程id", required = true)
    private Long id;
}
