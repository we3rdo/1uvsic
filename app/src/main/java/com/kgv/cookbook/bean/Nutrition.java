package com.kgv.cookbook.bean;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/10 10:38
 * Email : 18627241899@163.com
 * Description :
 */
public class Nutrition {


    private String spId;
    private List<String> data;

    public Nutrition() {
    }

    public Nutrition(String spId, List<String> data) {
        this.spId = spId;
        this.data = data;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}


