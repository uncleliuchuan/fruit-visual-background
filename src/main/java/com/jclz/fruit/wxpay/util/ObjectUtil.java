package com.jclz.fruit.wxpay.util;

/**
 * 对象工具类
 */
public class ObjectUtil {

    /**
     * 判断Integer类型参数是否可用  大于0
     */
    public static boolean isPassInteger(Integer id) {
        if (id != null && id > 0) {
            return true;
        } else {
            return false;
        }
    }
}
