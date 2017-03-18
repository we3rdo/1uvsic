package com.kgv.cookbook.fragment.interest;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.reflect.TypeToken;
import com.kgv.cookbook.R;
import com.kgv.cookbook.adapter.TitleAdapter;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.bean.InterestFlag;
import com.kgv.cookbook.config.Strings;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.fragment.materrials.AquaticFragment;
import com.kgv.cookbook.fragment.materrials.BeanFragment;
import com.kgv.cookbook.fragment.materrials.CondimentFragment;
import com.kgv.cookbook.fragment.materrials.FruitsFragment;
import com.kgv.cookbook.fragment.materrials.MeatFragment;
import com.kgv.cookbook.fragment.materrials.VegetableFragment;
import com.kgv.cookbook.util.HttpResponse;
import com.kgv.cookbook.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/11 11:48
 * Email : 18627241899@163.com
 * Description : 主页-设置->喜好设置-食材分类(1)
 */
public class InterestMaterialsFragment extends BaseFragment {

    private FragmentManager fragmentManager;
    private MeatFragment meatFragment;
    private VegetableFragment vegetableFragment;
    private BeanFragment beanFragment;
    private AquaticFragment aquaticFragment;
    private FruitsFragment fruitsFragment;
    private CondimentFragment condimentFragment;
    private int currentFragment = -1;
    public ArrayList<String> dislikes;
    private TitleAdapter titleAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_interest_materials;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        fragmentManager = getChildFragmentManager();
        initUI(contentView);
        showFragment(0);
    }

    private void initUI(View contentView) {
        GridView gv_title = (GridView) contentView.findViewById(R.id.gv_title);
        titleAdapter = new TitleAdapter(Strings.getMaterials());
        gv_title.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                titleAdapter.selectItem(position);
                showFragment(position);
            }
        });
        gv_title.setAdapter(titleAdapter);
    }

    public void showFragment(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        hideFragments(transaction);

        switch (position) {
            case 0:
                currentFragment = 0;
                if (meatFragment == null) {
                    meatFragment = new MeatFragment();
                    transaction.add(R.id.mContent, meatFragment);
                } else {
                    transaction.show(meatFragment);
                }
                break;
            case 1:
                currentFragment = 1;
                if (vegetableFragment == null) {
                    vegetableFragment = new VegetableFragment();
                    transaction.add(R.id.mContent, vegetableFragment);
                } else {
                    transaction.show(vegetableFragment);
                }
                break;
            case 2:
                currentFragment = 2;
                if (beanFragment == null) {
                    beanFragment = new BeanFragment();
                    transaction.add(R.id.mContent, beanFragment);
                } else {
                    transaction.show(beanFragment);
                }
                break;
            case 3:
                currentFragment = 3;
                if (aquaticFragment == null) {
                    aquaticFragment = new AquaticFragment();
                    transaction.add(R.id.mContent, aquaticFragment);
                } else {
                    transaction.show(aquaticFragment);
                }
                break;
            case 4:
                currentFragment = 4;
                if (fruitsFragment == null) {
                    fruitsFragment = new FruitsFragment();
                    transaction.add(R.id.mContent, fruitsFragment);
                } else {
                    transaction.show(fruitsFragment);
                }
                break;
            case 5:
                currentFragment = 5;
                if (condimentFragment == null) {
                    condimentFragment = new CondimentFragment();
                    transaction.add(R.id.mContent, condimentFragment);
                } else {
                    transaction.show(condimentFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (meatFragment != null) {
            transaction.hide(meatFragment);
        }
        if (vegetableFragment != null) {
            transaction.hide(vegetableFragment);
        }
        if (beanFragment != null) {
            transaction.hide(beanFragment);
        }
        if (aquaticFragment != null) {
            transaction.hide(aquaticFragment);
        }
        if (fruitsFragment != null) {
            transaction.hide(fruitsFragment);
        }
        if (condimentFragment != null) {
            transaction.hide(condimentFragment);
        }
    }

    public void getDislikeMaterials() {
        httpUtils.doGet(Urls.INTEREST_DISLIKE_MATERIALS_LIST
                + "username/" + mUser.getUsername() + "/password/" + mUser.getPassword()
                + "/field_name/like_shicai/", new HttpResponse<String>(String.class) {
            @Override
            public void onSuccess(String str) {
                LogUtils.v("interest", "getDislikeMaterials");
                if (!"null".equals(str)) {
                    List<InterestFlag> flags = gson.fromJson(str, new TypeToken<List<InterestFlag>>() {
                    }.getType());
                    handler.obtainMessage(0, flags).sendToTarget();
                }
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    @Override
    protected void handleMsg0(Message msg) {
        List<InterestFlag> flags = (List<InterestFlag>) msg.obj;
        dislikes = new ArrayList<>();
        for (int i = 0; i < flags.size(); i++) {
            dislikes.add(flags.get(i).getId());
        }
        refreshAllMateFlags(dislikes);
    }

    public void refreshAllMateFlags(ArrayList<String> ids) {
        switch (currentFragment) {
            case 0:
                meatFragment.refreshFlags(ids);
                break;
            case 1:
                vegetableFragment.refreshFlags(ids);
                break;
            case 2:
                beanFragment.refreshFlags(ids);
                break;
            case 3:
                aquaticFragment.refreshFlags(ids);
                break;
            case 4:
                fruitsFragment.refreshFlags(ids);
                break;
            case 5:
                condimentFragment.refreshFlags(ids);
                break;
        }
    }
}
