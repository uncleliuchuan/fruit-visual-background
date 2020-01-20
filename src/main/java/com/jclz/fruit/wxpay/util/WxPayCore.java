package com.jclz.fruit.wxpay.util;


import com.jclz.fruit.config.WxPayConfig;
import com.jclz.fruit.wxpay.model.Unifiedorder;
import com.jclz.fruit.wxpay.model.UnifiedorderResult;
import com.jclz.fruit.wxpay.model.UnifiedorderSelectResult;
import com.jclz.fruit.wxpay.model.WXPayResult;
import org.apache.log4j.Logger;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 类名：WxPayCore
 * 功能：拼接微信参数、验证微信异步通知信息
 * 详细：固定模块，一般情况下无需修改
 * 版本：1.0
 * 日期：2017-03-21
 * 说明：
 */
public class WxPayCore {

    private static Logger log = Logger.getLogger(WxPayCore.class);

    /**
     * 拼接微信支付必要参数，加签，并返回字符串
     *
     * @param out_trade_no 传入商户内部订单号，必须唯一；
     * @param total_fee    所要支付的金额，格式必须为0.00，单位为元。
     * @return App端调起支付所需要的参数
     */
    public static SortedMap<Object, Object> createWxPayParam(String out_trade_no, String total_fee, String orderType) {

        //声明统一下单待签名字符串的map
        SortedMap<Object, Object> wxparameters = new TreeMap<Object, Object>();

        wxparameters.put("appid", WxPayConfig.appid);//微信开放平台审核通过的应用APPID

        wxparameters.put("mch_id", WxPayConfig.mch_id);//微信支付分配的商户号

        String nonce_str = WxUtil.getRandomString(20);

        wxparameters.put("nonce_str", nonce_str);//随机字符串，不长于32位。

        if ("fruit".equals(orderType)) {
            wxparameters.put("body", WxPayConfig.body);
        }
        if ("vip".equals(orderType)) {
            wxparameters.put("body", WxPayConfig.vipbody);
        }


        //以下注释均为非必要参数，可根据实际情况决定是否需要
        //wxparameters.put("detail", "");//商品详情,非必要参数，根据项目实际需求
        //wxparameters.put("attach","");//附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
        //wxparameters.put("fee_type", "");//默认人民币：CNY
        //String time[] = WxUtil.getTime().split(",")
        //wxparameters.put("time_start", time[0]);//订单生成时间
        //wxparameters.put("time_expire", time[1]);//订单失效时间
        //wxparameters.put("goods_tag", "");//商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠(官方api)
        //wxparameters.put("limit_pay", "no_credit");//指定支付方式，no_credit

        wxparameters.put("out_trade_no", out_trade_no);//商户订单号

        /**
         * 订单总金额  传入的金额暂定为元，则需要*100变成分  int类型
         */
        Integer payMoney = new BigDecimal(total_fee).multiply(new BigDecimal(100)).intValue();
        wxparameters.put("total_fee", payMoney);//订单总金额 接口中参数支付金额单位为【分】，参数值不能带小数。

        wxparameters.put("spbill_create_ip", WxPayConfig.spbill_create_ip);//终端IP  用户端实际ip

        wxparameters.put("notify_url", WxPayConfig.notify_url);//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。

        wxparameters.put("trade_type", WxPayConfig.trade_type);//支付类型  只有一个参数APP

        //生成签名
        String sign = WxUtil.createSign("UTF-8", wxparameters);

        log.info("微信签名是：" + sign);

        //拼接XML给微信
        String body = "";
        if ("activity".equals(orderType)) {
            body = WxPayConfig.body;
        }
        if ("vip".equals(orderType)) {
            body = WxPayConfig.vipbody;
        }

        //对象进行存储
        Unifiedorder unifiedorder = new Unifiedorder(WxPayConfig.appid, WxPayConfig.mch_id, nonce_str, sign, body, out_trade_no, payMoney, WxPayConfig.spbill_create_ip, WxPayConfig.notify_url, WxPayConfig.trade_type);

        //转成xml形式
        String xmlInfo = HttpXmlUtil.xmlInfo(unifiedorder);

        log.info("转成的xml形式为:" + xmlInfo);

        if (StringUtil.isNull(xmlInfo)) {

            String weixinPost = HttpXmlUtil.httpsRequest(WxPayConfig.wxUrl, "POST", xmlInfo).toString();//请求微信

            log.info("微信支付的反馈信息为:" + weixinPost);

            UnifiedorderResult unifiedorderResult = ParseXMLUtils.jdomParseXml(weixinPost);//解析微信的反馈

            if (unifiedorderResult != null) {

                if ("SUCCESS".equals(unifiedorderResult.getReturn_code())) {

                    //开始拼接App调起微信的参数
                    SortedMap<Object, Object> wxAppparameters = new TreeMap<Object, Object>();

                    wxAppparameters.put("appid", unifiedorderResult.getAppid());
                    wxAppparameters.put("partnerid", unifiedorderResult.getMch_id());
                    wxAppparameters.put("prepayid", unifiedorderResult.getPrepay_id());
                    wxAppparameters.put("package", WxPayConfig.PACKAGE);
                    wxAppparameters.put("noncestr", WxUtil.getRandomString(20));
                    wxAppparameters.put("timestamp", String.valueOf(new Date().getTime()).substring(0, 10));

                    //生成签名
                    String signApp = WxUtil.createSign("UTF-8", wxAppparameters);
                    wxAppparameters.put("sign", signApp);
                    return wxAppparameters;
                } else {
                    log.error("错误原因为：" + unifiedorderResult.getReturn_msg());
                    return null;
                }
            } else {
                log.error("服务端请求微信的返回值异常。");
                return null;
            }
        } else {
            log.error("微信参数转成的xml形式错误");
            return null;
        }

    }

