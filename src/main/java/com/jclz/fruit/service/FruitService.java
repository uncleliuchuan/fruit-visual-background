package com.jclz.fruit.service;

import com.jclz.fruit.entity.Fruit;
import com.jclz.fruit.entity.Page;

import java.util.Map;

public interface FruitService {
    Page<Fruit> queryFruitList(String fruitName, Integer fruitType, Integer current, Integer size);

    Map<String, Object> addFruit(Fruit fruit);

    Map<String, Object> selectFruit(Integer id);

    Map<String, Object> updateFruit(Fruit fruit);

    Map<String, Object> deleteFruit(Integer id);

    Map<String, Object> lowerShelfFruit(Integer id);
}
