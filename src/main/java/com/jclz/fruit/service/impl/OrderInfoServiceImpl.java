package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.OrderInfoMapper;
import com.jclz.fruit.dao.OrderRefundMapper;
import com.jclz.fruit.entity.OrderInfo;
import com.jclz.fruit.entity.OrderRefund;
import com.jclz.fruit.entity.Page;
import com.jclz.fruit.service.OrderInfoService;
import com.jclz.fruit.service.OrderInitializingBean;
import com.jclz.fruit.utils.TimeUtils;
import com.jclz.fruit.utils.YouMengUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    OrderRefundMapper orderRefundMapper;
    @Override
    public Page<OrderInfo> orderInfoList(String fruitName, String orderNo,Integer orderStatus, Integer current, Integer size) {
        if (current == null) current = 1;
        if (size == null) size = 10;
        Integer start = size * (current - 1);
        Page<OrderInfo> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        Integer total = orderInfoMapper.orderInfoListTotal(fruitName, orderNo, orderStatus,start, size);
        page.setTotal(total);
        page.setPages(total / size);
        List<OrderInfo> orderInfos = orderInfoMapper.orderInfoList(fruitName, orderNo, orderStatus,start, size);
        page.setData(orderInfos);
        return page;
    }

    @Override
    public Page<OrderInfo> signedList(String fruitName, String orderNo, Integer current, Integer size) {
        if (current == null) current = 1;
        if (size == null) size = 10;
        Integer start = size * (current - 1);
        Page<OrderInfo> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        Integer total = orderInfoMapper.signedListTotal(fruitName, orderNo,start, size);
        page.setTotal(total);
        page.setPages(total / size);
        List<OrderInfo> orderInfos = orderInfoMapper.signedList(fruitName, orderNo,start, size);
        page.setData(orderInfos);
        return page;
    }

    @Override
    public Map<String, Object> deliverGoods(String orderNo) throws Exception {
        OrderInfo orderInfo = orderInfoMapper.selectByOrderNo(orderNo);
        if (null==orderInfo){
            return GenResult.FAILED.genResult("订单不存在！！！");
        }
        if (1!=orderInfo.getOrderStatus()){
            return GenResult.FAILED.genResult("只有待发货订单能做发货操作！！！");
        }
        orderInfo.setOrderStatus(2);
        orderInfo.setDeliveryTime(TimeUtils.getTime());
        orderInfoMapper.updateOrderStatus(orderInfo);
        OrderInitializingBean.OrderDelayQueueThread.add(orderNo);//自动收货队列添加
        YouMengUtil.sendAndroidCustomizedcast(orderInfo.getCreateUser(),"您的订单已发货，请耐心等待，app内还有更多新鲜果蔬在等您挑选(＾－＾)V","系统通知");
        YouMengUtil.sendIOSCustomizedcast(orderInfo.getCreateUser()+"","您的订单已发货，请耐心等待，app内还有更多新鲜果蔬在等您挑选(＾－＾)V","系统通知");
        return GenResult.SUCCESS.genResult();
    }

    @Override
    public Page<OrderInfo> billList(String fruitName, String orderNo, Integer current, Integer size) {
        if (current == null) current = 1;
        if (size == null) size = 10;
        Integer start = size * (current - 1);
        Page<OrderInfo> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        Integer total = orderInfoMapper.billListTotal(fruitName, orderNo,start, size);
        page.setTotal(total);
        page.setPages(total / size);
        List<OrderInfo> orderInfos = orderInfoMapper.billList(fruitName, orderNo,start, size);
        for (OrderInfo orderInfo: orderInfos) {
            OrderRefund orderRefund = orderRefundMapper.selectByOrderNo(orderInfo.getOrderNo());
            if (3==orderInfo.getOrderStatus()||4==orderInfo.getOrderStatus()){
                orderInfo.setRealIncome(orderInfo.getFruitAmountTotal());
                orderInfo.setRemarks("已签收");
            }else if (7==orderInfo.getOrderStatus()){
                orderInfo.setRealIncome(orderInfo.getFruitAmountTotal().subtract(orderRefund.getRefundFee()));
                orderInfo.setRemarks(orderRefund.getRefundReason());
            }else if (8==orderInfo.getOrderStatus()){
                orderInfo.setRealIncome(orderInfo.getFruitAmountTotal());
                orderInfo.setRemarks(orderRefund.getRefundReason());
            }
        }
        page.setData(orderInfos);
        return page;
    }
}
