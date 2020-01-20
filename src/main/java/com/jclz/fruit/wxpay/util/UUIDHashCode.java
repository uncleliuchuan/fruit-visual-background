package com.jclz.fruit.wxpay.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UUIDHashCode {
    public static String getOrderIdByUUId() {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        return new SimpleDateFormat("HHmmss").format(new Date().getTime()) + String.format("%011d", hashCodeV);
    }
}
