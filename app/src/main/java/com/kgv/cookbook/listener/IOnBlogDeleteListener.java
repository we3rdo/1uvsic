package com.kgv.cookbook.listener;

/**
 * Created by 陈可轩 on 2017/1/10 16:09
 * Email : 18627241899@163.com
 * Description :
 */
public interface IOnBlogDeleteListener {

    void onBlogDelete(String delId,int type);

    void onChangeTitle(int type);
}
