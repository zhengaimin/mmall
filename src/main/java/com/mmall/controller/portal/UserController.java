package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author 郑爱民
 * 日期: 2018-07-25
 * 时间: 16:40
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登陆
     *
     * @param username 用户名称
     * @param password 用户密码
     * @param session  session
     * @return
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody //自动通过springMVC的插件将返回值序列化为Json数据
    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            //将当前的用户信息放到Const.CURRENT_USER中
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 退出登陆
     *
     * @param session 用户信息
     * @return 返回提示消息
     */
    @RequestMapping(value = "/logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session) {
        //登陆的时候设置了session的map current_user, 现在可以删除
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return
     */
    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    /**
     * 校验用户名和邮箱
     *
     * @param str
     * @param type
     * @return
     */
    @RequestMapping(value = "/check_valid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    /**
     * 获取当前用户信息
     *
     * @param session 用户信息
     * @return 成功返回用户信息, 失败返回错误信息
     */
    @RequestMapping(value = "/get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        String msg = null;
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        msg = "用户未登录,无法获取当前用户信息";
        return ServerResponse.createByErrorMessage(msg);
    }

    /**
     * 忘记密码,通过密保来找回密码
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/forget_get_question.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    /**
     * 校验密保是否正确
     * @param username  用户名
     * @param question  问题
     * @param answer    回答
     * @return
     */
    @RequestMapping(value = "/forget_check_answer.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    /**
     * 忘记密码--修改密码
     * @param username          用户名
     * @param passwordNew       新密码
     * @param forgetToken       令牌
     * @return
     */
    @RequestMapping(value = "/forget_reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew,String forgetToken){
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken) ;
    }

    /**
     * 根据密码来修改密码
     * @param session   用户信息
     * @param passwordOld   旧密码
     * @param passwordNew   新密码
     * @return
     */
    @RequestMapping(value = "/reset_password.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        String msg = null ;
        if(user == null){
            msg = "用户未登录" ;
            return ServerResponse.createByErrorMessage(msg) ;
        }
        return iUserService.resetPassword(passwordOld, passwordNew, user) ;
    }

    /**
     * 更新用户信息
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "/update_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_information(HttpSession session,User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        String msg = null ;
        if(currentUser == null){
            msg = "用户未登录" ;
            return ServerResponse.createByErrorMessage(msg) ;
        }
        //Id和username 都不能被改变
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if(response.isSuccess()){
            //更新用户信息
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response ;
    }

    /**
     * 获取当前用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "/get_information.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> get_information(HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        String msg = null ;
        if(currentUser == null){
            msg = "未登录,需要强制登陆status=10" ;
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),msg);
        }
        return iUserService.getInformation(currentUser.getId());
    }

}
