package com.xian.proxy.modul;



import java.io.Serializable;

/**
 * @Description
 * @Author: xlr
 * @Date: Created in 9:29 上午 2021/1/3
 */
public class Student implements Serializable {

    private String name;
    private Integer number;
    private String birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
