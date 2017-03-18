package com.kgv.cookbook.util;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by 陈可轩 on 2016/12/23 15:10
 * Email : 18627241899@163.com
 * Description : glide配置
 */
public class GlideModuleConfig implements GlideModule {

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        Log.v("GlideModuleConfig","applyOptions");
        //100MB
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, 1024 * 1024 * 100));  //机身内存
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", 1024 * 1024 * 100)); //sd卡
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
