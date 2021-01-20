package com.xian.async.batch.model;

import java.io.Serializable;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 11:20 下午 2021/1/1
 */
public class SampleData implements Serializable {

    private int intValue;

    private String name;

    public int getIntValue() {
        return intValue;
    }

    public String getName() {
        return name;
    }

    public SampleData setIntValue(int intValue) {
        this.intValue = intValue;
        return this;
    }

    public SampleData setName(String name) {
        this.name = name;
        return this;
    }
}
