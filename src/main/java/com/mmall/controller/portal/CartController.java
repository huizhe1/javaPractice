package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author huizhe
 * @date 2018/10/6
 * @time 15:33
 */
@RestController
@RequestMapping(value="/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @GetMapping(value="list.do")
    public ServerResponse add(HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(user.getId());
    }

    @PostMapping(value="add.do")
    public ServerResponse add(HttpSession session, Integer count, Integer productId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(user.getId(), productId, count);
    }

    @PostMapping(value="update.do")
    public ServerResponse update(HttpSession session, Integer count, Integer productId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(user.getId(), productId, count);
    }

    @PostMapping(value="delete_product.do")
    public ServerResponse deleteProduct(HttpSession session, String productIds) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProduct(user.getId(), productIds);
    }

    @GetMapping(value="select_all.do")
    public ServerResponse selectAll(HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.CHECKED);
    }

    @GetMapping(value="un_select_all.do")
    public ServerResponse unSelectAll(HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.UN_CHECKED);
    }

    @PostMapping(value="select.do")
    public ServerResponse select(HttpSession session, Integer productId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.CHECKED);
    }

    @PostMapping(value="un_select.do")
    public ServerResponse unSelect(HttpSession session, Integer productId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.UN_CHECKED);
    }

    @GetMapping(value="get_cart_product_count.do")
    public ServerResponse<Integer> getCartProductCount(HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProductCount(user.getId());
    }
}
