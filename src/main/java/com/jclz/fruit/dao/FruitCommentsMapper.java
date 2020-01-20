package com.jclz.fruit.dao;

import com.jclz.fruit.entity.FruitComments;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FruitCommentsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FruitComments record);

    int insertSelective(FruitComments record);

    FruitComments selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FruitComments record);

    int updateByPrimaryKey(FruitComments record);

    int selectCommentsNum(Integer fruitId);

}