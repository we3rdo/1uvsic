package com.kgv.cookbook.util;

import android.text.TextUtils;

/**
 * @author KEXUAN CHEN
 * @time 2016/8/14  12:54
 * @desc Http请求回调抽象类
 */
public abstract class HttpResponse<T> {

    private Class<T> tClass;

    public HttpResponse(Class<T> tClass) { //告诉调用者需要返回什么类型
        this.tClass = tClass;
    }

    public abstract void onSuccess(T t);//成功时回调 返回解析到的Bean或原生JsonString

    public abstract void onError(String msg);//失败 返回失败信息

    public void parse(String json) {
        if (TextUtils.isEmpty(json)) {
            onError("net error : json is empty");
            return;
        }
        if (tClass == String.class) {
            //调用者传入String 直接返回JSON字符串
            onSuccess((T) json);
            return;
        }
        //以上情况除外,将Json解析成调用者需要的Bean对象并返回
        T t = JsonUtils.parse(json, tClass);
        if (t == null) {
            onError("net error : bean is null");
        } else {
            onSuccess(t);
        }
    }
}
