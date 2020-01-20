package com.jclz.fruit.config;

/**
 * 微信支付所需的必要参数
 * 理想情况下只需配置此处
 */
public class WxPayConfig {
    //应用ID
    public static String appid = "wx667aa1c419ac544e";
    //商户号
    public static String mch_id = "1517969351";

    /**
     * 设置地址在微信商户平台，key设置路径：微信商户平台-->账户设置-->API安全-->密钥设置
     * 密码生成地址：http://www.sexauth.com/
     */
    public static String Key = "JCAdversary834153Adversary834153";//"799f2542aa9d09c6aaccb39573dcf8d4";//秘钥

    /**
     * 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
     * 测试：http://39.104.138.184:8080/WXPay/pay_after
     * 正式：http://39.104.157.227:28080/WXPay/pay_after
     */
    public static String notify_url = "http://39.104.138.184:9000/wx/pay_after";

    /**
     * 商品描述交易字段格式根据不同的应用场景按照以下格式：
     * APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
     */
    public static String body = "超级果蔬";

    public static String vipbody = "超级果蔬--vip充值";


    //客户端ip，固定为此值
    public static String spbill_create_ip = "127.0.0.1";

    //支付类型  固定值 APP
    public static String trade_type = "APP";

    //固定值 Sign=WXPay
    public static String PACKAGE = "Sign=WXPay";

    /**
     * 微信统一下单接口请求地址   固定值
     */
    public static String wxUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 微信订单查询接口请求地址 固定值
     */
    public static String selectUrl = "https://api.mch.weixin.qq.com/pay/orderquery";
    /**
     * 微信订单申请退款接口请求地址 固定值
     */
    public static String refundUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 商户支付证书路径
     */
    public static final String API_CLIENT_CERT_PATH = "/home/cert/apiclient_cert.p12";

}
