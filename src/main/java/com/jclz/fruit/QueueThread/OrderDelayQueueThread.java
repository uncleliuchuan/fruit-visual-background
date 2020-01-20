package com.jclz.fruit.QueueThread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jclz.fruit.config.CFSConfig;
import com.jclz.fruit.dao.OrderInfoMapper;
import com.jclz.fruit.entity.OrderDelayedTask;
import com.jclz.fruit.entity.OrderInfo;
import com.jclz.fruit.service.OrderInitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 使用线程把订单数据存入redis和队列中并设置时间
 */
public class OrderDelayQueueThread implements Runnable {

    private static OrderInfoMapper orderService;

    private static RedisTemplate redisTemplate;

    private static AtomicBoolean isrun = new AtomicBoolean(true);

    private static DelayQueue delayQueue = new DelayQueue();

    public OrderDelayQueueThread(OrderInfoMapper orderService, RedisTemplate redisTemplate) {
        super();
        this.orderService = orderService;
        this.redisTemplate = redisTemplate;
    }

    public static DelayQueue getDelayQueue() {
        return delayQueue;
    }

    public static void setDelayQueue(DelayQueue delayQueue) {
        OrderDelayQueueThread.delayQueue = delayQueue;
    }

    //订单数据存入redis和队列中并设置时间
    @SuppressWarnings("unchecked")
    public void add(String orderId) {
        Map<String, OrderDelayedTask> map = new HashMap<String, OrderDelayedTask>();
        OrderInfo findOrderOne = orderService.selectByOrderNo(orderId);
        String jsonString = JSON.toJSONString(findOrderOne);
        OrderDelayedTask orderDelayedTask = new OrderDelayedTask(jsonString, 604800000);//7天
        map.put(orderId, orderDelayedTask);
        redisTemplate.opsForHash().putAll(CFSConfig.orderDelayBean, map);//订单信息添加到redis中
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
                    if (2==tbOrder.getOrderStatus()) {// 判断订单是否是已经发货的订单
                        OrderInfo findOrderOne = orderService.selectByOrderNo(tbOrder.getOrderNo());
                        if (2==findOrderOne.getOrderStatus()) {
                            findOrderOne.setOrderStatus(3);//修改订单状态为已经收货
                            findOrderOne.setBody("收货超时系统自动收货!!!");
                            String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//当前时间
                            findOrderOne.setConfirmReceivingTime(time);//收货时间
                            orderService.updateOrderStatus(findOrderOne);
                            redisTemplate.opsForHash().delete(CFSConfig.orderDelayBean, tbOrder.getOrderNo());//删除redis中的数据
                            OrderInitializingBean.fruitCommentsDelayQueueThread.add(tbOrder.getOrderNo());//默认评价队列添加
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除队列中的数据(在7天内用户点击收货调用方法删除redis数据和队列中的数据不进行检测)
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
                        redisTemplate.opsForHash().delete(CFSConfig.orderDelayBean, tbOrder.getOrderNo());
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
