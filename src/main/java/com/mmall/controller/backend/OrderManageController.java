package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
import com.mmall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author Lenovo
 * 日期: 2018-08-09
 * 时间: 19:19
 */
@Controller
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private IUserService iUserService ;

    @Autowired
    private IOrderService iOrderService ;

    /**
     * 管理员查看订单,进行分页
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpSession session,
                                              @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登陆管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //管理员查看订单分页
            return iOrderService.manageList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 管理员查询订单
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("/detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> detail(HttpSession session, Long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登陆管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //管理员查看订单分页
            return iOrderService.manageDetail(orderNo) ;
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 精准查询, 后期改成模糊查询, 进行分页
     * @param session
     * @param orderNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/search.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(HttpSession session, Long orderNo,
                                               @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                               @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登陆管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //管理员查看订单分页
            return iOrderService.manageSearch(orderNo, pageNum,pageSize) ;
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }

    /**
     * 发货
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("/send_goods.do")
    @ResponseBody
    public ServerResponse<String> orderSendGoods(HttpSession session, Long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登陆管理员");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //管理员查看订单分页
            return iOrderService.manageSendGoods(orderNo) ;
        } else {
            return ServerResponse.createByErrorMessage("无权限操作");
        }
    }


}
