package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
public class OrderRefund {
    private Integer id;

    private String orderNo;//订单编号

    private String outRefundNo;//商户退款单号

    private BigDecimal refundFee;//退款金额

    private String createTime;//申请时间

    private Integer isAgree;//是否同意退款 0-拒绝 /1-同意

    private String fruitStatus;//货物状态

    private String refundReason;//退款原因

    private String refundInstruction;//退款说明

    private String refundUrl;//退款凭证

    private Integer userId;//用户ID

    /**
     *商品名称
     */
    private String fruitName;

    /**
     *订单状态 待付款0,待发货1,待收货2，待评价3，已评价4，待发货申请退款5，确认收货后申请退款6，已退款7，拒绝退款8，付款失败-1
     */
    private Integer orderStatus;


    private String phone;//收货人手机号


   }