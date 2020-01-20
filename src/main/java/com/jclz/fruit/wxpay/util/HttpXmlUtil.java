package com.jclz.fruit.wxpay.util;


import com.jclz.fruit.wxpay.model.Unifiedorder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;

/**
 * post提交xml格式的参数
 */
public class HttpXmlUtil {

    /**
     * 开始post提交参数到接口
     * 并接受返回
     *
     * @param url
     * @param xml
     * @param method
     * @param contentType
     * @return
     */
    public static String xmlHttpProxy(String url, String xml, String method, String contentType) {
        InputStream is = null;
        OutputStreamWriter os = null;

        try {
            URL _url = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "text/xml");
            conn.setRequestProperty("Pragma:", "no-cache");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestMethod("POST");
            os = new OutputStreamWriter(conn.getOutputStream());
            os.write(new String(xml.getBytes(contentType)));
            os.flush();

            //返回值
            is = conn.getInputStream();
            return getContent(is, "utf-8");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析返回的值
     *
     * @param is
     * @param charset
     * @return
     */
    public static String getContent(InputStream is, String charset) {
        String pageString = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            isr = new InputStreamReader(is, charset);
            br = new BufferedReader(isr);
            sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            pageString = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb = null;
        }
        return pageString;
    }

    /**
     * 构造xml参数
     *
     * @param
     * @return
     */
    public static String xmlInfo(Unifiedorder unifiedorder) {
        //构造xml参数的时候，至少有10个必传参数
		/*
		 * <xml>
			   <appid>wx2421b1c4370ec43b</appid>
			   <body>APP支付测试</body>
			   <mch_id>10000100</mch_id>
			   <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>
			   <notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php</notify_url>
			   <out_trade_no>1415659990</out_trade_no>
			   <spbill_create_ip>14.23.150.211</spbill_create_ip>
			   <total_fee>1</total_fee>
			   <trade_type>APP</trade_type>
			   <sign>0CB01533B8C1EF103065174F50BCA001</sign>
			</xml> 
		 */

        if (unifiedorder != null) {
            StringBuffer bf = new StringBuffer();
            bf.append("<xml>");

            bf.append("<appid><![CDATA[");
            bf.append(unifiedorder.getAppid());
            bf.append("]]></appid>");

            bf.append("<mch_id><![CDATA[");
            bf.append(unifiedorder.getMch_id());
            bf.append("]]></mch_id>");

            bf.append("<nonce_str><![CDATA[");
            bf.append(unifiedorder.getNonce_str());
            bf.append("]]></nonce_str>");

            bf.append("<sign><![CDATA[");
            bf.append(unifiedorder.getSign());
            bf.append("]]></sign>");

            bf.append("<body><![CDATA[");
            bf.append(unifiedorder.getBody());
            bf.append("]]></body>");

            bf.append("<out_trade_no><![CDATA[");
            bf.append(unifiedorder.getOut_trade_no());
            bf.append("]]></out_trade_no>");

            bf.append("<total_fee><![CDATA[");
            bf.append(unifiedorder.getTotal_fee());
            bf.append("]]></total_fee>");

            bf.append("<spbill_create_ip><![CDATA[");
            bf.append(unifiedorder.getSpbill_create_ip());
            bf.append("]]></spbill_create_ip>");

            bf.append("<notify_url><![CDATA[");
            bf.append(unifiedorder.getNotify_url());
            bf.append("]]></notify_url>");

            bf.append("<trade_type><![CDATA[");
            bf.append(unifiedorder.getTrade_type());
            bf.append("]]></trade_type>");


            bf.append("</xml>");
			/*
			bf.append("<time_start><![CDATA[");
			bf.append(unifiedorder.getTime_start());
			bf.append("]]></time_start>");

			bf.append("<time_expire><![CDATA[");
			bf.append(unifiedorder.getTime_expire());
			bf.append("]]></time_expire>");
			
			bf.append("<detail><![CDATA[");
			bf.append(unifiedorder.getDetail());
			bf.append("]]></detail>");

			bf.append("<attach><![CDATA[");
			bf.append(unifiedorder.getAttach());
			bf.append("]]></attach>");
			*/
            return bf.toString();
        }

        return "";
    }


    /**
     * post请求并得到返回结果
     *
     * @param requestUrl
     * @param requestMethod
     * @param output
     * @return
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String output) {
        try {
            URL url = new URL(requestUrl);
            //打开到此 URL 的连接并返回一个用于从该连接读入的 InputStream
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            //如果打算使用 URL 连接进行输出，则将 DoOutput 标志设置为 true
            connection.setDoOutput(true);
            //打算使用 URL 连接进行输入，则将 DoInput 标志设置为 true
            connection.setDoInput(true);
            /*
             * 有些协议用于文档缓存。有时候能够进行“直通”并忽略缓存尤其重要，
             * 例如浏览器中的“重新加载”按钮。如果连接中的 UseCaches 标志为 true，
             * 则允许连接使用任何可用的缓存。如果为 false，则忽略缓存。
             * 默认值来自 DefaultUseCaches，它默认为 true。
             */
            connection.setUseCaches(false);
            //设置 URL 请求的方法，
            connection.setRequestMethod(requestMethod);
            if (null != output) {
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(output.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            //指示近期服务器不太可能有其他请求。调用 disconnect() 并不意味着可以对其他请求重用此 HttpURLConnection 实例。
            connection.disconnect();
            return buffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

    /**
     * 回调后将结果返回给微信
     *
     * @param return_code
     * @param return_msg
     * @return
     */
    public static String backWeixin(String return_code, String return_msg) {
        try {
            StringBuffer bf = new StringBuffer();
            bf.append("<xml>");

            bf.append("<return_code><![CDATA[");
            bf.append(return_code);
            bf.append("]]></return_code>");

            bf.append("<return_msg><![CDATA[");
            bf.append(return_msg);
            bf.append("]]></return_msg>");

            bf.append("</xml>");
            return bf.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }


    /**
     * SSL代码
     */
    public static String postSSL(String url, String data, String certPath, String certPass) {
        try {
            KeyStore clientStore = KeyStore.getInstance("PKCS12");
            // 读取本机存放的PKCS12证书文件
            FileInputStream instream = new FileInputStream(certPath);
            try {
                // 指定PKCS12的密码(商户ID)
                clientStore.load(instream, certPass.toCharArray());
            } finally {
                instream.close();
            }
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(clientStore, certPass.toCharArray()).build();
            // 指定TLS版本
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            // 设置httpclient的SSLSocketFactory
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            try {
                HttpPost httpost = new HttpPost(url); // 设置响应头信息
                httpost.addHeader("Connection", "keep-alive");
                httpost.addHeader("Accept", "*/*");
//                httpost.addHeader("Content-Type", CONTENT_TYPE_FORM.toString());
                httpost.addHeader("X-Requested-With", "XMLHttpRequest");
                httpost.addHeader("Cache-Control", "max-age=0");
//                httpost.addHeader("User-Agent", DEFAULT_USER_AGENT);
                httpost.setEntity(new StringEntity(data, "UTF-8"));
                CloseableHttpResponse response = httpclient.execute(httpost);
                try {
                    HttpEntity entity = response.getEntity();
                    String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                    EntityUtils.consume(entity);
                    return jsonStr;
                } finally {
                    response.close();
                }
            } finally {
                httpclient.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
