package com.kgv.cookbook.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.kgv.cookbook.R;
import com.kgv.cookbook.adapter.ImagePagerAdapter;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.config.IntentKeys;

import java.util.ArrayList;

/**
 * Created by 陈可轩 on 2017/3/3 17:02
 * Email : 18627241899@163.com
 * Description :
 */
public class ImageActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected boolean hasBottomMenu() {
        return false;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        ArrayList<String> urls = (ArrayList<String>) getIntent().getSerializableExtra(IntentKeys.IMAGES);
        ArrayList<String> descriptions = (ArrayList<String>) getIntent().getSerializableExtra(IntentKeys.DESCRIPTIONS);
        int position = getIntent().getIntExtra(IntentKeys.IMAGE_POSITION,0);
        initUI(urls,descriptions,position);
    }


    private void initUI(ArrayList<String> urls,ArrayList<String> descriptions,int position) {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        ImagePagerAdapter adapter = new ImagePagerAdapter(this,urls,descriptions,this);
        viewPager.setAdapter(adapter);
        int out = (Integer.MAX_VALUE / 2) % (urls.size());
        viewPager.setCurrentItem((Integer.MAX_VALUE / 2 - out) + position);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.photoView:
                finish();
                overridePendingTransition(R.anim.activity_in_alpha,R.anim.activity_out_alpha);
                break;
        }
    }
}