    /**
     * 检测微信异步反馈是否真实
     *
     * @param request
     * @return null 验证失败  不为null即成功
     */
    public static SortedMap<Object, Object> checkWxPayParam(HttpServletRequest request) throws Exception {

        BufferedReader reader = request.getReader();

        String line = "";
        StringBuffer inputString = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }

        if (reader != null) {
            reader.close();
        }

        log.info("----[微信回调]接收到的报文---" + inputString.toString());

        if (StringUtil.isNull(inputString.toString())) {
            //解析微信异步通知的内容
            WXPayResult wxPayResult = JdomParseXmlUtils.getWXPayResult(inputString.toString());

            //忽略大小写进行比对    此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
            if ("SUCCESS".equalsIgnoreCase(wxPayResult.getReturn_code())) {//SUCCESS/FAIL

                SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();

                parameters.put("appid", wxPayResult.getAppid());//应用ID

                parameters.put("attach", wxPayResult.getAttach());//商家数据包

                parameters.put("bank_type", wxPayResult.getBank_type());//付款银行

                parameters.put("cash_fee", wxPayResult.getCash_fee());//现金支付金额

                parameters.put("fee_type", wxPayResult.getFee_type());//**货币种类

                parameters.put("is_subscribe", wxPayResult.getIs_subscribe());

                parameters.put("mch_id", wxPayResult.getMch_id());//商户号

                parameters.put("nonce_str", wxPayResult.getNonce_str());//随机字符串

                parameters.put("openid", wxPayResult.getOpenid());//用户标识

                parameters.put("out_trade_no", wxPayResult.getOut_trade_no());//商户订单号

                parameters.put("req_info", wxPayResult.getReq_info());//加密信息
                if (!"".equals(wxPayResult.getReq_info()) && null != wxPayResult.getReq_info()) {
                    String xml = decryptData(wxPayResult.getReq_info(), WxPayConfig.Key);//解密退款回调的加密信息返回 微信req_infoXML格式文件
                    WXPayResult wxPayRefundResult = JdomParseXmlUtils.getWXPayRefundResult(xml);
                    if ("SUCCESS".equals(wxPayRefundResult.getRefund_status())) {
                        parameters.put("out_refund_no", wxPayRefundResult.getOut_refund_no());//商户退款单号
                        parameters.put("out_trade_no", wxPayRefundResult.getOut_trade_no());//商户退款单号
                        parameters.put("return_code", "SUCCESS");
                        return parameters;
                    }
                }

                if ("SUCCESS".equalsIgnoreCase(wxPayResult.getResult_code())) {

                    parameters.put("result_code", wxPayResult.getResult_code());//业务结果

                    parameters.put("return_code", wxPayResult.getReturn_code());//返回状态码

                    parameters.put("time_end", wxPayResult.getTime_end());//支付完成时间

                    parameters.put("total_fee", wxPayResult.getTotal_fee());//总金额-分为单位

                    parameters.put("trade_type", wxPayResult.getTrade_type());//交易类型

                    parameters.put("transaction_id", wxPayResult.getTransaction_id());//微信支付订单号

                } else if ("FAIL".equalsIgnoreCase(wxPayResult.getResult_code())) {
                    parameters.put("err_code", wxPayResult.getErr_code());//错误返回的信息描述
                    parameters.put("err_code_des", wxPayResult.getErr_code_des());//错误返回的信息描述
                    log.error("微信支付错误，错误码为：" + wxPayResult.getErr_code() + "错误详情为：" + wxPayResult.getErr_code_des());
                }

                //反校验签名
                String sign = WxUtil.createSign("UTF-8", parameters);

                if (sign.equals(wxPayResult.getSign())) {//签名一致

                    //验证一些基础参数是否一致
                    if (WxPayConfig.mch_id.equals(parameters.get("mch_id"))) {

                        if (WxPayConfig.appid.equals(parameters.get("appid"))) {//应用id

                            if ("SUCCESS".equalsIgnoreCase(parameters.get("result_code").toString())) {
                                return parameters;
                            } else {
                                log.error("微信支付通知结果不为SUCCESS。");
                                return null;
                            }
                        } else {
                            log.error("应用appid不一致。");
                            return null;
                        }
                    } else {
                        log.error("商户mch_id不一致。");
                        return null;
                    }
                } else {
                    log.error("反校验签名不一致。");
                    return null;
                }
            } else {
                log.error("微信回调的通信标识验证失败。");
                return null;
            }
        } else {
            log.error("未收到微信异步回调的报文。");
            return null;
        }

    }

    public static SortedMap<Object, Object> createWxPayParamForSelect(String out_trade_no) {
        //声明统一下单待签名字符串的map
        SortedMap<Object, Object> wxparameters = new TreeMap<Object, Object>();

        wxparameters.put("appid", WxPayConfig.appid);//微信开放平台审核通过的应用APPID

        wxparameters.put("mch_id", WxPayConfig.mch_id);//微信支付分配的商户号

        String nonce_str = WxUtil.getRandomString(20);

        wxparameters.put("nonce_str", nonce_str);//随机字符串，不长于32位。

        wxparameters.put("out_trade_no", out_trade_no);//商户订单号

        //wxparameters.put("transaction_id",transaction_id);//微信订单号
        //生成签名
        String sign = WxUtil.createSign("UTF-8", wxparameters);
        log.info("微信签名是：" + sign);

        //拼接XML给微信
        StringBuffer bf = new StringBuffer();
        bf.append("<xml>");

        bf.append("<appid><![CDATA[");
        bf.append(wxparameters.get("appid"));
        bf.append("]]></appid>");

        bf.append("<mch_id><![CDATA[");
        bf.append(wxparameters.get("mch_id"));
        bf.append("]]></mch_id>");

        bf.append("<nonce_str><![CDATA[");
        bf.append(wxparameters.get("nonce_str"));
        bf.append("]]></nonce_str>");

        bf.append("<out_trade_no><![CDATA[");
        bf.append(wxparameters.get("out_trade_no"));
        bf.append("]]></out_trade_no>");

		/*bf.append("<transaction_id><![CDATA[");
		bf.append(wxparameters.get("transaction_id"));
		bf.append("]]></transaction_id>");*/

        bf.append("<sign><![CDATA[");
        bf.append(sign);
        bf.append("]]></sign>");
        bf.append("</xml>");
        log.info("转成的xml形式为:" + bf);

        if (StringUtil.isNull(bf.toString())) {

            String weixinPost = HttpXmlUtil.httpsRequest(WxPayConfig.selectUrl, "POST", bf.toString()).toString();//请求微信

            log.info("微信订单查询的反馈信息为:" + weixinPost);
            UnifiedorderSelectResult unifiedorderSelectResult = ParseXMLUtils.jdomParseSelectXml(weixinPost);
            //UnifiedorderResult unifiedorderResult = ParseXMLUtils.jdomParseXml(weixinPost);//解析微信的反馈

            if (unifiedorderSelectResult != null) {

                if ("SUCCESS".equals(unifiedorderSelectResult.getReturn_code())) {

                    //开始拼接App调起微信的参数
                    SortedMap<Object, Object> wxAppparameters = new TreeMap<Object, Object>();

                    wxAppparameters.put("appid", unifiedorderSelectResult.getAppid());
                    wxAppparameters.put("partnerid", unifiedorderSelectResult.getMch_id());
                    wxAppparameters.put("package", WxPayConfig.PACKAGE);
                    wxAppparameters.put("out_trade_no", unifiedorderSelectResult.getOut_trade_no());
                    wxAppparameters.put("noncestr", unifiedorderSelectResult.getNonce_str());
                    wxAppparameters.put("timestamp", String.valueOf(new Date().getTime()).substring(0, 10));
                    wxAppparameters.put("trade_state", unifiedorderSelectResult.getTrade_state());
                    wxAppparameters.put("total_fee", unifiedorderSelectResult.getTotal_fee());
                    wxAppparameters.put("transaction_id", unifiedorderSelectResult.getTransaction_id());
                    wxAppparameters.put("time_end", unifiedorderSelectResult.getTime_end());
                    wxAppparameters.put("trade_state_desc", unifiedorderSelectResult.getTrade_state_desc());
                    //生成签名
                    String signApp = WxUtil.createSign("UTF-8", wxAppparameters);
                    wxAppparameters.put("sign", signApp);
                    return wxAppparameters;
                } else {
                    log.error("错误原因为：" + unifiedorderSelectResult.getReturn_msg());
                    return null;
                }
            } else {
                log.error("服务端请求微信的返回值异常。");
                return null;
            }
        } else {
            log.error("微信参数转成的xml形式错误");
            return null;
        }
    }

    public static SortedMap<Object, Object> createWxPayParamForRefund(String out_trade_no, String out_refund_no, Integer total_fee, Integer refund_fee) {
        //声明统一下单待签名字符串的map
        SortedMap<Object, Object> wxparameters = new TreeMap<Object, Object>();

        wxparameters.put("appid", WxPayConfig.appid);//微信开放平台审核通过的应用APPID

        wxparameters.put("mch_id", WxPayConfig.mch_id);//微信支付分配的商户号

        String nonce_str = WxUtil.getRandomString(20);

        wxparameters.put("nonce_str", nonce_str);//随机字符串，不长于32位。

        wxparameters.put("out_trade_no", out_trade_no);//商户订单号

        wxparameters.put("out_refund_no", out_refund_no);//商户退款单号

        wxparameters.put("total_fee", total_fee);//订单金额

        wxparameters.put("refund_fee", refund_fee);//退款金额

        //生成签名
        String sign = WxUtil.createSign("UTF-8", wxparameters);
        log.info("微信签名是：" + sign);

        //拼接XML给微信
        StringBuffer bf = new StringBuffer();
        bf.append("<xml>");

        bf.append("<appid><![CDATA[");
        bf.append(wxparameters.get("appid"));
        bf.append("]]></appid>");

        bf.append("<mch_id><![CDATA[");
        bf.append(wxparameters.get("mch_id"));
        bf.append("]]></mch_id>");

        bf.append("<nonce_str><![CDATA[");
        bf.append(wxparameters.get("nonce_str"));
        bf.append("]]></nonce_str>");

        bf.append("<out_refund_no><![CDATA[");
        bf.append(wxparameters.get("out_refund_no"));
        bf.append("]]></out_refund_no>");

        bf.append("<out_trade_no><![CDATA[");
        bf.append(wxparameters.get("out_trade_no"));
        bf.append("]]></out_trade_no>");

        bf.append("<total_fee><![CDATA[");
        bf.append(wxparameters.get("total_fee"));
        bf.append("]]></total_fee>");

        bf.append("<refund_fee><![CDATA[");
        bf.append(wxparameters.get("refund_fee"));
        bf.append("]]></refund_fee>");

        bf.append("<sign><![CDATA[");
        bf.append(sign);
        bf.append("]]></sign>");
        bf.append("</xml>");
        log.info("转成的xml形式为:" + bf);

        if (StringUtil.isNull(bf.toString())) {

            String weixinPost = HttpXmlUtil.postSSL(WxPayConfig.refundUrl, bf.toString(), WxPayConfig.API_CLIENT_CERT_PATH, WxPayConfig.mch_id); //请求微信
            log.info("微信申请退款的反馈信息为:" + weixinPost);
            UnifiedorderSelectResult unifiedorderSelectResult = ParseXMLUtils.jdomParseSelectXml(weixinPost);
            //UnifiedorderResult unifiedorderResult = ParseXMLUtils.jdomParseXml(weixinPost);//解析微信的反馈

            if (unifiedorderSelectResult != null) {

                if ("SUCCESS".equals(unifiedorderSelectResult.getReturn_code())) {

                    //微信申请退款的反馈信息
                    SortedMap<Object, Object> wxAppparameters = new TreeMap<Object, Object>();

                    wxAppparameters.put("appid", unifiedorderSelectResult.getAppid());
                    wxAppparameters.put("mch_id", unifiedorderSelectResult.getMch_id());
                    wxAppparameters.put("out_trade_no", unifiedorderSelectResult.getOut_trade_no());
                    wxAppparameters.put("nonce_str", unifiedorderSelectResult.getNonce_str());
                    wxAppparameters.put("sign", unifiedorderSelectResult.getSign());
                    wxAppparameters.put("out_refund_no", unifiedorderSelectResult.getOut_refund_no());
                    wxAppparameters.put("refund_id", unifiedorderSelectResult.getRefund_id());
                    wxAppparameters.put("refund_fee", unifiedorderSelectResult.getRefund_fee());
                    return wxAppparameters;
                } else {
                    log.error("错误原因为：" + unifiedorderSelectResult.getReturn_msg());
                    return null;
                }
            } else {
                log.error("服务端请求微信的返回值异常。");
                return null;
            }
        } else {
            log.error("微信参数转成的xml形式错误");
            return null;
        }
    }

    /**
     * 微信退款信息解密
     *
     * @param req_info    加密信息
     * @param paternerKey 商户号
     * @return
     */
    public static final String decryptData(String req_info, String paternerKey) {

        String res = "";
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(MD5Util.MD5Encode(paternerKey, "UTF-8").toLowerCase().getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] bytes = cipher.doFinal(Base64Utils.decode(req_info.getBytes()));
            res = new String(bytes);
            System.out.println("加密信息解密后：" + res);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return res;
    }
}
