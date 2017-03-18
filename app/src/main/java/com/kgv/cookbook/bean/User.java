package com.kgv.cookbook.bean;

/**
 * Created by 陈可轩 on 2016/12/20 14:39
 * Email : 18627241899@163.com
 * Description :
 */
public class User {

    private boolean exist;
    private String id;
    private String username;
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }
}
