package com.jclz.fruit.dao;

import com.jclz.fruit.entity.Fruit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FruitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Fruit record);

    int insertSelective(Fruit record);

    Fruit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Fruit record);

    int updateByPrimaryKey(Fruit record);

    List<Fruit> queryFruitList(@Param("fruitName")String fruitName,
                               @Param("fruitType")Integer fruitType,
                               @Param("start") Integer start,
                               @Param("size")Integer size);

    int queryFruitListPageTotal(@Param("fruitName")String fruitName,
                                @Param("fruitType")Integer fruitType,
                                @Param("start") Integer start,
                                @Param("size")Integer size);

    List<Integer> queryFruitIds();

    int lowerShelfFruit(Integer id);
}