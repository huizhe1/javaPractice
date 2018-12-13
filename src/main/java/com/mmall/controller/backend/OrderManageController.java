package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.CheckUser;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
import com.mmall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author huizhe
 * @date 2018/10/7
 * @time 15:10
 */
@RestController
@RequestMapping("/manage/order/")
public class OrderManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;

    @PostMapping("list.do")
    public ServerResponse<PageInfo> orderList(HttpServletRequest request, @RequestParam(value="pageNum ",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue = "10") int pageSize) {
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        //}
        //// 校验一下是否是管理员
        //if (iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.manageList(pageNum, pageSize);
        //} else {
        //    return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        //}
    }

    @PostMapping("detail.do")
    public ServerResponse<OrderVo> orderDetail(HttpServletRequest request, Long orderNo) {
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        //}
        //// 校验一下是否是管理员
        //if (iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.manageDetail(orderNo);
        //} else {
        //    return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        //}
    }

    @PostMapping("search.do")
    public ServerResponse<PageInfo> orderSearch(HttpServletRequest request, Long orderNo, @RequestParam(value="pageNum ",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue = "10") int pageSize) {
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        //}
        //// 校验一下是否是管理员
        //if (iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.manageSearch(orderNo, pageNum, pageSize);
        //} else {
        //    return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        //}
    }

    @PostMapping("send_goods.do")
    public ServerResponse<String> orderSendGoods(HttpServletRequest request, Long orderNo) {
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        //}
        //// 校验一下是否是管理员
        //if (iUserService.checkAdminRole(user).isSuccess()) {
            return iOrderService.manageSendGoods(orderNo);
        //} else {
        //    return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        //}
    }
}
