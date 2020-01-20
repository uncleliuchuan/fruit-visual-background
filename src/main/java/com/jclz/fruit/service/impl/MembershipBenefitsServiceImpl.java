package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.FruitMapper;
import com.jclz.fruit.dao.MembershipBenefitsMapper;
import com.jclz.fruit.entity.MembershipBenefits;
import com.jclz.fruit.entity.Page;
import com.jclz.fruit.service.MembershipBenefitsService;
import com.jclz.fruit.utils.UpdateMixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
@Service("membershipBenefitsService")
public class MembershipBenefitsServiceImpl implements MembershipBenefitsService {
    @Autowired
    MembershipBenefitsMapper membershipBenefitsMapper;
    @Autowired
    FruitMapper fruitMapper;
    @Override
    public Page<MembershipBenefits> membershipBenefitsList(String fruitName, Integer fruitType, Integer current, Integer size) {
        if (current == null) current = 1;
        if (size == null) size = 10;
        Integer start = size * (current - 1);
        Page<MembershipBenefits> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        Integer total = membershipBenefitsMapper.membershipBenefitsListTotal(fruitName, fruitType, start, size);
        page.setTotal(total);
        page.setPages(total / size);
        List<MembershipBenefits> fruitList = membershipBenefitsMapper.membershipBenefitsList(fruitName, fruitType, start, size);
        page.setData(fruitList);
        return page;
    }

    @Override
    public Map<String, Object> membershipBenefitsDetial(Integer id) {
        MembershipBenefits membershipBenefits=membershipBenefitsMapper.selectByPrimaryKey(id);
        if (null==membershipBenefits){
            return GenResult.FAILED.genResult("该会员福利不存在！！！");
        }

        return GenResult.SUCCESS.genResult(membershipBenefits);
    }

    @Override
    public Map<String, Object> updateMembershipBenefits(MembershipBenefits membershipBenefits) {
        MembershipBenefits oldMembershipBenefits = membershipBenefitsMapper.selectById(membershipBenefits.getId());
        UpdateMixUtils<MembershipBenefits> updateMixUtils = new UpdateMixUtils<MembershipBenefits>();
        MembershipBenefits mixMembershipBenefits = updateMixUtils.equalsObj(membershipBenefits, oldMembershipBenefits, new MembershipBenefits());
        Integer row = membershipBenefitsMapper.updateByPrimaryKeySelective(mixMembershipBenefits);
        if (row > 0) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> addMembershipBenefits(MembershipBenefits membershipBenefits) {
        MembershipBenefits old = membershipBenefitsMapper.selectByFruitId(membershipBenefits.getFruitId());
        if (null!=old){
            return GenResult.FAILED.genResult("该商品已发布会员福利，请在会员福利列表查看！！！");
        }
        int row = membershipBenefitsMapper.insertSelective(membershipBenefits);
        if (row>0){
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> fruitIds() {
        List<Integer> fruitIds=fruitMapper.queryFruitIds();
        List<Integer> fruitIdsTemp=membershipBenefitsMapper.queryFruitIds();
        fruitIds.removeAll(fruitIdsTemp);
        return GenResult.SUCCESS.genResult(fruitIds);
    }
}
