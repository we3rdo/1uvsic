package com.kgv.cookbook.base;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author KEXUAN CHEN
 * @time 2016/8/20  18:42
 * @desc ${TODD}
 */
public abstract class BaseHolder<T> {

    public View mHolderView; //根布局的视图

    private T mData;
    
    public BaseHolder(ViewGroup parent){
        //1.初始化根布局,找到自控件,由子类具体实现
        mHolderView = initHolderViewAndFBI(parent);
        mHolderView.setTag(this);
    }

    public void setDataAndBindingData(ViewGroup parent, T data){
        //2.接收数据
        mData = data;
        //3.绑定数据刷新UI,也由子类实现
        bindingData(data,parent);
    }

    /**
     *  初始化根布局and找到子控件
     */
    public abstract View initHolderViewAndFBI(ViewGroup parent);

    /**
     *  绑定数据到子控件
     */
    public abstract void bindingData(T data,ViewGroup parent);

}
