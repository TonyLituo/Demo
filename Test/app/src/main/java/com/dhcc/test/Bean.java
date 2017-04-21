package com.dhcc.test;

/**
 * Created by Lituo on 2017/4/17 0017. 09:00 .
 * Mail：tony1994_vip@163.com
 */

public class Bean {

    private String name;

    private int age;


    public Bean() {
    }

    public Bean(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
