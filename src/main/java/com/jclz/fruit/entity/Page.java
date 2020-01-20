package com.jclz.fruit.entity;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private int current;//当前页
    private int pages;//总页数
    private int size;//每页显示条数
    private int total;//总条数
    private List<T> data;//

}
