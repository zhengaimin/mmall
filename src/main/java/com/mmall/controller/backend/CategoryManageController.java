package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author Lenovo
 * 日期: 2018-07-27
 * 时间: 16:29
 */
@Controller
@RequestMapping(value = "/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService ;

    @Autowired
    private ICategoryService iCategoryService ;

    /**
     * 添加类别
     * @param session       用户信息
     * @param categoryName  类别名称
     * @param parentId      父类别Id  默认为0
     * @return
     */
    @RequestMapping("/add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") Integer parentId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录") ;
        }
        //校验一下是否为管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //是管理员
            //增加我们处理分类的逻辑
            return iCategoryService.addCategory(categoryName, parentId) ;
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限") ;
        }
    }

    /**
     * 更新分类
     * @param session       用户信息
     * @param categoryId    分类Id
     * @param categoryName  分类名称
     * @return
     */
    @RequestMapping("/set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录") ;
        }
        //校验一下是否为管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //如果是管理员,更新categoryName
            return iCategoryService.updateCategoryName(categoryId, categoryName) ;
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限") ;
        }
    }


    /**
     * 查找子节点的平级分类
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping("/get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录") ;
        }
        //校验一下是否为管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //如果是管理员,查询子节点的category信息,不递归,保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId) ;
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限") ;
        }
    }

    /**
     * 查找当前节点Id和子节点Id
     * @param session       用户信息
     * @param categoryId    分类Id
     * @return
     */
    @RequestMapping("/get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录") ;
        }
        //校验一下是否为管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //如果是管理员,查找当前节点Id和递归(所有)子节点Id
            return iCategoryService.selectCategoryAndChildrenById(categoryId) ;
        }else {
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限") ;
        }
    }


}
