package com.xuecheng.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author mph
 * @version 1.0
 * @date 2023/6/12 14:09
 * @description 分页查询结果，节后响应结果
 */
@Data
@AllArgsConstructor
public class PageResult<T> implements Serializable {

    //数据列表（各种数据：课程、师资等）
    private List<T> items;

    //每页记录数
    private long pageSize;

    //分页信息
    //总记录数
    private long counts;

    //当前页码
    private long page;



}
