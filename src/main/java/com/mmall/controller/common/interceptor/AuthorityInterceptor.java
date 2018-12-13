package com.mmall.controller.common.interceptor;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedpoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * @author huizhe
 * @date 2018/12/13
 * @time 15:40
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String methodName = handlerMethod.getMethod().getName();
        String className = ((HandlerMethod) handler).getBean().getClass().getSimpleName();
        StringBuffer requestParamBuffer = new StringBuffer();
        Map paramMap = request.getParameterMap();
        Iterator it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String mapKey = (String) entry.getKey();
            String mapValue = StringUtils.EMPTY;
            // request 参数的map,里面的value返回的是一个String[]
            Object obj = entry.getValue();
            if (obj instanceof String[]) {
                String[] strs = (String[]) obj;
                mapValue = Arrays.toString(strs);
                }
                requestParamBuffer.append(mapKey).append("=").append(mapValue);
            }
        User user = null;
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isNoneEmpty(loginToken)) {
            String userJsonStr = RedisShardedpoolUtil.get(loginToken);
            user = JsonUtil.string2Obj(userJsonStr, User.class);
        }
        if (null == user || (user.getRole().intValue() != Const.Role.ROLE_ADMIN)) {
            // 返回false, 即不回调用controller理的方法
            // 这里要reset,否则会异常
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");

            PrintWriter out = response.getWriter();

            if (null == user) {
                // 富文本上传是有相应的请求结果的
                if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "richtextImgUpload")) {
                     Map resultMap = Maps.newHashMap();
                     resultMap.put("success", false);
                     resultMap.put("msg", "请登录管理员");
                    out.print(JsonUtil.obj2String(resultMap));
                } else{
                    out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截,用户未登录")));
                }
            } else {
                if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "richtextImgUpload")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "无权限操作");
                    out.print(JsonUtil.obj2String(resultMap));
                }  else {
                    out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截,用户无权限操作")));
                }
            }
            out.flush();
            out.close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion");
    }
}
