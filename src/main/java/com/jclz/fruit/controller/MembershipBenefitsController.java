package com.jclz.fruit.controller;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.entity.MembershipBenefits;
import com.jclz.fruit.entity.Page;
import com.jclz.fruit.service.MembershipBenefitsService;
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
@Api(tags = "会员福利API")
@RequestMapping("/membershipBenefits")
public class MembershipBenefitsController {
    @Autowired
    MembershipBenefitsService membershipBenefitsService;

    @RequestMapping("/list")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "会员福利列表")
    public  Map<String,Object> membershipBenefitsList(@ApiParam(required = false, value = "水果名称") @RequestParam(required = false,value = "fruitName") String fruitName,
                                                      @ApiParam(required = false, value = "水果类型") @RequestParam(required = false,value = "fruitType") Integer fruitType,
                                                      @ApiParam(required = true, value = "当前页") @RequestParam Integer current,
                                                      @ApiParam(required = true, value = "每页数据量") @RequestParam Integer size){

        Page<MembershipBenefits> membershipBenefitsList = membershipBenefitsService.membershipBenefitsList(fruitName,fruitType, current, size);
        return GenResult.SUCCESS.genResult(membershipBenefitsList);

    }
    @RequestMapping("/detial")
    @ApiOperation(httpMethod = "POST",responseContainer = "Map",response = Map.class,value = "会员福利详情")
    public  Map<String,Object> membershipBenefitsDetial(@ApiParam(required = true, value = "会员福利ID") @RequestParam(value = "id") Integer id){
        if (null==id){
            return GenResult.PARAMS_ERROR.genResult();
        }
                return membershipBenefitsService.membershipBenefitsDetial(id);

    }
    /**
     * 会员福利
     *
     * @param membershipBenefits 水果信息
     * @return
     */
    @PostMapping("/update")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "更新会员福利")
    public Map<String, Object> updateMembershipBenefits(@ApiParam(value = "会员福利") MembershipBenefits membershipBenefits) {

        return membershipBenefitsService.updateMembershipBenefits(membershipBenefits);

    }
    /**
     * 会员福利
     *
     * @param membershipBenefits 会员福利信息
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "添加会员福利")
    public Map<String, Object> addMembershipBenefits(@ApiParam(value = "会员福利") MembershipBenefits membershipBenefits) {

        return membershipBenefitsService.addMembershipBenefits(membershipBenefits);
    }
    /**
     * 未发布的水果id列表
     *
     * @return
     */
    @PostMapping("/ids")
    @ApiOperation(httpMethod = "POST", response = Map.class, responseContainer = "Map", value = "未发布的水果id列表")
    public Map<String, Object> fruitIds() {

        return membershipBenefitsService.fruitIds();
    }


}
