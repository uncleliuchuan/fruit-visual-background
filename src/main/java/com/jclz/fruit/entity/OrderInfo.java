package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Data
public class OrderInfo {
    private Integer id;
    /**
     * 订单单号
     */
    private String orderNo;
    /**
     *商店编号
     */
    private Integer shopId;
    /**
     *水果数量
     */
    private Integer fruitCount;
    /**
     *水果总价
     */
    private BigDecimal fruitAmountTotal;
    /**
     *订单状态 待付款0,待发货1,待收货2，待评价3，已评价4，待发货申请退款5，确认收货后申请退款6，已退款7，拒绝退款8，付款失败-1
     */
    private Integer orderStatus;
    /**
     *订单创建时间
     */
    private String createTime;
    /**
     *创建用户
     */
    private Integer createUser;
    /**
     *水果ID
     */
    private Integer fruitId;
    /**
     *对一笔交易的具体描述信息。
     */
    private String body;
    /**
     *商品名称
     */
    private String fruitName;
    /**
     *快递单号
     */
    private String courierNumber;
    /**
     *物流名称
     */
    private String logisticsName;
    /**
     *用户地址ID
     */
    private Integer addressId;
    /**
     *支付类型，alipay-支付宝支付,weixin-微信支付
     */
    private String payType;
    /**
     *实付金额
     */
    private BigDecimal payAmount;
    /**
     *订单下单时间
     */
    private String orderTime;
    /**
     *订单支付时间
     */
    private String payTime;
    /**
     *发货时间
     */
    private String deliveryTime;
    /**
     *收货时间
     */
    private String confirmReceivingTime;
    /**
     *交易订单号，比如支付宝给我平台的订单号
     */
    private String outerTradeNo;

    /**
     * 支付过期时间
     */
    private String expirationTime;

    /**
     * 单位价格水果数量 例：1提/约3kg
     */
    private String fruitNum;

    /**
     * 首页图片
     */
    private String fruitPictureUrl;

    /**
     * 水果价格
     */
    private BigDecimal fruitPrice;

    /**
     *订单类型 0-普通订单/1-助力订单
     */
    private Integer orderInfoType;

    private String name;//收货人姓名

    private String phone;//收货人手机号

    private String address;//收货人详细地址

    private BigDecimal realIncome;//实际收入

    private String remarks;//备注


}