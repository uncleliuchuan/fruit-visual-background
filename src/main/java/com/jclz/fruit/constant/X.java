package com.jclz.fruit.constant;

public interface X {

    interface TIME {
        //对应秒
        int DAY_SECOND = 24 * 60 * 60;
        int HOUR_SECOND = 60 * 60;
        int MINUTE = 60;
        //对应毫秒
        int DAY_MILLISECOND = 24 * 60 * 60 * 1000;
        int HOUR_MILLISECOND = 60 * 60 * 1000;
        int MINUTE_MILLISECOND = 60 * 1000;
        int SECOND_MILLISECOND = 1000;
    }

    /**
     * 编码
     */
    interface ENCODING {
        String U = "UTF-8";
        String I = "ISO-8859-1";
    }

    /**
     * 短信配置
     */
    interface SMS {
        String URL = "xx";
        String NAME = "xx";
        String PWD = "xx";
        String SIGN = "xx";
        String TEMPLATE_SMS_CODE = "xx";
    }
}
