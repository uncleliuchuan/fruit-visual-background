package com.jclz.fruit.service.impl;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jclz.fruit.config.AlipayConfig;
import com.jclz.fruit.dao.FruitMapper;
import com.jclz.fruit.dao.OrderInfoMapper;
import com.jclz.fruit.dao.OrderRefundMapper;
import com.jclz.fruit.entity.OrderInfo;
import com.jclz.fruit.entity.OrderRefund;
import com.jclz.fruit.service.AlipayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service("alipayOrderService")
public class AlipayOrderServiceImpl implements AlipayOrderService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    FruitMapper fruitMapper;
    @Autowired
    OrderRefundMapper orderRefundMapper;

    private static String Body="超级果蔬--";

    @Override
    public String refound(String outTradeNo,String outRequestNo,String refundAmount) {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
                AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel bizModel = new AlipayTradeRefundModel();
        bizModel.setOutTradeNo(outTradeNo);
//        bizModel.setTradeNo(tradeNo);
        bizModel.setRefundAmount(refundAmount);
        bizModel.setOutRequestNo(outRequestNo);
        Boolean flag = new Boolean(false);
        request.setBizModel(bizModel);

        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);

            if (response.isSuccess()) {
                flag = true;
                System.out.println("成功");
                log.info("==================调用支付宝退款接口成功！");
                //修改订单状态为已退款
                     OrderRefund orderRefund = orderRefundMapper.selectByOutRefundNo(outRequestNo);
                    OrderInfo orderInfo = orderInfoMapper.selectByOrderNo(orderRefund.getOrderNo());
                     if (orderInfo.getOrderStatus() == 5 || orderInfo.getOrderStatus() == 6) {
                    orderInfo.setOrderStatus(7);//已退款
                    }
                    orderInfoMapper.updateOrderStatus(orderInfo);
                    return response.getBody();
                } else {
                    log.info("==================调用支付宝退款接口失败！");
                    flag = false;
                    return response.getBody();
                }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("异常");
            flag = false;
            return response.getBody();
        }
      }

    }
