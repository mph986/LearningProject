package com.xuecheng.content;



import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


/**
 * @author mph
 * @version 1.0
 * @date 2023/6/14 16:44
 * @description TODO
 */
@SpringBootTest
class CourseCategoryMapperTest {
    @Autowired
    CourseCategoryMapper courseCategoryMapper;
    @Test
    public void testCourseCategoryMapper() {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos =
                courseCategoryMapper.selectTreeNodes("1");
        System.out.println(courseCategoryTreeDtos);
    }
}
