package com.mmall.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author huizhe
 * @date 2018/10/6
 * @time 15:53
 */
public class CartVo {


    List<CartProductVo> cartProductVoList;
    private BigDecimal cartTotalPrice;
    private Boolean allCheck;
    private String imageHost;

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllCheck() {
        return allCheck;
    }

    public void setAllCheck(Boolean allCheck) {
        this.allCheck = allCheck;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }
}
