package com.mmall.controller.portal;

import com.mmall.common.CheckUser;
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

import javax.servlet.http.HttpServletRequest;
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
    public ServerResponse add(HttpServletRequest request) {
        User user = CheckUser.getUser(request);

        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(user.getId());
    }

    @PostMapping(value="add.do")
    public ServerResponse add(HttpServletRequest request, Integer count, Integer productId) {
        User user = CheckUser.getUser(request);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(user.getId(), productId, count);
    }

    @PostMapping(value="update.do")
    public ServerResponse update(HttpServletRequest request, Integer count, Integer productId) {
        User user = CheckUser.getUser(request);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(user.getId(), productId, count);
    }

    @PostMapping(value="delete_product.do")
    public ServerResponse deleteProduct(HttpServletRequest request, String productIds) {
        User user = CheckUser.getUser(request);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProduct(user.getId(), productIds);
    }

    @GetMapping(value="select_all.do")
    public ServerResponse selectAll(HttpServletRequest request) {
        User user = CheckUser.getUser(request);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.CHECKED);
    }

    @GetMapping(value="un_select_all.do")
    public ServerResponse unSelectAll(HttpServletRequest request) {
        User user = CheckUser.getUser(request);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), null, Const.Cart.UN_CHECKED);
    }

    @PostMapping(value="select.do")
    public ServerResponse select(HttpServletRequest request, Integer productId) {
        User user = CheckUser.getUser(request);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.CHECKED);
    }

    @PostMapping(value="un_select.do")
    public ServerResponse unSelect(HttpServletRequest request, Integer productId) {
        User user = CheckUser.getUser(request);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(), productId, Const.Cart.UN_CHECKED);
    }

    @GetMapping(value="get_cart_product_count.do")
    public ServerResponse<Integer> getCartProductCount(HttpServletRequest request) {
        User user = CheckUser.getUser(request);
        if (null == user) {
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProductCount(user.getId());
    }
}
