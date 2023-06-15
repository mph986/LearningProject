package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mph
 * @version 1.0
 * @date 2023/6/14 14:42
 * @description TODO
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {

    //子节点
    List<CourseCategoryTreeDto> childrenTreeNodes;
}
