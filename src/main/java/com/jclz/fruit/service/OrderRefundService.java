package com.jclz.fruit.service;

import com.jclz.fruit.entity.OrderRefund;
import com.jclz.fruit.entity.Page;

import java.math.BigDecimal;
import java.util.Map;

public interface OrderRefundService {
    Page<OrderRefund> orderRefundList(String fruitName, String orderNo,Integer isHandle, Integer current, Integer size);

    Map<String, Object> updateOrderRefund(Integer id, BigDecimal refundFee);

    Map<String, Object> orderRefundIsAgree(Integer id, Integer isAgree) throws Exception;
}
