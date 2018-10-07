package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author huizhe
 * @date 2018/10/7
 * @time 11:06
 */
@RestController
@RequestMapping("/order/")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService iOrderService;

    @PostMapping("create.do")
    public ServerResponse create(HttpSession session, Integer shippingId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.createOrder(user.getId(), shippingId);
    }

    @PostMapping("cancel.do")
    public ServerResponse cancel(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.cancel(user.getId(), orderNo);
    }

    @PostMapping("get_order_cart_product.do")
    public ServerResponse getOrderCartProduct(HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderCartProduct(user.getId());
    }

    @PostMapping("detail.do")
    public ServerResponse detail(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderDetail(user.getId(), orderNo);
    }

    @PostMapping("list.do")
    public ServerResponse list(HttpSession session, @RequestParam(value="pageNum ",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue = "10") int pageSize) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iOrderService.getOrderList(user.getId(), pageNum, pageSize);
    }
}
