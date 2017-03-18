package com.kgv.cookbook.base;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.kgv.cookbook.CookbookApplication;
import com.kgv.cookbook.R;
import com.kgv.cookbook.activity.AWeekBranchActivity;
import com.kgv.cookbook.activity.AllMaterialActivity;
import com.kgv.cookbook.activity.InterestSettingActivity;
import com.kgv.cookbook.adapter.MAdapter;
import com.kgv.cookbook.bean.M;
import com.kgv.cookbook.config.SpKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.fragment.interest.InterestMaterialsFragment;
import com.kgv.cookbook.ui.GridView4ScrollView;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;
import com.kgv.cookbook.util.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/23 16:12
 * Email : 18627241899@163.com
 * Description : 主页-食谱->食材大全-查看全部(1)-材列表页基类
 */
public abstract class BaseMFragment extends BaseFragment {

    private int parentPage = -1;
    private final int ALL_MATERIALS = 0;
    private final int INTEREST = 1;
    private final int A_WEEK = 2;
    protected List<TextView> tvs;
    protected List<GridView4ScrollView> gvs;
    protected ArrayList<MAdapter> adapters;
    //区分哪个界面在使用
    private AllMaterialActivity materialActivity;
    private ArrayList<String> activityIds;
    private InterestMaterialsFragment parentFragment;
    private AWeekBranchActivity aWeekBranchActivity;
    private ArrayList<String> aWeekIds = new ArrayList<>();
    private InterestSettingActivity interestSettingActivity;

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        initUI(contentView);
        getData();
    }

    @Override
    protected void initOnActivityCreated(Bundle savedInstanceState) {
        if (baseActivity.flag == 0) {
            //食材大全使用
            LogUtils.v("baseM", "isAllMate");
            parentPage = ALL_MATERIALS;
            materialActivity = (AllMaterialActivity) baseActivity;
            activityIds = materialActivity.ids;
        } else if (baseActivity.flag == 1) {
            //喜好设置使用
            LogUtils.v("baseM", "isInterest");
            parentPage = INTEREST;
            activityIds = new ArrayList<>();
            interestSettingActivity = (InterestSettingActivity) baseActivity;
            parentFragment = (InterestMaterialsFragment) getParentFragment();
        } else {
            //一周菜谱使用
            parentPage = A_WEEK;
            aWeekBranchActivity = (AWeekBranchActivity) baseActivity;
            activityIds = new ArrayList<>();
        }
    }

    private void initUI(View contentView) {
        adapters = new ArrayList<>();
        tvs = new ArrayList<>();
        gvs = new ArrayList<>();
        tvs.add((TextView) contentView.findViewById(R.id.tv0));
        tvs.add((TextView) contentView.findViewById(R.id.tv1));
        tvs.add((TextView) contentView.findViewById(R.id.tv2));
        tvs.add((TextView) contentView.findViewById(R.id.tv3));
        tvs.add((TextView) contentView.findViewById(R.id.tv4));
        tvs.add((TextView) contentView.findViewById(R.id.tv5));
        tvs.add((TextView) contentView.findViewById(R.id.tv6));
        tvs.add((TextView) contentView.findViewById(R.id.tv7));
        GridView4ScrollView gv0 = (GridView4ScrollView) contentView.findViewById(R.id.gv0);
        gvs.add(gv0);
        GridView4ScrollView gv1 = (GridView4ScrollView) contentView.findViewById(R.id.gv1);
        gvs.add(gv1);
        GridView4ScrollView gv2 = (GridView4ScrollView) contentView.findViewById(R.id.gv2);
        gvs.add(gv2);
        GridView4ScrollView gv3 = (GridView4ScrollView) contentView.findViewById(R.id.gv3);
        gvs.add(gv3);
        GridView4ScrollView gv4 = (GridView4ScrollView) contentView.findViewById(R.id.gv4);
        gvs.add(gv4);
        GridView4ScrollView gv5 = (GridView4ScrollView) contentView.findViewById(R.id.gv5);
        gvs.add(gv5);
        GridView4ScrollView gv6 = (GridView4ScrollView) contentView.findViewById(R.id.gv6);
        gvs.add(gv6);
        GridView4ScrollView gv7 = (GridView4ScrollView) contentView.findViewById(R.id.gv7);
        gvs.add(gv7);
        for (int i = 0; i < gvs.size(); i++) {
            final int finalI = i;
            gvs.get(i).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MAdapter currentAdapter = adapters.get(0);
                    switch (parent.getId()) {
                        case R.id.gv0:
                            currentAdapter = adapters.get(0);
                            break;
                        case R.id.gv1:
                            currentAdapter = adapters.get(1);
                            break;
                        case R.id.gv2:
                            currentAdapter = adapters.get(2);
                            break;
                        case R.id.gv3:
                            currentAdapter = adapters.get(3);
                            break;
                        case R.id.gv4:
                            currentAdapter = adapters.get(4);
                            break;
                        case R.id.gv5:
                            currentAdapter = adapters.get(5);
                            break;
                        case R.id.gv6:
                            currentAdapter = adapters.get(6);
                            break;
                        case R.id.gv7:
                            currentAdapter = adapters.get(7);
                            break;
                    }
                    String scid = adapters.get(finalI).getItemIdByPosition(position);
                    String name = adapters.get(finalI).getNameByPosition(position);
                    if (currentAdapter.isHasMore()) {
                        //查看更多
                        if (position == currentAdapter.getDataSize() - 1) {
                            if (currentAdapter.isOpening()) {
                                close(currentAdapter);
                            } else {
                                open(currentAdapter);
                            }
                        } else {
                            //普通点击
                            if (parentPage == ALL_MATERIALS) {
                                AllMaterialActivity activity = (AllMaterialActivity) getActivity();
                                if (activity.leftAdapter.isEditing()) {
                                    materialActivity.addShiCaiToMade(scid);
                                } else {
                                    activity.getGroupMateData(scid,name);
                                }
                            } else if (parentPage == INTEREST) {
                                if (!CookbookApplication.getUser().isExist()) {
                                    interestSettingActivity.showLoginDialog();
                                    return;
                                }
                                String data = splitParams(scid);
                                httpUtils.doGet(Urls.INTEREST_DISLIKE_MATERIALS_SET
                                        + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                                        + "/field_name/like_shicai/data/"
                                        + data, new HttpResponse<String>(String.class) {
                                    @Override
                                    public void onSuccess(String str) {
                                        parentFragment.getDislikeMaterials();
                                    }

                                    @Override
                                    public void onError(String msg) {

                                    }
                                });
                            } else {
                                aWeekBranchActivity.onCheck(scid, name);
                                if (aWeekIds.contains(scid)) {
                                    aWeekIds.remove(scid);
                                    LogUtils.v("base", "remove");
                                } else {
                                    aWeekIds.add(scid);
                                    LogUtils.v("base", "add");
                                }
                                refreshFlags(aWeekIds);
                            }
                        }
                    } else {
                        //普通点击
                        if (parentPage == ALL_MATERIALS) {
                            AllMaterialActivity activity = (AllMaterialActivity) getActivity();
                            if (activity.leftAdapter.isEditing()) {
                                materialActivity.addShiCaiToMade(scid);
                            } else {
                                activity.getGroupMateData(scid,name);
                            }
                        } else if (parentPage == INTEREST) {
                            if (!CookbookApplication.getUser().isExist()) {
                                interestSettingActivity.showLoginDialog();
                                return;
                            }
                            String data = splitParams(scid);
                            httpUtils.doGet(Urls.INTEREST_DISLIKE_MATERIALS_SET
                                    + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                                    + "/field_name/like_shicai/data/"
                                    + data, new HttpResponse<String>(String.class) {
                                @Override
                                public void onSuccess(String str) {
                                    parentFragment.getDislikeMaterials();
                                }

                                @Override
                                public void onError(String msg) {

                                }
                            });
                        } else {
                            aWeekBranchActivity.onCheck(scid, name);
                            if (aWeekIds.contains(scid)) {
                                aWeekIds.remove(scid);
                            } else {
                                aWeekIds.add(scid);
                            }
                            refreshFlags(aWeekIds);
                        }
                    }
                }
            });
        }
    }

    private String splitParams(String scid) {
        String data = "";
        if (parentFragment.dislikes.contains(scid)) {
            parentFragment.dislikes.remove(scid);
        } else {
            parentFragment.dislikes.add(scid);
        }
        for (int i = 0; i < parentFragment.dislikes.size(); i++) {
            if (i != parentFragment.dislikes.size() - 1) {
                data += parentFragment.dislikes.get(i) + ",";
            } else {
                data += parentFragment.dislikes.get(i);
            }
        }
        return data;
    }

    private void open(MAdapter mAdapter) {
        mAdapter.control(true);
    }

    private void close(MAdapter mAdapter) {
        mAdapter.control(false);
    }

    private void getData() {
        String str = SpUtils.getString(context, SpKeys.ALL_M_LIST_JSON + getCateId());
        if (TextUtils.isEmpty(str)) {
            getDataFromNet();
        } else {
            if (System.currentTimeMillis() - SpUtils.getLong(context, SpKeys.ALL_M_LIST_TIME + getCateId()) > SpKeys.OUT_TIME) {
                getDataFromNet();
            } else {
                M m = gson.fromJson(str, M.class);
                handler.obtainMessage(0, m).sendToTarget();
            }
        }
    }

    private void getDataFromNet() {
        httpUtils.doGet(Urls.SHICAI_CATEGORY + getCateId(), new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                SpUtils.saveString(context, SpKeys.ALL_M_LIST_JSON + getCateId(), str);
                SpUtils.saveLong(context, SpKeys.ALL_M_LIST_TIME + getCateId(), System.currentTimeMillis());
                M m = gson.fromJson(str, M.class);
                handler.obtainMessage(0, m).sendToTarget();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    @Override
    protected void handleMsg0(Message msg) {
        M m = (M) msg.obj;
        bindingData(m);
        if (parentPage == INTEREST) {
            parentFragment.getDislikeMaterials();
        }
    }

    private void bindingData(M m) {
        List<M.ChildrenEntity> datas = m.getChildren();
        for (int i = 0; i < datas.size(); i++) {
            MAdapter mAdapter = new MAdapter(datas.get(i).getChildren(), activityIds);
            adapters.add(mAdapter);
            tvs.get(i).setText(datas.get(i).getTitle());
            gvs.get(i).setAdapter(mAdapter);
        }
    }

    public void refreshFlags(ArrayList<String> ids) {
        for (int i = 0; i < adapters.size(); i++) {
            adapters.get(i).refreshFlags(ids);
        }
    }

    /**
     * 返回各自分类id
     */
    protected abstract String getCateId();


    @Override
    public void onDestroy() {
        super.onDestroy();
        materialActivity = null;

    }
}
