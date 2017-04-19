package com.kgv.cookbook.bean;

/**
 *   Created by ckx on 2017/4/8.
 */
public class NameAndValue {


    private String name;
    private String value;

    public NameAndValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
