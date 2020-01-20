package com.jclz.fruit.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Data
public class Fruit {
    private Integer id;
    /**
     * 水果名称
     */
    private String fruitName;
    /**
     * 首页图片
     */
    private String fruitPictureUrl;
    /**
     * 水果价格
     */
    private BigDecimal fruitPrice;
    /**
     * 会员价
     */
    private BigDecimal fruitVipPrice;
    /**
     * 水果类型枚举
     */
    private Integer fruitType;
    /**
     * 详情页轮播图
     */
    private String fruitDetailCarousel;
    /**
     * 单位价格水果数量 例：1提/约3kg
     */
    private String fruitNum;
    /**
     * 水果已售数量
     */
    private Integer fruitAcceptedNum;
    /**
     * 水果产地 例：烟台
     */
    private String fruitOriginPlace;
    /**
     * 规格 例：1箱
     */
    private String fruitSpecifications;
    /**
     * 水果重量 例：16粒装
     */
    private String fruitWeight;
    /**
     * 水果包装 例：箱装
     */
    private String fruitPacking;
    /**
     * 保质期 例：7天
     */
    private String fruitQuality;
    /**
     * 储存方式 例：常温
     */
    private String fruitStorageMode;
    /**
     * 水果图片详情
     */
    private String fruitDetail;

    /**
     * 水果状态 -1下架/1上架
     */
    private Integer status;

}