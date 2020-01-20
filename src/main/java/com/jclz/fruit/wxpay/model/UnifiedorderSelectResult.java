package com.jclz.fruit.wxpay.model;

import lombok.Data;

/**
 * 订单查询微信返回的参数组
 */
@Data
public class UnifiedorderSelectResult {
    private String appid;//调用接口提交的应用ID
    private String mch_id;//调用接口提交的商户号
    private String nonce_str;//微信返回的随机字符串
    private String sign;//微信返回的签名
    private String result_code;//业务结果
    private String err_code;//错误代码
    private String err_code_des;//错误返回的信息描述
    private String trade_type;//调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP
    private String return_code;//返回状态码SUCCESS/FAIL此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
    private String return_msg;//返回信息 如非空，为错误原因 1.签名失败 2.参数格式校验错误
    private String openid;//用户在商户appid下的唯一标识
    private String device_info;//微信支付分配的终端设备号
    private String is_subscribe;//用户是否关注公众账号，Y-关注，N-未关注
    /*
    SUCCESS—支付成功
    REFUND—转入退款
    NOTPAY—未支付
    CLOSED—已关闭
    REVOKED—已撤销（刷卡支付）
    USERPAYING--用户支付中
    PAYERROR--支付失败(其他原因，如银行返回失败)
     */
    private String trade_state;//交易状态
    private String bank_type;//付款银行,银行类型，采用字符串类型的银行标识
    private Integer total_fee;//总金额,订单总金额，单位为分
    private String fee_type;//fee_type 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
    private String transaction_id;//微信支付订单号 1009660380201506130728806387
    private String out_trade_no;//商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。20150806125346
    private String time_end;//订单支付时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
    private String trade_state_desc;//交易状态描述 对当前查询订单状态的描述和下一步操作的指引

    private String out_refund_no;//商户退款单号
    private String refund_id;//微信退款单号
    private Integer refund_fee;//退款金额


}
