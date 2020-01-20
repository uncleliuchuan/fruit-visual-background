package com.jclz.fruit.wxpay.util;


import com.jclz.fruit.wxpay.model.UnifiedorderResult;
import com.jclz.fruit.wxpay.model.WXPayResult;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * 微信解析xml：带有CDATA格式的
 */
public class JdomParseXmlUtils {

    /**
     * 1、统一下单获取微信返回
     * 解析的时候自动去掉CDMA
     *
     * @param xml
     */
    @SuppressWarnings("unchecked")
    public static UnifiedorderResult getUnifiedorderResult(String xml) {
        UnifiedorderResult unifieorderResult = new UnifiedorderResult();
        try {
            StringReader read = new StringReader(xml);
            // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
            // 创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
            // 通过输入源构造一个Document
            Document doc;
            doc = (Document) sb.build(source);

            Element root = doc.getRootElement();// 指向根节点
            List<Element> list = root.getChildren();

            if (list != null && list.size() > 0) {
                for (Element element : list) {
					/*
					 * <xml>
						   <return_code><![CDATA[SUCCESS]]></return_code>
						   <return_msg><![CDATA[OK]]></return_msg>
						   <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
						   <mch_id><![CDATA[10000100]]></mch_id>
						   <nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>
						   <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>
						   <result_code><![CDATA[SUCCESS]]></result_code>
						   <prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>
						   <trade_type><![CDATA[JSAPI]]></trade_type>
						</xml>
					 */
                    System.out.println("key是：" + element.getName() + "，值是：" + element.getText());

                    if ("return_code".equals(element.getName())) {
                        unifieorderResult.setReturn_code(element.getText());
                    }

                    if ("return_msg".equals(element.getName())) {
                        unifieorderResult.setReturn_msg(element.getText());
                    }

                    if ("appid".equals(element.getName())) {
                        unifieorderResult.setAppid(element.getText());
                    }


                    if ("mch_id".equals(element.getName())) {
                        unifieorderResult.setMch_id(element.getText());
                    }

                    if ("nonce_str".equals(element.getName())) {
                        unifieorderResult.setNonce_str(element.getText());
                    }

                    if ("sign".equals(element.getName())) {
                        unifieorderResult.setSign(element.getText());
                    }

                    if ("result_code".equals(element.getName())) {
                        unifieorderResult.setResult_code(element.getText());
                    }

                    if ("prepay_id".equals(element.getName())) {
                        unifieorderResult.setPrepay_id(element.getText());
                    }

                    if ("trade_type".equals(element.getName())) {
                        unifieorderResult.setTrade_type(element.getText());
                    }
                }
            }

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return unifieorderResult;
    }


    /**
     * 2、微信回调后参数解析
     * 解析的时候自动去掉CDMA
     *
     * @param xml
     */
    @SuppressWarnings("unchecked")
    public static WXPayResult getWXPayResult(String xml) {
        WXPayResult wXPayResult = new WXPayResult();
        try {
            StringReader read = new StringReader(xml);
            // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
            // 创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
            // 通过输入源构造一个Document
            Document doc;
            doc = (Document) sb.build(source);

            Element root = doc.getRootElement();// 指向根节点
            List<Element> list = root.getChildren();

            if (list != null && list.size() > 0) {
                for (Element element : list) {
                    System.out.println("key是：" + element.getName() + "，值是：" + element.getText());

                    if ("return_code".equals(element.getName())) {
                        wXPayResult.setReturn_code(element.getText());
                    }

                    if ("return_msg".equals(element.getName())) {
                        wXPayResult.setReturn_msg(element.getText());//空值
                    }


                    if ("appid".equals(element.getName())) {
                        wXPayResult.setAppid(element.getText());
                    }

                    if ("mch_id".equals(element.getName())) {
                        wXPayResult.setMch_id(element.getText());
                    }

                    if ("nonce_str".equals(element.getName())) {
                        wXPayResult.setNonce_str(element.getText());
                    }

                    if ("sign".equals(element.getName())) {
                        wXPayResult.setSign(element.getText());
                    }

                    if ("cash_fee".equals(element.getName())) {
                        int a = Integer.parseInt((element.getText()));
                        wXPayResult.setCash_fee(a);
                    }

                    if ("fee_type".equals(element.getName())) {
                        wXPayResult.setFee_type(element.getText());
                    }

                    if ("is_subscribe".equals(element.getName())) {
                        wXPayResult.setIs_subscribe(element.getText());
                    }

                    if ("openid".equals(element.getName())) {
                        wXPayResult.setOpenid(element.getText());
                    }

                    if ("out_trade_no".equals(element.getName())) {
                        wXPayResult.setOut_trade_no(element.getText());
                    }

                    if ("time_end".equals(element.getName())) {
                        wXPayResult.setTime_end(element.getText());
                    }

                    if ("total_fee".equals(element.getName())) {
                        int b = Integer.parseInt(element.getText());
                        wXPayResult.setTotal_fee(b);
                    }

                    if ("trade_type".equals(element.getName())) {
                        wXPayResult.setTrade_type(element.getText());
                    }

                    if ("transaction_id".equals(element.getName())) {
                        wXPayResult.setTransaction_id(element.getText());
                    }
                    if ("attach".equals(element.getName())) {
                        wXPayResult.setAttach(element.getText());
                    }

                    if ("result_code".equals(element.getName())) {
                        wXPayResult.setResult_code(element.getText());
                    }

                    if ("is_subscribe".equals(element.getName())) {
                        wXPayResult.setIs_subscribe(element.getText());
                    }

                    if ("bank_type".equals(element.getName())) {
                        wXPayResult.setBank_type(element.getText());
                    }
                    if ("req_info".equals(element.getName())) {
                        wXPayResult.setReq_info(element.getText());
                    }
                }
            }

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wXPayResult;
    }

    public static WXPayResult getWXPayRefundResult(String xml) {
        WXPayResult wXPayResult = new WXPayResult();
        try {
            StringReader read = new StringReader(xml);
            // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
            // 创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
            // 通过输入源构造一个Document
            Document doc;
            doc = (Document) sb.build(source);

            Element root = doc.getRootElement();// 指向根节点
            List<Element> list = root.getChildren();

            if (list != null && list.size() > 0) {
                for (Element element : list) {
                    System.out.println("key是：" + element.getName() + "，值是：" + element.getText());

                    if ("out_refund_no".equals(element.getName())) {
                        wXPayResult.setOut_refund_no(element.getText());
                    }

                    if ("out_trade_no".equals(element.getName())) {
                        wXPayResult.setOut_trade_no(element.getText());//空值
                    }


                    if ("refund_account".equals(element.getName())) {
                        wXPayResult.setRefund_account(element.getText());
                    }

                    if ("refund_fee".equals(element.getName())) {
                        wXPayResult.setRefund_fee(element.getText());
                    }

                    if ("refund_id".equals(element.getName())) {
                        wXPayResult.setRefund_id(element.getText());
                    }

                    if ("refund_recv_accout".equals(element.getName())) {
                        wXPayResult.setRefund_recv_accout(element.getText());
                    }

                    if ("refund_request_source".equals(element.getName())) {
                        wXPayResult.setRefund_request_source(element.getText());
                    }

                    if ("refund_status".equals(element.getName())) {
                        wXPayResult.setRefund_status(element.getText());
                    }

                    if ("settlement_refund_fee".equals(element.getName())) {
                        wXPayResult.setSettlement_refund_fee(element.getText());
                    }

                    if ("success_time".equals(element.getName())) {
                        wXPayResult.setSuccess_time(element.getText());
                    }

                    if ("transaction_id".equals(element.getName())) {
                        wXPayResult.setTransaction_id(element.getText());
                    }

                    if ("total_fee".equals(element.getName())) {
                        int b = Integer.parseInt(element.getText());
                        wXPayResult.setTotal_fee(b);
                    }
                }
            }

        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return wXPayResult;
    }
}
