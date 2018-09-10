package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;
import java.util.Set;

/**
 * @author Lenovo
 * 日期: 2018-07-27
 * 时间: 17:00
 */
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categroyId);

    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) ;
}
