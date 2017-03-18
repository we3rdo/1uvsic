package com.kgv.cookbook.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.frescoimageviewer.ImageOverlayView;
import com.frescoimageviewer.ImageViewer;
import com.kgv.cookbook.R;
import com.kgv.cookbook.adapter.RecipeStepAdapter;
import com.kgv.cookbook.base.BaseActivity;
import com.kgv.cookbook.bean.ShiPuDetail;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.fragment.recipedetail.FirstFragment;
import com.kgv.cookbook.fragment.recipedetail.SecondFragment;
import com.kgv.cookbook.fragment.recipedetail.StepFragment;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.HttpUtils;
import com.kgv.cookbook.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import me.kaelaela.verticalviewpager.VerticalViewPager;
import me.kaelaela.verticalviewpager.transforms.ZoomOutTransformer;

/**
 * Created by 陈可轩 on 2016/12/27 14:48
 * Email : 18627241899@163.com
 * Description : 食谱详情页
 */
public class RecipeActivity extends BaseActivity {

    private TextView tv_name, tv_gy, tv_people, tv_time, tv_video;
    private ArrayList<Fragment> fragments;
    private FragmentManager fragmentManager;
    public ShiPuDetail bean;
    private int currentStepIndex;
    private VerticalViewPager viewPager;
    private ListView lv_step;
    private RecipeStepAdapter stepAdapter;
    private WebView wv_video;
    private boolean isFirstPlay = true;
    private boolean isShowVideo = false;
    public ImageOverlayView overlayView;
    public ArrayList<String> imageUrls = new ArrayList<>();
    public ArrayList<String> descriptions = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_recipe;
    }

    @Override
    protected boolean hasBottomMenu() {
        return true;
    }

    @Override
    protected void initialization(Bundle savedInstanceState) {
        initUI();
        fragmentManager = getSupportFragmentManager();
        String recipeId = getIntent().getStringExtra(IntentKeys.SHIPU_ID);
        if (!TextUtils.isEmpty(recipeId)) {
            getData(recipeId);
        }
    }

    private void initUI() {
        overlayView = new ImageOverlayView(this);
        wv_video = (WebView) findViewById(R.id.wv_video);
        wv_video.getSettings().setJavaScriptEnabled(true);
        //wv_video.getSettings().setPluginState(WebSettings.PluginState.ON);
        wv_video.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                LogUtils.v("onPageFinished");
            }
        });
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_gy = (TextView) findViewById(R.id.tv_gy);
        tv_people = (TextView) findViewById(R.id.tv_people);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_video = (TextView) findViewById(R.id.tv_video);
        lv_step = (ListView) findViewById(R.id.lv_step);
        lv_step.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                viewPager.setCurrentItem(position, true);
                stepAdapter.setSelectedPosition(position);
                if (isShowVideo) {
                    isShowVideo = false;
                    tv_video.setSelected(false);
                    wv_video.onPause();
                    wv_video.setVisibility(View.GONE);
                }
            }
        });
        fragments = new ArrayList<>();
        //1.先添加1和2
        FirstFragment firstFragment = new FirstFragment();
        SecondFragment secondFragment = new SecondFragment();
        fragments.add(firstFragment);
        fragments.add(secondFragment);

        viewPager = (VerticalViewPager) findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != 0 && position != 1) {
                    currentStepIndex = position - 2;
                }
                stepAdapter.setSelectedPosition(position);
                lv_step.setSelection(position);
                LogUtils.v("recipe", "position " + position);
                LogUtils.v("recipe", "currentStepIndex " + currentStepIndex);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getData(String id) {
        LogUtils.v(Urls.SHIPU_DETAIL + id);
        HttpUtils.getInstance().doGet(Urls.SHIPU_DETAIL + id, new HttpResponse<ShiPuDetail>(ShiPuDetail.class) {
            @Override
            public void onSuccess(ShiPuDetail bean) {
                myHandler.obtainMessage(0, bean).sendToTarget();
            }

            @Override
            public void onError(String msg) {
                myHandler.obtainMessage(1, msg).sendToTarget();
            }
        });
    }

    public void videoClick(View view) {
        String flashUrl = bean.getFlash_src();
        isShowVideo = !isShowVideo;
        tv_video.setSelected(isShowVideo);
        if (isShowVideo) {
            if (isFirstPlay) {
                String video = flashUrl.substring(39, 56);
                //http://pimi.kgv.cn/list/youku/vid/XMTcxMzc2OTMwOA==
                String url = "http://pimi.kgv.cn/list/youku/vid/" + video;
                LogUtils.v("video", "url : " + url);
                wv_video.setVisibility(View.VISIBLE);
                wv_video.loadUrl(url);
                isFirstPlay = false;
            } else {
                wv_video.onResume();
                wv_video.setVisibility(View.VISIBLE);
            }
        } else {
            wv_video.onPause();
            wv_video.setVisibility(View.GONE);
        }

    }

    @Override
    protected void handleMsg0(Message msg) {
        bean = (ShiPuDetail) msg.obj;
        List<ShiPuDetail.StepEntity> steps = bean.getStep();
        addImageResource(steps);
        int size = steps.size();
        //1.填充标题
        fillTitle(bean);
        //2.显示步骤标题
        showStepTitle(size);
        //3.添加步骤fragment
        addStepFragments(steps, size);
        //4.绑定viewpager
        StepViewPagerAdapter adapter = new StepViewPagerAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(false, new ZoomOutTransformer());
    }

    @Override
    protected void handleMsg1(Message msg) {
        tip("未知错误，请稍后重试");
    }


    private void addImageResource(List<ShiPuDetail.StepEntity> steps) {
        imageUrls.add(Urls.BASE_IMG_URL + bean.getImage());
        descriptions.add(bean.getDescription());
        for (int i = 0; i < steps.size(); i++) {
            imageUrls.add(Urls.BASE_IMG_URL + steps.get(i).getImage());
            descriptions.add(steps.get(i).getNote());
        }
    }

    private void fillTitle(ShiPuDetail bean) {
        String flashUrl = bean.getFlash_src();
        tv_video.setVisibility(TextUtils.isEmpty(flashUrl) ? View.INVISIBLE : View.VISIBLE);
        String name = bean.getName();
        tv_name.setText(name);
        tv_gy.setText("工艺：" + bean.getGong_yi_detail().getName());
        tv_people.setText("人数：" + bean.getPeople_num() + "人份");
        tv_time.setText("烹饪时间：" + bean.getHao_shi_detail().getName());
    }

    private void showStepTitle(int size) {
        List<String> ss = new ArrayList<>();
        int titleSize = 2;
        if (size == 3 || size == 4) {
            titleSize = 4;
        } else if (size == 5 || size == 6) {
            titleSize = 5;
        } else if (size == 7 || size == 8) {
            titleSize = 6;
        } else if (size == 9 || size == 10) {
            titleSize = 7;
        } else if (size == 11 || size == 12) {
            titleSize = 8;
        } else if (size == 13 || size == 14) {
            titleSize = 9;
        } else if (size == 15 || size == 16) {
            titleSize = 10;
        }
        for (int i = 0; i < titleSize; i++) {
            ss.add(i == 0 ? "l" : "s");
        }
        stepAdapter = new RecipeStepAdapter(ss);
        lv_step.setAdapter(stepAdapter);
    }

    private void addStepFragments(List<ShiPuDetail.StepEntity> steps, int size) {
        int realSize = size % 2 == 0 ? size / 2 : size / 2 + 1;
        LogUtils.v("stepSize = " + size);
        LogUtils.v("realSize = " + realSize);
        for (int i = 0; i < realSize; i++) {
            StepFragment stepFragment = new StepFragment();
            if (i == 0) {
                stepFragment.setData(steps.get(0), steps.get(1), "1", "2");//步骤:1/2
            } else {
                if (size >= i * 2 + 2) {
                    stepFragment.setData(
                            steps.get(i * 2),
                            steps.get(i * 2 + 1),
                            i * 2 + 1 + "",
                            i * 2 + 2 + "");
                } else {
                    stepFragment.setData(
                            steps.get(i * 2),
                            null,
                            i * 2 + 1 + "",
                            null);
                }
            }
            fragments.add(stepFragment);
        }
    }

    public ImageViewer.OnImageChangeListener getImageChangeListener() {
        return new ImageViewer.OnImageChangeListener() {
            @Override
            public void onImageChange(int position) {
                int realPosition = position % descriptions.size();
                if (realPosition == 0){
                    overlayView.setDescription(descriptions.get(realPosition));
                }else{
                    String index = realPosition + "/" + (descriptions.size() - 1);
                    overlayView.setDescription(index + "     " + descriptions.get(realPosition));
                }
            }
        };
    }

    public GenericDraweeHierarchyBuilder getHierarchyBuilder() {
        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setRoundAsCircle(true);
        return GenericDraweeHierarchyBuilder.newInstance(getResources());
    }

    public ImageViewer.OnDismissListener getDisissListener() {
        return new ImageViewer.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        };
    }

    /*     FragmentPagerAdapter : 缓存其他页面
           FragmentStatePagerAdapter: 不会缓存其他页面        */
    private class StepViewPagerAdapter extends FragmentPagerAdapter {

        public StepViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        wv_video.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        wv_video.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyWebView();
    }

    private void destroyWebView() {
        wv_video.destroy();
    }
}
