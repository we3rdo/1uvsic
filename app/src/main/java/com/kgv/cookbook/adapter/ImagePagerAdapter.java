package com.kgv.cookbook.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.ui.RecipeStepImageView;
import com.kgv.cookbook.util.LogUtils;

import java.util.ArrayList;

/**
 * Created by 陈可轩 on 2017/3/3 17:19
 * Email : 18627241899@163.com
 * Description :
 */
public class ImagePagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> urls;
    private ArrayList<String> descriptions;
    private RecipeStepImageView view;
    private View.OnClickListener listener;

    public ImagePagerAdapter(Context context, ArrayList<String> urls, ArrayList<String> descriptions, View.OnClickListener listener){
        this.context = context;
        this.urls = urls;
        this.descriptions = descriptions;
        this.listener = listener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position % urls.size();
        view = new RecipeStepImageView(context);
        view.setImage(urls.get(realPosition));
        view.setDescription(descriptions.get(realPosition));
        LogUtils.v("nongsha","r p = " + realPosition);
        if (realPosition == 0){
            view.hintPosition();
        }else{
            view.setPosition(realPosition + "/" + (urls.size() - 1));
        }
        view.setListener(listener);
        container.addView(view);
        return view;
    }

    public RecipeStepImageView getView(){
        return view;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
