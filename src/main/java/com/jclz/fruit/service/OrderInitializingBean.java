package com.jclz.fruit.service;

import com.jclz.fruit.QueueThread.FruitCommentsDelayQueueThread;
import com.jclz.fruit.QueueThread.OrderDelayQueueThread;
import com.jclz.fruit.QueueThread.OrderRefundDelayQueueThread;
import com.jclz.fruit.config.CFSConfig;
import com.jclz.fruit.dao.FruitCommentsMapper;
import com.jclz.fruit.dao.OrderInfoMapper;
import com.jclz.fruit.dao.OrderRefundMapper;
import com.jclz.fruit.entity.OrderDelayedTask;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 系统启动时查询redis中两个队列数据并添加到DelayQueue队列
 */
@Service("orderInitializingBean")
public class OrderInitializingBean implements InitializingBean {

    public static OrderDelayQueueThread OrderDelayQueueThread = null;

    public static FruitCommentsDelayQueueThread fruitCommentsDelayQueueThread = null;

    public static OrderRefundDelayQueueThread orderRefundDelayQueueThread = null;


    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderInfoMapper orderService;

    @Autowired
    private FruitCommentsMapper fruitCommentsMapper;

    @Autowired
    private OrderRefundMapper orderRefundMapper;
    @Autowired
    AlipayOrderService alipayOrderService;

    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() throws Exception {
        if (OrderDelayQueueThread == null) {
            OrderDelayQueueThread = new OrderDelayQueueThread(orderService, redisTemplate);
            Thread thread = new Thread(OrderDelayQueueThread);
            thread.start();
        }
        if (fruitCommentsDelayQueueThread == null) {
            fruitCommentsDelayQueueThread = new FruitCommentsDelayQueueThread(fruitCommentsMapper,orderService, redisTemplate);
            Thread thread = new Thread(fruitCommentsDelayQueueThread);
            thread.start();
        }
        if (orderRefundDelayQueueThread == null) {
            orderRefundDelayQueueThread = new OrderRefundDelayQueueThread(orderRefundMapper,orderService, redisTemplate,alipayOrderService);
            Thread thread = new Thread(orderRefundDelayQueueThread);
            thread.start();
        }

    }
}

