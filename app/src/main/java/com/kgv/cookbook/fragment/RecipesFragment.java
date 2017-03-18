package com.kgv.cookbook.fragment;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.FoodWorldActivity;
import com.kgv.cookbook.adapter.RAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.RecipeCategory;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.config.SpKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2017/1/3 16:52
 * Email : 18627241899@163.com
 * Description : 主页-食谱->美食天下-查看全部(1)
 */
public class RecipesFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv0, tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10;
    private TextView tv_title;
    private GridView gv_content;
    public RAdapter adapter;
    private FoodWorldActivity activity;
    private ArrayList<String> activityIds;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_recipes;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        initUI(contentView);
        initListener();
        show("3");
        select(0);
    }

    @Override
    protected void initOnActivityCreated(Bundle savedInstanceState) {
        activity = (FoodWorldActivity) baseActivity;
        activityIds = activity.ids;
    }

    private void initUI(View contentView) {
        tv0 = (TextView) contentView.findViewById(R.id.tv0);
        tv1 = (TextView) contentView.findViewById(R.id.tv1);
        tv2 = (TextView) contentView.findViewById(R.id.tv2);
        tv3 = (TextView) contentView.findViewById(R.id.tv3);
        tv4 = (TextView) contentView.findViewById(R.id.tv4);
        tv5 = (TextView) contentView.findViewById(R.id.tv5);
        tv6 = (TextView) contentView.findViewById(R.id.tv6);
        tv7 = (TextView) contentView.findViewById(R.id.tv7);
        tv8 = (TextView) contentView.findViewById(R.id.tv8);
        tv9 = (TextView) contentView.findViewById(R.id.tv9);
        tv10 = (TextView) contentView.findViewById(R.id.tv10);
        tv_title = (TextView) contentView.findViewById(R.id.tv_title);
        gv_content = (GridView) contentView.findViewById(R.id.gv_content);
    }

    private void initListener() {
        tv0.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);
        tv10.setOnClickListener(this);
        gv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                activity = (FoodWorldActivity) getActivity();
                String pid = adapter.getItemIdByPosition(position);
                if (activity.leftAdapter.isEditing()) {
                    activity.addShiPuToMade(pid);
                }else{
                    String name = adapter.getItemNameByPosition(position);
                    activity.refreshContent(pid,name);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv0://外国菜
                show("3");
                select(0);
                break;
            case R.id.tv1://中华菜系
                show("2");
                select(1);
                break;
            case R.id.tv2://功能调理
                show("6");
                select(2);
                break;
            case R.id.tv3://适宜人群
                show("5");
                select(3);
                break;
            case R.id.tv4://烘焙
                show("8");
                select(4);
                break;
            case R.id.tv5://场景
                show("10");
                select(5);
                break;
            case R.id.tv6://家常菜
                show("1");
                select(6);
                break;
            case R.id.tv7://各地小吃
                show("4");
                select(7);
                break;
            case R.id.tv8://疾病调理
                show("7");
                select(8);
                break;
            case R.id.tv9://家电
                show("250");
                select(9);
                break;
            case R.id.tv10://节日
                show("9");
                select(10);
                break;
        }
    }

    private void select(int position) {
        tv0.setSelected(false);
        tv1.setSelected(false);
        tv2.setSelected(false);
        tv3.setSelected(false);
        tv4.setSelected(false);
        tv5.setSelected(false);
        tv6.setSelected(false);
        tv7.setSelected(false);
        tv8.setSelected(false);
        tv9.setSelected(false);
        tv10.setSelected(false);
        switch (position) {
            case 0:
                tv0.setSelected(true);
                break;
            case 1:
                tv1.setSelected(true);
                break;
            case 2:
                tv2.setSelected(true);
                break;
            case 3:
                tv3.setSelected(true);
                break;
            case 4:
                tv4.setSelected(true);
                break;
            case 5:
                tv5.setSelected(true);
                break;
            case 6:
                tv6.setSelected(true);
                break;
            case 7:
                tv7.setSelected(true);
                break;
            case 8:
                tv8.setSelected(true);
                break;
            case 9:
                tv9.setSelected(true);
                break;
            case 10:
                tv10.setSelected(true);
                break;
        }
    }

    private void show(String id) {
        String str = SpUtils.getString(getContext(), SpKeys.FOODWORLD_R_LIST_JSON + id);
        if (TextUtils.isEmpty(str)) {
            getDataFromNet(id);
        } else {
            if (System.currentTimeMillis() - SpUtils.getLong(getContext(),
                    SpKeys.FOODWORLD_R_LIST_TIME + id) > SpKeys.OUT_TIME) {
                getDataFromNet(id);
            } else {
                RecipeCategory cBean = gson.fromJson(str, RecipeCategory.class);
                handler.obtainMessage(0, cBean).sendToTarget();
            }
        }
    }

    private void getDataFromNet(final String id) {
        httpUtils.doGet(Urls.SHIPU_CATE_BASE_URL + id, new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                SpUtils.saveString(AppUtils.getContext(), SpKeys.FOODWORLD_R_LIST_JSON + id, str);
                SpUtils.saveLong(AppUtils.getContext(), SpKeys.FOODWORLD_R_LIST_TIME + id, System.currentTimeMillis());
                RecipeCategory cBean = gson.fromJson(str, RecipeCategory.class);
                handler.obtainMessage(0, cBean).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    @Override
    protected void handleMsg0(Message msg) {
        RecipeCategory cBean = (RecipeCategory) msg.obj;
        tv_title.setText(cBean.getTitle());
        List<RecipeCategory.ChildrenEntity> datas = cBean.getChildren();
        if (adapter == null){
            adapter = new RAdapter(datas,activityIds);
        }else{
            adapter.replaceData(datas);



        }
        gv_content.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity = null;
    }
}
