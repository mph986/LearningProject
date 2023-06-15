package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mph
 * @version 1.0
 * @date 2023/6/15 9:32
 * @description TODO
 */
@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {

        //递归查询分类信息
        List<CourseCategoryTreeDto> courseCategoryTreeDtos =
                courseCategoryMapper.selectTreeNodes(id);
        //找到每个节点的子节点，封装成List<CourseCategoryTreeDto>

        //先将List转成Map，方便获取节点id。key：节点id  value：courseCategoryTreeDto对象
        Map<String, CourseCategoryTreeDto> mapTemp =
                courseCategoryTreeDtos
                        .stream().filter(item -> !id.equals(item.getId()))
                        .collect(Collectors.toMap(
                                CourseCategory::getId,
                                value -> value, (key1,
                                                 key2) -> key2));
        //定义一个List作为最终返回的List
        List<CourseCategoryTreeDto> courseCategoryList = new ArrayList<>();
        //遍历List，并将子节点放入父节点的childrenTreeNodes
        courseCategoryTreeDtos.stream().filter(item -> !id.equals(item.getId())).forEach(item -> {
            if (item.getParentid().equals(id)) courseCategoryList.add(item);
            //找到父节点
            CourseCategoryTreeDto parentNode = mapTemp.get(item.getParentid());
            if (parentNode != null) {
                if (parentNode.getChildrenTreeNodes() == null)
                    //如果父节点的ChildrenTreeNodes属性为空要创建一个集合，往该集合放子节点
                    parentNode.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                //将子节点放入父节点的childrenTreeNodes
                parentNode.getChildrenTreeNodes().add(item);
            }
        });
        return courseCategoryList;
    }
}
