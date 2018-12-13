package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.CheckUser;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author huizhe
 * @date 2018/10/4
 * @time 16:11
 */
@RestController
@RequestMapping(value="/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;

    @PostMapping(value="save.do")
    public ServerResponse productSave(HttpServletRequest request, Product product) {
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        //}
        //if (iUserService.checkAdminRole(user).isSuccess()) {
        //    // 填充我们增加产品的业务逻辑
            return iProductService.saveOrUpdateProduct(product);
        //} else {
        //    return ServerResponse.createByErrorMessage("无权限操作");
        //}
    }

    @PostMapping(value="set_sale_status.do")
    public ServerResponse setSaleStatus(HttpServletRequest request, Integer productId, Integer status) {
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        //}
        //if (iUserService.checkAdminRole(user).isSuccess()) {
        //    // 填充业务逻辑
            return iProductService.setSaleStatus(productId, status);
        //} else {
        //    return ServerResponse.createByErrorMessage("无权限操作");
        //}
    }

    @PostMapping(value="detail.do")
    public ServerResponse getDatail(HttpServletRequest request, Integer productId) {
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        //}
        //if (iUserService.checkAdminRole(user).isSuccess()) {
        //    // 填充业务逻辑
            return iProductService.manageProductDetail(productId);
        //} else {
        //    return ServerResponse.createByErrorMessage("无权限操作");
        //}
    }

    @GetMapping(value="list.do")
    public ServerResponse getList(HttpServletRequest request, @RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="10") int pageSize) {
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        //}
        //if (iUserService.checkAdminRole(user).isSuccess()) {
        //    // 动态分页
            return iProductService.getProductList(pageNum, pageSize);
        //} else {
        //    return ServerResponse.createByErrorMessage("无权限操作");
        //}
    }

    @PostMapping(value="search.do")
    public ServerResponse getList(HttpServletRequest request, String productName, Integer productId, @RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="10") int pageSize) {
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        //}
        //if (iUserService.checkAdminRole(user).isSuccess()) {
        //    // 动态分页
            return iProductService.searchProduct(productName, productId, pageNum, pageSize);
        //} else {
        //    return ServerResponse.createByErrorMessage("无权限操作");
        //}
    }

    @RequestMapping(value="upload.do")
    public ServerResponse upload(@RequestParam(value="upload_file", required=false) MultipartFile file, HttpServletRequest request) {
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    return ServerResponse.createeByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员");
        //}
        //if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/")+targetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);
            return ServerResponse.createBySuccess(fileMap);
        //} else {
        //    return ServerResponse.createByErrorMessage("无权限操作");
        //}

    }

    @PostMapping(value="richtext_img_upload.do")
    public Map richtextImgUpload(@RequestParam(value="upload_file", required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = Maps.newHashMap();
        //User user = CheckUser.getUser(request);
        //if (null == user) {
        //    resultMap.put("success", false);
        //    resultMap.put("msg", "请登录管理员");
        //    return resultMap;
        //}
        //// 富文本对于返回值有自己的要求，我们使用的是simditor， 所提按照simditor的要求进行返回
        //if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            if (StringUtils.isBlank(targetFileName)) {
                resultMap.put("success", false);
                resultMap.put("msg", "上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/")+targetFileName;
            resultMap.put("success", true);
            resultMap.put("msg", "上传成功");
            resultMap.put("file_path", url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        //} else {
        //    resultMap.put("success", false);
        //    resultMap.put("msg", "无权限操作");
        //    return resultMap;
        //}

    }
}
