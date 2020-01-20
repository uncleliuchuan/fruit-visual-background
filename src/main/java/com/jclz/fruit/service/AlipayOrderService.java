package com.jclz.fruit.service;


public interface AlipayOrderService {

    String refound(String outTradeNo, String outRequestNo, String refundAmount);
}
