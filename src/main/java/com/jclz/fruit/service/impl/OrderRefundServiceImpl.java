package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.OrderInfoMapper;
import com.jclz.fruit.dao.OrderRefundMapper;
import com.jclz.fruit.entity.OrderInfo;
import com.jclz.fruit.entity.OrderRefund;
import com.jclz.fruit.entity.Page;
import com.jclz.fruit.service.AlipayOrderService;
import com.jclz.fruit.service.OrderInitializingBean;
import com.jclz.fruit.service.OrderRefundService;
import com.jclz.fruit.utils.YouMengUtil;
import com.jclz.fruit.wxpay.util.WxPayCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("orderRefundService")
public class OrderRefundServiceImpl implements OrderRefundService {
    @Autowired
    OrderRefundMapper orderRefundMapper;
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    AlipayOrderService alipayOrderService;

    @Override
    public Page<OrderRefund> orderRefundList(String fruitName, String orderNo, Integer isHandle,Integer current, Integer size) {
        if (current == null) current = 1;
        if (size == null) size = 10;
        Integer start = size * (current - 1);
        Page<OrderRefund> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        if (null==isHandle){
        Integer total = orderRefundMapper.orderRefundListTotal(fruitName, orderNo,start, size);
        page.setTotal(total);
        page.setPages(total / size);
        List<OrderRefund> orderRefunds = orderRefundMapper.orderRefundList(fruitName, orderNo,start, size);
        page.setData(orderRefunds);
        }else if (0==isHandle){//未处理
            Integer total = orderRefundMapper.orderRefundIsHandleTotal(fruitName, orderNo,start, size);
            page.setTotal(total);
            page.setPages(total / size);
            List<OrderRefund> orderRefunds = orderRefundMapper.orderRefundIsHandle(fruitName, orderNo,start, size);
            page.setData(orderRefunds);
        }else if (1==isHandle){//已处理
            Integer total = orderRefundMapper.orderRefundHandleTotal(fruitName, orderNo,start, size);
            page.setTotal(total);
            page.setPages(total / size);
            List<OrderRefund> orderRefunds = orderRefundMapper.orderRefundHandle(fruitName, orderNo,start, size);
            page.setData(orderRefunds);
        }
        return page;
    }

    @Override
    public Map<String, Object> updateOrderRefund(Integer id, BigDecimal refundFee) {
        OrderRefund orderRefund = orderRefundMapper.selectByPrimaryKey(id);
        if (null==orderRefund){
            return GenResult.FAILED.genResult("退款申请不存在！！！");
        }
        OrderInfo orderInfo = orderInfoMapper.selectByOrderNo(orderRefund.getOrderNo());
        if (null==orderInfo){
            return GenResult.FAILED.genResult("订单不存在！！！");
        }
        if (6!=orderInfo.getOrderStatus()){
            return GenResult.FAILED.genResult("只有确认收货后申请退款可修改退款金额！！！");
        }
        if (refundFee.compareTo(orderInfo.getFruitAmountTotal())>0){
            return GenResult.FAILED.genResult("退款金额不能超过支付金额！！！");
        }
        orderRefund.setRefundFee(refundFee);
        orderRefundMapper.updateByPrimaryKeySelective(orderRefund);
        return GenResult.SUCCESS.genResult();
    }

    @Override
    public Map<String, Object> orderRefundIsAgree(Integer id, Integer isAgree) throws Exception {
        OrderRefund orderRefund = orderRefundMapper.selectByPrimaryKey(id);
        if (null==orderRefund){
            return GenResult.FAILED.genResult("退款申请不存在！！！");
        }
        if (null!=orderRefund.getIsAgree()){
            return GenResult.FAILED.genResult("退款已处理过，不能重复处理！！！");
        }
        OrderInfo orderInfo = orderInfoMapper.selectByOrderNo(orderRefund.getOrderNo());
        if (null==orderInfo){
            return GenResult.FAILED.genResult("订单不存在！！！");
        }
        orderRefund.setIsAgree(isAgree);

        if (1==isAgree){
            String out_refund_no = "refund" + UUID.randomUUID().toString().substring(0, 29);
            orderRefund.setOutRefundNo(out_refund_no);
            if ("weixin".equals(orderInfo.getPayType())){
                WxPayCore.createWxPayParamForRefund(orderInfo.getOuterTradeNo(), out_refund_no, orderInfo.getFruitAmountTotal().multiply(new BigDecimal(100)).intValueExact(),orderRefund.getRefundFee().multiply(new BigDecimal(100)).intValueExact());
            }else if ("alipay".equals(orderInfo.getPayType())){
                alipayOrderService.refound(orderInfo.getOuterTradeNo(), out_refund_no, orderRefund.getRefundFee().toString());
            }
            orderInfo.setOrderStatus(7);//已退款
            orderInfoMapper.updateOrderStatus(orderInfo);
            YouMengUtil.sendAndroidCustomizedcast(orderInfo.getCreateUser(),"您的退款申请已同意，退款金额将在24小时内退回原账户。","系统通知");
            YouMengUtil.sendIOSCustomizedcast(orderInfo.getCreateUser()+"","您的退款申请已同意，退款金额将在24小时内退回原账户。","系统通知");

        }else {
            orderInfo.setOrderStatus(8);//拒绝退款
            orderInfoMapper.updateOrderStatus(orderInfo);
        }
        OrderInitializingBean.orderRefundDelayQueueThread.removeDelay(orderInfo);//自动退款队列删除
        orderRefundMapper.updateByPrimaryKeySelective(orderRefund);
        return GenResult.SUCCESS.genResult();
    }
}

