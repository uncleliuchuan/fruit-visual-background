package com.jclz.fruit.wxpay.util;



import com.jclz.fruit.config.WxPayConfig;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 微信参数工具类
 */
public class WxUtil {


    private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 微信支付签名算法sign
     *
     * @param characterEncoding 编码方式
     * @param parameters        map
     * @return
     */
    public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        //该 set 的迭代器将按升序键顺序返回这些条目
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + WxPayConfig.Key);
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    /**
     * 方法名：得到随机的字符串，不超过32位
     *
     * @param length 表示生成字符串的长度
     * @return
     */
    public static String getRandomString(int length) {
        if (length > 32 || length < 1) {
            length = 32;
        }
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int number = 0;
        for (int i = 0; i < length; i++) {
            //从给定的长度里选一个随机数
            number = random.nextInt(base.length());
            //返回指定索引出的Char值
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 订单开始交易的时间\交易终止的时间
     *
     * @return
     */
    public static String getTime() {
        //使用默认时区和语言环境获得一个日历
        Calendar now = Calendar.getInstance();
        String timeStart = df.format(now.getTimeInMillis());
        //return df.format(new Date());
        now.add(Calendar.MINUTE, 30);
        String timeExpire = df.format(now.getTimeInMillis());
        return timeStart + "," + timeExpire;
    }

    /**
     * 商户系统内部的订单号,32个字符内
     *
     * @return
     */
    public static String out_trade_no() {
        //14个数字
        String out_trade_no = df.format(new Date());
        //加上6个随机数
        out_trade_no += getThree() + getThree();

        if (out_trade_no.length() == 18) {
            out_trade_no += "00";
        } else if (out_trade_no.length() == 19) {
            out_trade_no += "0";
        }

        return out_trade_no.trim();

    }


    /**
     * 产生随机的三位数 可能有点bug
     *
     * @return
     */
    public static String getThree() {
        Random rad = new Random();
        return String.valueOf(rad.nextInt(1000));
    }

}
