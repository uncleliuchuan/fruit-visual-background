package com.jclz.fruit.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class UpdateMixUtils<T> implements Serializable {
    /**
     * @param recent 混合前信对象
     * @param old    混合前旧对象
     * @param mix    混合后对象
     * @return
     */
    public T equalsObj(T recent, T old, T mix) {
        Field[] fields = recent.getClass().getDeclaredFields();
        Field[] fields2 = old.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            fields2[i].setAccessible(true);
            try {
                if (fields[i].get(recent) == null) {// null时填充old
                    String name = fields[i].getName();
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);// 将属性的首字符大写，方便构造get，set方法
                    //System.out.println("name:" + name);
                    Method m = mix.getClass().getMethod("set" + name, fields[i].getType());
                    m.invoke(mix, fields2[i].get(old));
                    //System.out.println("null" + T);
                } else {
                    String name = fields[i].getName();
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);// 将属性的首字符大写，方便构造get，set方法
                    //System.out.println("name:" + name);
                    Method m = mix.getClass().getMethod("set" + name, fields[i].getType());
                    m.invoke(mix, fields[i].get(recent));
                    //System.out.println("not null" + T);
                }
            } catch (Exception e) {
            }
            fields[i].setAccessible(false);
            fields2[i].setAccessible(false);
        }
        System.out.println(mix);
        return mix;
    }
}
