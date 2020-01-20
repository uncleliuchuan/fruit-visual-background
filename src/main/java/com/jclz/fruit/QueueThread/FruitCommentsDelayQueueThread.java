package com.jclz.fruit.QueueThread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jclz.fruit.config.CFSConfig;
import com.jclz.fruit.dao.FruitCommentsMapper;
import com.jclz.fruit.dao.OrderInfoMapper;
import com.jclz.fruit.entity.FruitComments;
import com.jclz.fruit.entity.OrderDelayedTask;
import com.jclz.fruit.entity.OrderInfo;
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
public class FruitCommentsDelayQueueThread implements Runnable{

    private static FruitCommentsMapper fruitCommentsMapper;

    private static OrderInfoMapper orderService;

    private static RedisTemplate redisTemplate;

    private static AtomicBoolean isrun = new AtomicBoolean(true);

    private static DelayQueue delayQueue = new DelayQueue();

    public FruitCommentsDelayQueueThread(FruitCommentsMapper fruitCommentsMapper, OrderInfoMapper orderService, RedisTemplate redisTemplate) {
        super();
        this.fruitCommentsMapper = fruitCommentsMapper;
        this.orderService = orderService;
        this.redisTemplate = redisTemplate;
    }

    public static DelayQueue getDelayQueue() {
        return delayQueue;
    }

    public static void setDelayQueue(DelayQueue delayQueue) {
        FruitCommentsDelayQueueThread.delayQueue = delayQueue;
    }

    //订单数据存入redis和队列中并设置时间
    @SuppressWarnings("unchecked")
    public void add(String orderId) {
        Map<String, OrderDelayedTask> map = new HashMap<String, OrderDelayedTask>();
        OrderInfo findOrderOne = orderService.selectByOrderNo(orderId);
        String jsonString = JSON.toJSONString(findOrderOne);
        OrderDelayedTask orderDelayedTask=new OrderDelayedTask(jsonString,259200000);//3天
        map.put(orderId, orderDelayedTask);
        redisTemplate.opsForHash().putAll(CFSConfig.fruitCommentsDelayBean, map);//订单信息添加到redis中
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
                    if (3==tbOrder.getOrderStatus()) {// 判断订单是否是待评价的订单
                        OrderInfo findOrderOne = orderService.selectByOrderNo(tbOrder.getOrderNo());
                        if (3==findOrderOne.getOrderStatus()) {
                            findOrderOne.setOrderStatus(4);//修改订单状态为已评价
                            findOrderOne.setBody("评价超时系统自动评价!!!");
                            orderService.updateOrderStatus(findOrderOne);//修改订单的状态
                            FruitComments fruitComments=new FruitComments();
                            String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());//当前时间
                            fruitComments.setCreateTime(time);
                            fruitComments.setUserId(findOrderOne.getCreateUser());
                            fruitComments.setAppearance(5);
                            fruitComments.setTexture(5);
                            fruitComments.setCommentContent("系统默认好评！！！");
                            fruitComments.setFruitId(findOrderOne.getFruitId());
                            fruitComments.setOrderId(findOrderOne.getId());
                            fruitCommentsMapper.insertSelective(fruitComments);
                            redisTemplate.opsForHash().delete(CFSConfig.fruitCommentsDelayBean, tbOrder.getOrderNo());//删除redis中的数据
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
                    if (tbOrder.getOrderNo().equals(orderInfo.getOrderNo())){
                        redisTemplate.opsForHash().delete(CFSConfig.fruitCommentsDelayBean, tbOrder.getOrderNo());
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
