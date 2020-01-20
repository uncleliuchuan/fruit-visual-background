package com.jclz.fruit.QueueThread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jclz.fruit.config.CFSConfig;
import com.jclz.fruit.dao.OrderInfoMapper;
import com.jclz.fruit.dao.OrderRefundMapper;
import com.jclz.fruit.entity.OrderDelayedTask;
import com.jclz.fruit.entity.OrderInfo;
import com.jclz.fruit.entity.OrderRefund;
import com.jclz.fruit.service.AlipayOrderService;
import com.jclz.fruit.wxpay.util.WxPayCore;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 使用线程把订单数据存入redis和队列中并设置时间
 */
public class OrderRefundDelayQueueThread implements Runnable{

    private static OrderRefundMapper orderRefundMapper;

    private static OrderInfoMapper orderService;

    private static RedisTemplate redisTemplate;

    private static AlipayOrderService alipayOrderService;

    private static AtomicBoolean isrun = new AtomicBoolean(true);

    private static DelayQueue delayQueue = new DelayQueue();

    public OrderRefundDelayQueueThread(OrderRefundMapper orderRefundMapper, OrderInfoMapper orderService, RedisTemplate redisTemplate, AlipayOrderService alipayOrderService) {
        super();
        this.orderRefundMapper = orderRefundMapper;
        this.orderService = orderService;
        this.redisTemplate = redisTemplate;
        this.alipayOrderService = alipayOrderService;
    }

    public static DelayQueue getDelayQueue() {
        return delayQueue;
    }

    public static void setDelayQueue(DelayQueue delayQueue) {
        OrderRefundDelayQueueThread.delayQueue = delayQueue;
    }

    //订单数据存入redis和队列中并设置时间
    @SuppressWarnings("unchecked")
    public void add(String orderId) {
        Map<String, OrderDelayedTask> map = new HashMap<String, OrderDelayedTask>();
        OrderInfo findOrderOne = orderService.selectByOrderNo(orderId);
        String jsonString = JSON.toJSONString(findOrderOne);
        OrderDelayedTask orderDelayedTask=new OrderDelayedTask(jsonString,86400000);//1天
        map.put(orderId, orderDelayedTask);
        redisTemplate.opsForHash().putAll(CFSConfig.orderRefundDelayBean, map);//订单信息添加到redis中
        delayQueue.offer(orderDelayedTask);// 添加到队列中   5分种
    }

    @Override
    public void run() {
        try {
            while (isrun.get()) {
                Delayed take = delayQueue.take();//从队列中取出
                if (!StringUtils.isEmpty(take)) {
                    OrderDelayedTask orderDelayedTask = (OrderDelayedTask) take;
                    String orderJson = orderDelayedTask.getOrderJson();
                    OrderInfo tbOrder = JSONObject.parseObject(orderJson, OrderInfo.class);
                    if (5==tbOrder.getOrderStatus()) {// 判断订单是否是待发货申请退款的订单
                        OrderInfo findOrderOne = orderService.selectByOrderNo(tbOrder.getOrderNo());
                        if (5==findOrderOne.getOrderStatus()) {
                            String out_refund_no = "refund" + UUID.randomUUID().toString().substring(0, 29);
                            OrderRefund orderRefund = orderRefundMapper.selectByOrderNo(tbOrder.getOrderNo());
                            orderRefund.setOutRefundNo(out_refund_no);
                            orderRefund.setIsAgree(1);
                            orderRefundMapper.updateByPrimaryKeySelective(orderRefund);

                            if ("weixin".equals(findOrderOne.getPayType())){
                                WxPayCore.createWxPayParamForRefund(tbOrder.getOuterTradeNo(), out_refund_no, findOrderOne.getFruitAmountTotal().multiply(new BigDecimal(100)).intValueExact(), findOrderOne.getFruitAmountTotal().multiply(new BigDecimal(100)).intValueExact());
                            }else {
                                alipayOrderService.refound(tbOrder.getOuterTradeNo(), out_refund_no, findOrderOne.getFruitAmountTotal().toString());
                            }
                            findOrderOne.setOrderStatus(7);//已退款
                            orderService.updateOrderStatus(findOrderOne);
                            redisTemplate.opsForHash().delete(CFSConfig.orderRefundDelayBean, tbOrder.getOrderNo());//删除redis中的数据
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 删除队列中的数据(在3天内用户点击评价调用方法删除redis数据和队列中的数据不进行检测)
     *
     * @param tbOrder
     * @return
     */
    public static boolean removeDelay(OrderInfo tbOrder) {
        //用户点击确认收货，取出队列中的数据，删除redis种的订单，修改订单的状态
        if (isrun.get()) {
            Object[] objectArray = delayQueue.toArray();
            if (objectArray != null && objectArray.length > 0) {
                for (Object object : objectArray) {
                    OrderDelayedTask orderDelayedTask = (OrderDelayedTask) object;
                    String orderJson = orderDelayedTask.getOrderJson();
                    OrderInfo orderInfo = JSONObject.parseObject(orderJson, OrderInfo.class);
                    if (orderInfo.getOrderNo().equals(tbOrder.getOrderNo())){
                        redisTemplate.opsForHash().delete(CFSConfig.orderRefundDelayBean, tbOrder.getOrderNo());
                        boolean remove = delayQueue.remove(orderDelayedTask);
                        return remove;
                    }
                }
            }
        }
        return false;
    }

    public void putDelayQueueForRedis(OrderDelayedTask orderDelayedTask) {
        delayQueue.offer(orderDelayedTask);
    }
}
