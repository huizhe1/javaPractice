package com.mmall.common;

import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedpoolUtil;
import com.mmall.util.RedispoolUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huizhe
 * @date 2018/12/8
 * @time 22:05
 */
public class CheckUser {

    public static User getUser(HttpServletRequest httpServletRequest) {
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isEmpty(loginToken)) {
            return null;
        }
        String userJsonStr = RedisShardedpoolUtil.get(loginToken);
        return JsonUtil.string2Obj(userJsonStr, User.class);
    }
}
