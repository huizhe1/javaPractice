package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.CheckUser;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author huizhe
 * @date 2018/10/6
 * @time 19:00
 */
@RestController
@RequestMapping(value="/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    @PostMapping(value="add.do")
    public ServerResponse add(HttpServletRequest request, Shipping shipping) {
        User user = CheckUser.getUser(request);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.add(user.getId(), shipping);
    }

    @PostMapping(value="del.do")
    public ServerResponse del(HttpServletRequest request, Integer shippingId) {
        User user = CheckUser.getUser(request);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.del(user.getId(), shippingId);
    }

    @PostMapping(value="update.do")
    public ServerResponse update(HttpServletRequest request, Shipping shipping) {
        User user = CheckUser.getUser(request);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.update(user.getId(), shipping);
    }

    @PostMapping(value="select.do")
    public ServerResponse<Shipping> select(HttpServletRequest request, Integer shippingId) {
        User user = CheckUser.getUser(request);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.select(user.getId(), shippingId);
    }

    @PostMapping(value="list.do")
    public ServerResponse<PageInfo> list(HttpServletRequest request, @RequestParam(value="pageNum", defaultValue ="1")int pageNum, @RequestParam(value="pageSize", defaultValue ="10")int pageSize) {
        User user = CheckUser.getUser(request);
        if (null == user) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.list(user.getId(), pageNum, pageSize);
    }
}
