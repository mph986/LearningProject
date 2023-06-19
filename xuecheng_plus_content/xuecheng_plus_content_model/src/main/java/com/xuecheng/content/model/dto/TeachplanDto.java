package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;

import java.util.List;

/**
 * @author mph
 * @version 1.0
 * @date 2023/6/18 13:58
 * @description 课程计划模型
 */

@Data
public class TeachplanDto extends Teachplan {

    //小章节列表
    private List<TeachplanDto> teachPlanTreeNodes;
    //与媒资关联的信息
    private TeachplanMedia teachplanMedia;
}
