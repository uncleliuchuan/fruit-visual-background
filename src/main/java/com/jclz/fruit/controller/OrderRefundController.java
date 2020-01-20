package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.entity.OrderRefund;
import com.jclz.fruit.entity.Page;
import com.jclz.fruit.service.OrderRefundService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@Slf4j
@Api(tags = "用户退款API")
@RequestMapping("/orderRefund")
public class OrderRefundController {
    @Autowired
    OrderRefundService orderRefundService;
    /**
     * 退款列表
     */
    @PostMapping("/list")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "退款列表")
    public Map<String, Object> orderRefundList(@ApiParam(required = false, value = "水果名称") @RequestParam(required = false,value = "fruitName") String fruitName,
                                                @ApiParam(required = false, value = "订单编号") @RequestParam(required = false,value = "orderNo") String orderNo,
                                               @ApiParam(required = false, value = "是否处理 0未处理-1已处理") @RequestParam(required = false,value = "isHandle") Integer isHandle,
                                                @ApiParam(required = true, value = "当前页") @RequestParam Integer current,
                                                @ApiParam(required = true, value = "每页数据量") @RequestParam Integer size) {
        Page<OrderRefund> orderRefundPage = orderRefundService.orderRefundList(fruitName,orderNo,isHandle,current, size);
        return GenResult.SUCCESS.genResult(orderRefundPage);
    }
    /**
     * 退款金额修改
     */
    @PostMapping("/update")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "退款金额修改")
    public Map<String, Object> updateOrderRefund(@ApiParam(required = true, value = "退款ID") @RequestParam(value = "id") Integer id,
                                               @ApiParam(required = true, value = "退款金额") @RequestParam(value = "refundFee") BigDecimal refundFee) {
        return orderRefundService.updateOrderRefund(id,refundFee);
    }
    /**
     * 是否同意退款
     */
    @PostMapping("/isAgree")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "是否同意退款")
    public Map<String, Object> orderRefundIsAgree(@ApiParam(required = true, value = "退款ID") @RequestParam(value = "id") Integer id,
                                                  @ApiParam(required = true, value = "是否同意 0不同意-1同意") @RequestParam(value = "isAgree") Integer isAgree) {
        try {
            return orderRefundService.orderRefundIsAgree(id,isAgree);
        } catch (Exception e) {
            log.error("退款申请审核-----",e);
            return GenResult.FAILED.genResult();
        }
    }

}
