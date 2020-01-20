package com.jclz.fruit.service.impl;

import com.jclz.fruit.constant.GenResult;
import com.jclz.fruit.dao.FruitMapper;
import com.jclz.fruit.entity.Fruit;
import com.jclz.fruit.entity.Page;
import com.jclz.fruit.service.FruitService;
import com.jclz.fruit.utils.UpdateMixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("fruitService")
public class FruitServiceImpl implements FruitService {
    @Autowired
    FruitMapper fruitMapper;
    @Override
    public Page<Fruit> queryFruitList(String fruitName, Integer fruitType, Integer current, Integer size) {
        if (current == null) current = 1;
        if (size == null) size = 10;
        Integer start = size * (current - 1);
        Page<Fruit> page = new Page<>();
        page.setCurrent(current);
        page.setSize(size);
        Integer total = fruitMapper.queryFruitListPageTotal(fruitName, fruitType, start, size);
        page.setTotal(total);
        page.setPages(total / size);
        List<Fruit> fruitList = fruitMapper.queryFruitList(fruitName, fruitType, start, size);
        page.setData(fruitList);
        return page;
    }

    @Override
    public Map<String, Object> addFruit(Fruit fruit) {
        Integer row = fruitMapper.insertSelective(fruit);
        if (row > 0) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> selectFruit(Integer id) {
        Fruit fruit = fruitMapper.selectByPrimaryKey(id);
        if (null!=fruit){
            return GenResult.SUCCESS.genResult(fruit);
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> updateFruit(Fruit fruit) {
        Fruit oldFruit = fruitMapper.selectByPrimaryKey(fruit.getId());
        UpdateMixUtils<Fruit> updateMixUtils = new UpdateMixUtils<Fruit>();
        Fruit mixFruit = updateMixUtils.equalsObj(fruit, oldFruit, new Fruit());
        Integer row = fruitMapper.updateByPrimaryKeySelective(mixFruit);
        if (row > 0) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> deleteFruit(Integer id) {
        Integer row = fruitMapper.deleteByPrimaryKey(id);
        if (row > 0) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }

    @Override
    public Map<String, Object> lowerShelfFruit(Integer id) {
         Integer row = fruitMapper.lowerShelfFruit(id);
        if (row > 0) {
            return GenResult.SUCCESS.genResult();
        }
        return GenResult.FAILED.genResult();
    }
}
