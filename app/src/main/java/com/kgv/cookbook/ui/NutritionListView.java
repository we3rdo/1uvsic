package com.kgv.cookbook.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.bean.Nutrition;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.config.IntentKeys;
import com.kgv.cookbook.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/17 10:48
 * Email : 18627241899@163.com
 * Description :
 */
public class NutritionListView extends LinearLayout implements View.OnClickListener {

    private Context context;
    private List<Nutrition> dataSet;
    private ArrayList<LinearLayout> parents = new ArrayList<>();
    private ArrayList<View> lines = new ArrayList<>();
    private ArrayList<ViewStub> stubs = new ArrayList<>();
    private ArrayList<TextView> tvList0 = new ArrayList<>();
    private ArrayList<TextView> tvList1 = new ArrayList<>();
    private ArrayList<TextView> tvList2 = new ArrayList<>();
    private ArrayList<ArrayList<TextView>> items = new ArrayList<>();
    private boolean hasMore = false;
    private boolean opening = false;
    private int inflateCount;

    public NutritionListView(Context context) {
        super(context);
        init(context);
        this.context = context;
    }

    public NutritionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        this.context = context;
    }

    public NutritionListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        this.context = context;
    }

    private void init(Context context) {
        View.inflate(context, R.layout.view_nutrition_list, this);
        tvList0.add((TextView) findViewById(R.id.tv_item0_name));
        tvList0.add((TextView) findViewById(R.id.tv_item0_d0));
        tvList0.add((TextView) findViewById(R.id.tv_item0_d1));
        tvList0.add((TextView) findViewById(R.id.tv_item0_d2));
        tvList0.add((TextView) findViewById(R.id.tv_item0_d3));
        tvList0.add((TextView) findViewById(R.id.tv_item0_d4));
        tvList0.add((TextView) findViewById(R.id.tv_item0_d5));
        tvList0.add((TextView) findViewById(R.id.tv_item0_d6));
        tvList0.add((TextView) findViewById(R.id.tv_item0_d7));
        tvList0.add((TextView) findViewById(R.id.tv_item0_d8));
        tvList0.add((TextView) findViewById(R.id.tv_item0_d9));
        tvList1.add((TextView) findViewById(R.id.tv_item1_name));
        tvList1.add((TextView) findViewById(R.id.tv_item1_d0));
        tvList1.add((TextView) findViewById(R.id.tv_item1_d1));
        tvList1.add((TextView) findViewById(R.id.tv_item1_d2));
        tvList1.add((TextView) findViewById(R.id.tv_item1_d3));
        tvList1.add((TextView) findViewById(R.id.tv_item1_d4));
        tvList1.add((TextView) findViewById(R.id.tv_item1_d5));
        tvList1.add((TextView) findViewById(R.id.tv_item1_d6));
        tvList1.add((TextView) findViewById(R.id.tv_item1_d7));
        tvList1.add((TextView) findViewById(R.id.tv_item1_d8));
        tvList1.add((TextView) findViewById(R.id.tv_item1_d9));
        tvList2.add((TextView) findViewById(R.id.tv_item2_name));
        tvList2.add((TextView) findViewById(R.id.tv_item2_d0));
        tvList2.add((TextView) findViewById(R.id.tv_item2_d1));
        tvList2.add((TextView) findViewById(R.id.tv_item2_d2));
        tvList2.add((TextView) findViewById(R.id.tv_item2_d3));
        tvList2.add((TextView) findViewById(R.id.tv_item2_d4));
        tvList2.add((TextView) findViewById(R.id.tv_item2_d5));
        tvList2.add((TextView) findViewById(R.id.tv_item2_d6));
        tvList2.add((TextView) findViewById(R.id.tv_item2_d7));
        tvList2.add((TextView) findViewById(R.id.tv_item2_d8));
        tvList2.add((TextView) findViewById(R.id.tv_item2_d9));
        items.add(tvList0);
        items.add(tvList1);
        items.add(tvList2);
        parents.add((LinearLayout) findViewById(R.id.ll_0));
        parents.add((LinearLayout) findViewById(R.id.ll_1));
        parents.add((LinearLayout) findViewById(R.id.ll_2));
        lines.add(findViewById(R.id.view_line_0));
        lines.add(findViewById(R.id.view_line_1));
        lines.add(findViewById(R.id.view_line_2));
        findViewById(R.id.tv_item0_name).setOnClickListener(this);
        findViewById(R.id.tv_item1_name).setOnClickListener(this);
        findViewById(R.id.tv_item2_name).setOnClickListener(this);
        ViewStub stub3 = (ViewStub) findViewById(R.id.vs_item_3);
        ViewStub stub4 = (ViewStub) findViewById(R.id.vs_item_4);
        ViewStub stub5 = (ViewStub) findViewById(R.id.vs_item_5);
        ViewStub stub6 = (ViewStub) findViewById(R.id.vs_item_6);
        ViewStub stub7 = (ViewStub) findViewById(R.id.vs_item_7);
        ViewStub stub8 = (ViewStub) findViewById(R.id.vs_item_8);
        ViewStub stub9 = (ViewStub) findViewById(R.id.vs_item_9);
        ViewStub stub10 = (ViewStub) findViewById(R.id.vs_item_10);
        ViewStub stub11 = (ViewStub) findViewById(R.id.vs_item_11);
        ViewStub stub12 = (ViewStub) findViewById(R.id.vs_item_12);
        ViewStub stub13 = (ViewStub) findViewById(R.id.vs_item_13);
        ViewStub stub14 = (ViewStub) findViewById(R.id.vs_item_14);
        ViewStub stub15 = (ViewStub) findViewById(R.id.vs_item_15);
        stubs.add(stub3);
        stubs.add(stub4);
        stubs.add(stub5);
        stubs.add(stub6);
        stubs.add(stub7);
        stubs.add(stub8);
        stubs.add(stub9);
        stubs.add(stub10);
        stubs.add(stub11);
        stubs.add(stub12);
        stubs.add(stub13);
        stubs.add(stub14);
        stubs.add(stub15);
    }

    private void bindingData() {
        if (dataSet.size() > 16) {
            for (int i = 0; i < 16; i++) {
                ArrayList<TextView> item = items.get(i);
                List<String> datas = dataSet.get(i).getData();
                for (int j = 0; j < item.size(); j++) { //j=11
                    item.get(j).setText(datas.get(j));
                }
            }
        } else {
            for (int i = 0; i < dataSet.size(); i++) {
                ArrayList<TextView> item = items.get(i);
                List<String> datas = dataSet.get(i).getData();
                for (int j = 0; j < item.size(); j++) { //j=11
                    item.get(j).setText(datas.get(j));
                }
            }
        }
        showItemBySize(3);
    }

    private void initStubItems(int realNeedSize) {
        LogUtils.v("开始加载 : 需要加载" + realNeedSize + "个");
        if (stubs.size() < 1) {
            return;
        }
        for (int i = 0; i < realNeedSize; i++) {
            ViewStub stub = stubs.get(i);
            LinearLayout ll = (LinearLayout) stub.inflate();
            ArrayList<TextView> tvList = new ArrayList<>();
            TextView tvName = (TextView) ll.findViewById(R.id.tv_name);
            final int finalI = i;
            tvName.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.v("click","2 click : index = " + (finalI + 3));
                    sendJumpBroadcast(finalI + 3);
                }
            });
            tvList.add(tvName);
            tvList.add((TextView) ll.findViewById(R.id.tv_d0));
            tvList.add((TextView) ll.findViewById(R.id.tv_d1));
            tvList.add((TextView) ll.findViewById(R.id.tv_d2));
            tvList.add((TextView) ll.findViewById(R.id.tv_d3));
            tvList.add((TextView) ll.findViewById(R.id.tv_d4));
            tvList.add((TextView) ll.findViewById(R.id.tv_d5));
            tvList.add((TextView) ll.findViewById(R.id.tv_d6));
            tvList.add((TextView) ll.findViewById(R.id.tv_d7));
            tvList.add((TextView) ll.findViewById(R.id.tv_d8));
            tvList.add((TextView) ll.findViewById(R.id.tv_d9));
            items.add(tvList);
            parents.add(ll);
            lines.add(ll.findViewById(R.id.view_line));
        }
        for (int j = realNeedSize - 1; j >= 0; j--) {
            stubs.remove(j);
        }
        LogUtils.v("加载完成 : 剩余stubs = " + stubs.size() + "个");
    }

    private void showItemBySize(int size) {
        for (int i = 0; i < parents.size(); i++) {
            parents.get(i).setVisibility(View.GONE);
            lines.get(i).setVisibility(View.GONE);
        }
        for (int i = 0; i < size; i++) {
            parents.get(i).setVisibility(View.VISIBLE);
            lines.get(i).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        int i = 0;
        switch (v.getId()) {
            case R.id.tv_item0_name:
                i = 0;
                break;
            case R.id.tv_item1_name:
                i = 1;
                break;
            case R.id.tv_item2_name:
                i = 2;
                break;
        }
        LogUtils.v("click","1 click : index = " + i);
        if (dataSet != null && dataSet.get(i) != null){
            sendJumpBroadcast(i);
        }

    }

    private void sendJumpBroadcast(int i) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        Intent intent = new Intent("com.kgv.cookbook.JUMP_RECIPE");
        intent.putExtra(IntentKeys.SHIPU_ID, dataSet.get(i).getSpId());
        localBroadcastManager.sendBroadcast(intent);
    }

    /*   need call.  */
    public void setDataSet(List<Nutrition> dataSet) {
        this.dataSet = dataSet;
        hasMore = dataSet.size() > 3;
        if (hasMore) {
            //如果超出16条则inflate全部
            inflateCount = dataSet.size() > 16 ? stubs.size() : dataSet.size() - 3;
            LogUtils.v("第1次加载,需要加载" + inflateCount + "个");
            initStubItems(inflateCount);
        }
        bindingData();
    }

    public void appendDataSet(List<Nutrition> dataSet) {
        this.dataSet = dataSet;
        hasMore = dataSet.size() > 3;
        if (hasMore) {
            //如果超出16条则需要inflate全部
            int needInflate = dataSet.size() > 16 ? 13 : dataSet.size() - 3;
            if (needInflate > inflateCount) {
                LogUtils.v("第2次加载,需要加载" + needInflate + "个");
                initStubItems(needInflate - inflateCount);
                inflateCount = needInflate;
            }
        }
        bindingData();
    }


    public void control(boolean flag) {
        int needShow = dataSet.size() > 16 ? 16 : dataSet.size();
        if (hasMore) {
            opening = flag;
            showItemBySize(opening ? needShow : 3);
        }
    }

    public int getSize(){
        return dataSet.size();
    }

    public boolean isOpening() {
        return opening;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void clearData() {
        dataSet = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            ArrayList<TextView> item = items.get(i);
            for (int j = 0; j < item.size(); j++) {
                item.get(j).setText("");
            }
        }
    }

    public void setBackground(boolean singleStart) {
        for (int i = 0; i < parents.size(); i++) {
            if (singleStart) {
                if (i % 2 == 0) {
                    parents.get(i).setBackgroundColor(AppUtils.getColor(R.color.item_single));
                } else {
                    parents.get(i).setBackgroundColor(AppUtils.getColor(R.color.item_double));
                }
            } else {
                if (i % 2 == 0) {
                    parents.get(i).setBackgroundColor(AppUtils.getColor(R.color.item_double));
                } else {
                    parents.get(i).setBackgroundColor(AppUtils.getColor(R.color.item_single));
                }
            }
        }
    }
}
