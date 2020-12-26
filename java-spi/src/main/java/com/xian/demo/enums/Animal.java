package com.xian.demo.enums;

import jdk.nashorn.internal.objects.annotations.Getter;

import javax.swing.*;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 8:58 下午 2020/12/26
 */
public enum Animal {

    /**
     *
     */
    CAT("cat"),
    DOG("dog"),
    MOW("mow");
    private String animal;
    Animal(String animal){
        this.animal = animal;
    }

    public String getAnimal() {
        return animal;
    }
}
