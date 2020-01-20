package com.jclz.fruit.dao;

import com.jclz.fruit.entity.MembershipBenefits;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MembershipBenefitsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MembershipBenefits record);

    int insertSelective(MembershipBenefits record);

    MembershipBenefits selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MembershipBenefits record);

    int updateByPrimaryKey(MembershipBenefits record);

    List<MembershipBenefits> membershipBenefitsList(@Param("fruitName")String fruitName,
                                                    @Param("fruitType")Integer fruitType,
                                                    @Param("start") Integer start,
                                                    @Param("size")Integer size);

    int membershipBenefitsListTotal(@Param("fruitName")String fruitName,
                                        @Param("fruitType")Integer fruitType,
                                        @Param("start") Integer start,
                                        @Param("size")Integer size);

    MembershipBenefits selectById(Integer id);

    MembershipBenefits selectByFruitId(Integer fruitId);

    List<Integer> queryFruitIds();
}