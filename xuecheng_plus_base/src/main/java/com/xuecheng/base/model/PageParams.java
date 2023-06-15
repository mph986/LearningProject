package com.xuecheng.base.model;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mph
 * @version 1.0
 * @date 2023/6/12  13:47
 * @description 分页参数
 */
@Data
public class PageParams {


    @ApiModelProperty("当前页码")
    private Long pageNo = 1L;

    @ApiModelProperty("每页记录数")
    private Long pageSize = 30L;

}
