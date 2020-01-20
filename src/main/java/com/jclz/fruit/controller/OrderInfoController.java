package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.entity.OrderInfo;
import com.jclz.fruit.entity.Page;
import com.jclz.fruit.service.OrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@Api(tags = "订单API")
@RequestMapping("/orderInfo")
public class OrderInfoController {
    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 订单列表
     * @param current
     * @return
     */
    @RequestMapping("/list")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "订单列表")
    public Map<String, Object> orderInfoList(@ApiParam(required = false, value = "水果名称") @RequestParam(required = false,value = "fruitName") String fruitName,
                                             @ApiParam(required = false, value = "订单编号") @RequestParam(required = false,value = "orderNo") String orderNo,
                                             @ApiParam(required = false, value = "订单状态待发货1,待收货2，待评价3，已评价4") @RequestParam(required = false,value = "orderStatus") Integer orderStatus,
                                             @ApiParam(required = true, value = "当前页") @RequestParam Integer current,
                                             @ApiParam(required = true, value = "每页数据量") @RequestParam Integer size){

        Page<OrderInfo> fruitList = orderInfoService.orderInfoList(fruitName,orderNo,orderStatus, current, size);
        return GenResult.SUCCESS.genResult(fruitList);

    }
    /**
     * 已签收列表
     * @param current
     * @return
     */
    @RequestMapping("/signedList")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "已签收列表")
    public Map<String, Object> signedList(@ApiParam(required = false, value = "水果名称") @RequestParam(required = false,value = "fruitName") String fruitName,
                                             @ApiParam(required = false, value = "订单编号") @RequestParam(required = false,value = "orderNo") String orderNo,
                                             @ApiParam(required = true, value = "当前页") @RequestParam Integer current,
                                             @ApiParam(required = true, value = "每页数据量") @RequestParam Integer size){

        Page<OrderInfo> fruitList = orderInfoService.signedList(fruitName,orderNo,current, size);
        return GenResult.SUCCESS.genResult(fruitList);

    }

    /**
     * 待发货订单发货
     */
    @PostMapping("/deliverGoods")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "待发货订单发货")
    public Map<String, Object> deliverGoods(@ApiParam(required = true, value = "订单编号") @RequestParam(value = "orderNo") String orderNo) {
        try {
            return orderInfoService.deliverGoods(orderNo);
        } catch (Exception e) {
            log.error("待发货订单发货----",e);
            return GenResult.FAILED.genResult();
        }
    }

    /**
     * 账单列表
     * @param current
     * @return
     */
    @RequestMapping("/billList")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "账单列表")
    public Map<String, Object> billList(@ApiParam(required = false, value = "水果名称") @RequestParam(required = false,value = "fruitName") String fruitName,
                                             @ApiParam(required = false, value = "订单编号") @RequestParam(required = false,value = "orderNo") String orderNo,
                                             @ApiParam(required = true, value = "当前页") @RequestParam Integer current,
                                             @ApiParam(required = true, value = "每页数据量") @RequestParam Integer size) {

        Page<OrderInfo> fruitList = orderInfoService.billList(fruitName, orderNo, current, size);
        return GenResult.SUCCESS.genResult(fruitList);
    }

}
