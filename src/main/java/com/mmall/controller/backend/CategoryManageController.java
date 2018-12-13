package com.mmall.controller.backend;

import com.mmall.common.CheckUser;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author huizhe
 * @date 2018/10/4
 * @time 10:37
 */
@RestController
@RequestMapping(value="/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @PostMapping(value="add_category.do")
    public ServerResponse addCategory(HttpServletRequest request, String categoryName, @RequestParam(value="parentId", defaultValue="0") int parentId) {
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        //}
        //// 校验一下是否是管理员
        //if (iUserService.checkAdminRole(user).isSuccess()) {
        //    // 是管理员
        //    // 增加我们处理分类的逻辑
            return iCategoryService.addCategory(categoryName, parentId);
        //} else {
        //    return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        //}
    }

    @PostMapping(value="set_category_name.do")
    public ServerResponse setCategoryName(HttpServletRequest request, Integer categoryId, String categoryName) {
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        //}
        //// 校验一下是否是管理员
        //if (iUserService.checkAdminRole(user).isSuccess()) {
        //    // 更新categoryName
            return iCategoryService.updateCategoryName(categoryId, categoryName);
        //} else {
        //    return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        //}
    }

    @GetMapping(value="get_category.do")
    public ServerResponse getChildrenParallelCategory(HttpServletRequest request, @RequestParam(value="categoryId",defaultValue = "0") Integer categoryId) {
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        //}
        //// 校验一下是否是管理员
        //if (iUserService.checkAdminRole(user).isSuccess()) {
        //    // 查询子节点的 category 信息，并且不递归，保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId);
        //} else {
        //    return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        //}
    }

    @GetMapping(value="get_deep_category.do")
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest request, @RequestParam(value="categoryId",defaultValue = "0") Integer categoryId) {
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        //}
        //if (iUserService.checkAdminRole(user).isSuccess()) {
        //   // 查询当前节点的id 和递归子节点的 id
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        //} else {
        //    return ServerResponse.createByErrorMessage("无权限操作，需要管理员权限");
        //}
    }
}
