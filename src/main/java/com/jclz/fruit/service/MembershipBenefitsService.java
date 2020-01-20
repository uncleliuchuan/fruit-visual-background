package com.jclz.fruit.service;

import com.jclz.fruit.entity.MembershipBenefits;
import com.jclz.fruit.entity.Page;

import java.util.Map;

public interface MembershipBenefitsService {
    Page<MembershipBenefits> membershipBenefitsList(String fruitName, Integer fruitType, Integer current, Integer size);

    Map<String, Object> membershipBenefitsDetial(Integer id);

    Map<String, Object> updateMembershipBenefits(MembershipBenefits membershipBenefits);

    Map<String, Object> addMembershipBenefits(MembershipBenefits membershipBenefits);

    Map<String, Object> fruitIds();
}
