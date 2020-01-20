package com.jclz.fruit.wxpay.util;


import com.jclz.fruit.wxpay.model.UnifiedorderResult;
import com.jclz.fruit.wxpay.model.UnifiedorderSelectResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.Iterator;
import java.util.List;


public class ParseXMLUtils {

    private static Logger log = Logger.getLogger(ParseXMLUtils.class);

    /**
     * 1、DOM解析
     */
    public static void beginXMLParse(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML

            Element rootElt = doc.getRootElement(); // 获取根节点smsReport

            System.out.println("根节点是：" + rootElt.getName());

            Iterator iters = rootElt.elementIterator("sendResp"); // 获取根节点下的子节点sms

            while (iters.hasNext()) {
                Element recordEle1 = (Element) iters.next();
                Iterator iter = recordEle1.elementIterator("sms");

                while (iter.hasNext()) {
                    Element recordEle = (Element) iter.next();
                    String phone = recordEle.elementTextTrim("phone"); // 拿到sms节点下的子节点stat值

                    String smsID = recordEle.elementTextTrim("smsID"); // 拿到sms节点下的子节点stat值

                    System.out.println(phone + ":" + smsID);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2、DOM4j解析XML（支持xpath）
     * 解析的时候自动去掉CDMA
     *
     * @param xml
     */
    public static void xpathParseXml(String xml) {
        try {
            StringReader read = new StringReader(xml);
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(read);
            String xpath = "/xml/appid";
            System.out.print(doc.selectSingleNode(xpath).getText());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 3、JDOM解析XML
     * 解析的时候自动去掉CDMA
     *
     * @param xml
     */
    public static UnifiedorderResult jdomParseXml(String xml) {
        UnifiedorderResult unifieorderResult = new UnifiedorderResult();
        try {
            StringReader read = new StringReader(xml);
            // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
            // 创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
            // 通过输入源构造一个Document
            org.jdom.Document doc;
            doc = (org.jdom.Document) sb.build(source);

            org.jdom.Element root = doc.getRootElement();// 指向根节点
            List<org.jdom.Element> list = root.getChildren();

            if (list != null && list.size() > 0) {
                boolean flag1 = true;
                boolean flag2 = true;
                for (org.jdom.Element element : list) {
                    log.info("key是：" + element.getName() + "，值是：" + element.getText());

                    if ("return_code".equals(element.getName())) {
                        if ("FAIL".equals(element.getText())) {
                            flag1 = false;
                        } else {
                            unifieorderResult.setReturn_code(element.getText());
                        }
                    }

                    if ("return_msg".equals(element.getName())) {
                        if (element.getText() != null && !"OK".equals(element.getText())) {//微信支付的第一个坑，这里返回了OK，23333
                            log.error("统一下单参数有误，错误原因为:" + element.getText());
                            return null;
                        }
                    }

                    if (flag1) {
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
                        if ("err_code".equals(element.getName())) {
                            unifieorderResult.setErr_code(element.getText());
                        }
                        if ("err_code_des".equals(element.getName())) {
                            unifieorderResult.setErr_code_des(element.getText());
                        }
                        if ("result_code".equals(element.getName())) {
                            if ("FAIL".equals(element.getText())) {
                                flag2 = false;
                                log.error("统一下单业务结果有误，无法返回预支付交易会话标识");
                            } else {
                                unifieorderResult.setResult_code(element.getText());
                            }
                        }
                    }
                    if (flag1 && flag2 && flag2 == true) {
                        if ("trade_type".equals(element.getName())) {
                            unifieorderResult.setTrade_type(element.getText());
                        }
                        if ("prepay_id".equals(element.getName())) {
                            log.info("统一下单接口成功返回预支付交易会话标识！");
                            unifieorderResult.setPrepay_id(element.getText());
                        }
                    }

                }
                return unifieorderResult;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean parseInt(String key) {
        if (!StringUtils.isEmpty(key)) {
            if (key.equals("total_fee") || key.equals("cash_fee") || key.equals("coupon_fee") || key.equals("coupon_count") || key.equals("coupon_fee_0")) {
                return true;
            }
        }

        return false;
    }

    /**
     * JDOM解析XML
     * 解析的时候自动去掉CDMA
     *
     * @param xml 微信查询订单返回的xml
     * @return
     */
    public static UnifiedorderSelectResult jdomParseSelectXml(String xml) {
        UnifiedorderSelectResult unifiedorderSelectResult = new UnifiedorderSelectResult();
        try {
            StringReader read = new StringReader(xml);
            // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
            InputSource source = new InputSource(read);
            // 创建一个新的SAXBuilder
            SAXBuilder sb = new SAXBuilder();
            // 通过输入源构造一个Document
            org.jdom.Document doc;
            doc = (org.jdom.Document) sb.build(source);

            org.jdom.Element root = doc.getRootElement();// 指向根节点
            List<org.jdom.Element> list = root.getChildren();

            if (list != null && list.size() > 0) {
                boolean flag1 = true;
                boolean flag2 = true;
                for (org.jdom.Element element : list) {
                    log.info("key是：" + element.getName() + "，值是：" + element.getText());

                    if ("return_code".equals(element.getName())) {
                        if ("FAIL".equals(element.getText())) {
                            flag1 = false;
                        } else {
                            unifiedorderSelectResult.setReturn_code(element.getText());
                        }
                    }

                    if ("return_msg".equals(element.getName())) {
                        if (element.getText() != null && !"OK".equals(element.getText())) {//微信支付的第一个坑，这里返回了OK，23333
                            log.error("查询订单参数有误，错误原因为:" + element.getText());
                            return null;
                        }
                    }

                    if (flag1) {
                        if ("appid".equals(element.getName())) {
                            unifiedorderSelectResult.setAppid(element.getText());
                        }
                        if ("mch_id".equals(element.getName())) {
                            unifiedorderSelectResult.setMch_id(element.getText());
                        }
                        if ("nonce_str".equals(element.getName())) {
                            unifiedorderSelectResult.setNonce_str(element.getText());
                        }
                        if ("sign".equals(element.getName())) {
                            unifiedorderSelectResult.setSign(element.getText());
                        }
                        if ("result_ code".equals(element.getName())) {
                            unifiedorderSelectResult.setResult_code(element.getText());
                        }
                        if ("err_code".equals(element.getName())) {
                            unifiedorderSelectResult.setErr_code(element.getText());
                        }
                        if ("err_code_des".equals(element.getName())) {
                            unifiedorderSelectResult.setErr_code_des(element.getText());
                        }
                        if ("result_code".equals(element.getName())) {
                            if ("FAIL".equals(element.getText())) {
                                flag2 = false;
                                log.error("查询订单业务失败，无法返回查询结果");
                            } else {
                                unifiedorderSelectResult.setResult_code(element.getText());
                            }
                        }
                    }
                    if (flag1 && flag2 && flag2 == true) {
                        if ("trade_type".equals(element.getName())) {
                            unifiedorderSelectResult.setTrade_type(element.getText());
                        }
                        if ("device_info".equals(element.getName())) {
                            unifiedorderSelectResult.setDevice_info(element.getText());
                        }
                        if ("open_id".equals(element.getName())) {
                            unifiedorderSelectResult.setOpenid(element.getText());
                        }
                        if ("trade_state".equals(element.getName())) {
                            unifiedorderSelectResult.setTrade_state(element.getText());
                        }
                        if ("total_fee".equals(element.getName())) {
                            unifiedorderSelectResult.setTotal_fee(Integer.parseInt(element.getText()));
                        }
                        if ("transaction_id".equals(element.getName())) {
                            unifiedorderSelectResult.setTransaction_id(element.getText());
                        }
                        if ("time_end".equals(element.getName())) {
                            unifiedorderSelectResult.setTime_end(element.getText());
                        }
                        if ("trade_state_desc".equals(element.getName())) {
                            unifiedorderSelectResult.setTrade_state_desc(element.getText());
                        }
                        if ("out_refund_no".equals(element.getName())) {
                            unifiedorderSelectResult.setOut_trade_no(element.getText());
                        }
                        if ("refund_id".equals(element.getName())) {
                            unifiedorderSelectResult.setRefund_id(element.getText());
                        }
                        if ("refund_fee".equals(element.getName())) {
                            unifiedorderSelectResult.setRefund_fee(Integer.parseInt(element.getText()));
                        }
                    }

                }
                return unifiedorderSelectResult;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}
