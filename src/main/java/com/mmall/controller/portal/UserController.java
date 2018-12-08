package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.RedisPool;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedispoolUtil;
import net.sf.jsqlparser.schema.Server;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author huizhe
 * @date 2018/10/3
 * @time 13:42
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @PostMapping(value="login.do")
    public ServerResponse<User> login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            // session.setAttribute(Const.CURRENT_USER, response.getData());
            CookieUtil.writeLoginToken(httpServletResponse, session.getId());
            RedispoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()), Const.RedisCacheExtiome.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    @PostMapping(value="logout.do")
    public ServerResponse<String> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        CookieUtil.delLoginToken(httpServletRequest, httpServletResponse);
        RedispoolUtil.del(loginToken);
        return ServerResponse.createBySuccess();
    }

    @PostMapping(value="register.do")
    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }

    @PostMapping(value="check_valid.do")
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str, type);
    }

    @PostMapping(value="get_user_info.do")
    public ServerResponse<User> getUserInfo(HttpServletRequest httpServletRequest) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
        }
        String userJsonStr = RedispoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr, User.class);
        if (null != user) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }

    @PostMapping(value="forget_get_question.do")
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    @PostMapping(value="forget_check_answer.do")
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username, question, answer);
    }

    @PostMapping(value="forget_reset_password.do")
    public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
        return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    @PostMapping(value="reset_password.do")
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == user) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld, passwordNew, user);
    }

    @PostMapping(value="update_information.do")
    public ServerResponse<User> update_information(HttpSession session, User user) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if (response.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @PostMapping(value="get_information.do")
    public ServerResponse<User> get_information(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (null == currentUser) {
            return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,需要强制登录status=10");
        }
        return iUserService.getInformation(currentUser.getId());
    }
}
