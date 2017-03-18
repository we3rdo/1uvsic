package com.kgv.cookbook.bean;

/**
 * Created by 陈可轩 on 2017/1/12 16:11
 * Email : 18627241899@163.com
 * Description :
 */
public class FamilyContent {


    /**
     * uid : 59
     * health_name :
     * num : 1
     * member : 5
     * health : 0
     * id : 3
     * member_name : 成年人 19岁-40岁
     */
    private String uid;
    private String health_name;
    private String num;
    private String member;
    private String health;
    private String id;
    private String member_name;

    public FamilyContent (String uid, String health_name, String num, String member, String health, String id, String member_name) {
        this.uid = uid;
        this.health_name = health_name;
        this.num = num;
        this.member = member;
        this.health = health;
        this.id = id;
        this.member_name = member_name;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setHealth_name(String health_name) {
        this.health_name = health_name;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getUid() {
        return uid;
    }

    public String getHealth_name() {
        return health_name;
    }

    public String getNum() {
        return num;
    }

    public String getMember() {
        return member;
    }

    public String getHealth() {
        return health;
    }

    public String getId() {
        return id;
    }

    public String getMember_name() {
        return member_name;
    }
}
