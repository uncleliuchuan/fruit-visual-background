package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class FruitComments {
    private Integer id;
    /**
     * 水果id
     */
    private Integer fruitId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 口感
     */
    private Integer texture;
    /**
     * 颜值
     */
    private Integer appearance;
    /**
     * 评论内容
     */
    private String commentContent;
    /**
     * 评论图片
     */
    private String commentUrl;
    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 订单ID
     */
    private Integer orderId;


}