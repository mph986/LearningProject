package com.xuecheng.content.model.dto;

import lombok.Data;

/**
 * @author mph
 * @version 1.0
 * @date 2023/6/12  14:01
 * @description 课程查询条件模型类，接收查询请求参数
 */
@Data
public class QueryCourseParamsDto {

    //审核状态
    private String auditStatus;
    //课程名称
    private String courseName;
    //发布状态
    private String publishStatus;
}
