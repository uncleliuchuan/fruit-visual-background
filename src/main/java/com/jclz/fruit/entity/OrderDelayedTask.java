package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Component
@Data
public class OrderDelayedTask implements Delayed, Serializable {

    private String orderJson;
    private long start = System.currentTimeMillis();//开始时间
    private long time;


    public OrderDelayedTask(String orderJson, long time) {
        super();
        this.orderJson = orderJson;
        this.time = time;
    }
    public OrderDelayedTask() {
    }
    public String getOrderJson() {
        return orderJson;
    }

    public void setOrderJson(String orderJson) {
        this.orderJson = orderJson;
    }

    @Override
    public String toString() {
        return "OrderDelayedTask [orderJson=" + orderJson + ", start=" + start + ", time=" + time + "]";
    }


    /**
     * 需要实现的接口，获得延迟时间   用过期时间-当前时间
     *
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert((start + time) - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 用于延迟队列内部比较排序   当前时间的延迟时间 - 比较对象的延迟时间
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        OrderDelayedTask o1 = (OrderDelayedTask) o;
        return (int) (this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

}