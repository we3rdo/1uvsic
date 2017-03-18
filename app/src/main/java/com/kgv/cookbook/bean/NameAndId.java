package com.kgv.cookbook.bean;

import java.io.Serializable;

/**
 * Created by 陈可轩 on 2017/1/11 17:52
 * Email : 18627241899@163.com
 * Description :
 */
public class NameAndId implements Serializable{

    private String id;
    private String name;

    public NameAndId(String id, String name) {
        this.id = id;
        this.name = name;
    }



    @Override
    public String toString() {
        return "NameAndId{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
