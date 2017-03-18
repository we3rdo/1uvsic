package com.kgv.cookbook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.RecipeActivity;
import com.kgv.cookbook.adapter.AllBlogAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.MenuAndBlogShiPu;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.ui.GridView4ScrollView;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/6 13:05
 * Email : 18627241899@163.com
 * Description : 主页-食谱->食材大全&美食天下-去制作(2)
 */
public class GoCookingFragment extends BaseFragment implements View.OnClickListener {

    private GridView4ScrollView gv_m;
    private GridView4ScrollView gv_a;
    private GridView4ScrollView gv_n;
    private AllBlogAdapter mAdapter;
    private AllBlogAdapter aAdapter;
    private AllBlogAdapter nAdapter;
    private TextView tv_more_m;
    private TextView tv_more_a;
    private TextView tv_more_n;
    private ImageView iv_more_m;
    private ImageView iv_more_a;
    private ImageView iv_more_n;
    private boolean openMing = false;
    private boolean openAing = false;
    private boolean openNing = false;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_gocooking;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        getData();
        initUI(contentView);
    }

    private void getData() {
        LogUtils.v("go cooking",Urls.BLOG_MORNING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/morning_blog/create_time/");
        //早餐日志
        httpUtils.doGet(Urls.BLOG_MORNING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/morning_blog/create_time/", new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                handler.obtainMessage(0,str).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
        //午餐日志
        httpUtils.doGet(Urls.BLOG_NOONING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/nooning_blog/create_time/", new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                handler.obtainMessage(1,str).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
        //晚餐日志
        httpUtils.doGet(Urls.BLOG_EVENING_LIST + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword() + "/db_name/evening_blog/create_time/", new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                handler.obtainMessage(2,str).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private void initUI(View contentView) {
        gv_m = (GridView4ScrollView) contentView.findViewById(R.id.gv_m);
        gv_a = (GridView4ScrollView) contentView.findViewById(R.id.gv_a);
        gv_n = (GridView4ScrollView) contentView.findViewById(R.id.gv_n);
        contentView.findViewById(R.id.ll_more_m).setOnClickListener(this);
        contentView.findViewById(R.id.ll_more_a).setOnClickListener(this);
        contentView.findViewById(R.id.ll_more_n).setOnClickListener(this);
        tv_more_m = (TextView) contentView.findViewById(R.id.tv_more_m);
        tv_more_a = (TextView) contentView.findViewById(R.id.tv_more_a);
        tv_more_n = (TextView) contentView.findViewById(R.id.tv_more_n);
        iv_more_m = (ImageView) contentView.findViewById(R.id.iv_more_m);
        iv_more_a = (ImageView) contentView.findViewById(R.id.iv_more_a);
        iv_more_n = (ImageView) contentView.findViewById(R.id.iv_more_n);
        gv_m.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String spid = mAdapter.getItemIdByPosition(position);
                Intent intent = new Intent(baseActivity, RecipeActivity.class);
                intent.putExtra(IntentKeys.SHIPU_ID,spid);
                baseActivity.startActivity(intent);
                baseActivity.overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
            }
        });
        gv_a.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String spid = aAdapter.getItemIdByPosition(position);
                Intent intent = new Intent(baseActivity, RecipeActivity.class);
                intent.putExtra(IntentKeys.SHIPU_ID,spid);
                baseActivity.startActivity(intent);
                baseActivity.overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
            }
        });
        gv_n.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String spid = nAdapter.getItemIdByPosition(position);
                Intent intent = new Intent(baseActivity, RecipeActivity.class);
                intent.putExtra(IntentKeys.SHIPU_ID,spid);
                baseActivity.startActivity(intent);
                baseActivity.overridePendingTransition(R.anim.activity_in_right_2_left, R.anim.activity_out_right_2_left);
            }
        });
    }

    public void refreshData() {
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_more_m:
                if (mAdapter != null && mAdapter.isHasMore()){
                    if (openMing){
                        close(0);
                        openMing = false;
                    }else{
                        open(0);
                        openMing = true;
                    }
                }
                break;
            case R.id.ll_more_a:
                if (aAdapter != null && aAdapter.isHasMore()){
                    if (openAing){
                        close(1);
                        openAing = false;
                    }else{
                        open(1);
                        openAing = true;
                    }
                }
                break;
            case R.id.ll_more_n:
                if (nAdapter != null && nAdapter.isHasMore()){
                    if (openNing){
                        close(2);
                        openNing = false;
                    }else{
                        open(2);
                        openNing = true;
                    }
                }
                break;
        }
    }

    private void open(int type) {
        switch (type){
            case 0:
                mAdapter.control(true);
                tv_more_m.setText("收起");
                iv_more_m.setImageResource(R.drawable.all_m_opened);
                break;
            case 1:
                aAdapter.control(true);
                tv_more_a.setText("收起");
                iv_more_a.setImageResource(R.drawable.all_m_opened);
                break;
            case 2:
                nAdapter.control(true);
                tv_more_n.setText("收起");
                iv_more_n.setImageResource(R.drawable.all_m_opened);
                break;
        }
    }

    private void close(int type) {
        switch (type){
            case 0:
                mAdapter.control(false);
                tv_more_m.setText("更多");
                iv_more_m.setImageResource(R.drawable.all_m_closed);
                break;
            case 1:
                aAdapter.control(false);
                tv_more_a.setText("更多");
                iv_more_a.setImageResource(R.drawable.all_m_closed);
                break;
            case 2:
                nAdapter.control(false);
                tv_more_n.setText("更多");
                iv_more_n.setImageResource(R.drawable.all_m_closed);
                break;
        }
    }

    @Override
    protected void handleMsg0(Message msg) {
        String json = (String) msg.obj;
        if (!"null".equals(json)){
           List<MenuAndBlogShiPu> datas = gson.fromJson(json,new TypeToken<List<MenuAndBlogShiPu>>() {}.getType());
            mAdapter = new AllBlogAdapter(datas);
            gv_m.setAdapter(mAdapter);
        }
    }

    @Override
    protected void handleMsg1(Message msg) {
        String json = (String) msg.obj;
        if (!"null".equals(json)){
            List<MenuAndBlogShiPu> datas = gson.fromJson(json,new TypeToken<List<MenuAndBlogShiPu>>() {}.getType());
            aAdapter = new AllBlogAdapter(datas);
            gv_a.setAdapter(aAdapter);
        }
    }

    @Override
    protected void handleMsg2(Message msg) {
        String json = (String) msg.obj;
        if (!"null".equals(json)){
            List<MenuAndBlogShiPu> datas = gson.fromJson(json,new TypeToken<List<MenuAndBlogShiPu>>() {}.getType());
            nAdapter = new AllBlogAdapter(datas);
            gv_n.setAdapter(nAdapter);
        }
    }
}
