package com.kgv.cookbook.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseFragment;
import com.kgv.cookbook.fragment.materrials.AquaticFragment;
import com.kgv.cookbook.fragment.materrials.BeanFragment;
import com.kgv.cookbook.fragment.materrials.CondimentFragment;
import com.kgv.cookbook.fragment.materrials.FruitsFragment;
import com.kgv.cookbook.fragment.materrials.MeatFragment;
import com.kgv.cookbook.fragment.materrials.VegetableFragment;

import java.util.ArrayList;

/**
 * Created by 陈可轩 on 2016/12/6 13:05
 * Email : 18627241899@163.com
 * Description : 主页-食谱->食材大全-查看全部(1)
 */
public class MaterialsFragment extends BaseFragment implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private TextView tv0, tv1, tv2, tv3, tv4, tv5;
    private MeatFragment meatFragment;
    private VegetableFragment vegetableFragment;
    private BeanFragment beanFragment;
    private AquaticFragment aquaticFragment;
    private FruitsFragment fruitsFragment;
    private CondimentFragment condimentFragment;
    private int currentFragment = -1;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_materials;
    }

    @Override
    protected void initialization(View contentView, Bundle savedInstanceState) {
        fragmentManager = getChildFragmentManager();
        initUI(contentView);
        showFragment(0);
    }

    private void initUI(View contentView) {
        tv0 = (TextView) contentView.findViewById(R.id.tv0);
        tv0.setOnClickListener(this);
        tv1 = (TextView) contentView.findViewById(R.id.tv1);
        tv1.setOnClickListener(this);
        tv2 = (TextView) contentView.findViewById(R.id.tv2);
        tv2.setOnClickListener(this);
        tv3 = (TextView) contentView.findViewById(R.id.tv3);
        tv3.setOnClickListener(this);
        tv4 = (TextView) contentView.findViewById(R.id.tv4);
        tv4.setOnClickListener(this);
        tv5 = (TextView) contentView.findViewById(R.id.tv5);
        tv5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv0:
                showFragment(0);
                break;
            case R.id.tv1:
                showFragment(1);
                break;
            case R.id.tv2:
                showFragment(2);
                break;
            case R.id.tv3:
                showFragment(3);
                break;
            case R.id.tv4:
                showFragment(4);
                break;
            case R.id.tv5:
                showFragment(5);
                break;
        }
    }
    private void showFragment(int position) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        hideFragments(transaction);

        switch (position) {
            case 0:
                currentFragment = 0;
                tv0.setSelected(true);
                if (meatFragment == null) {
                    meatFragment = new MeatFragment();
                    transaction.add(R.id.mContent, meatFragment);
                } else {
                    transaction.show(meatFragment);
                }
                break;
            case 1:
                currentFragment = 1;
                tv1.setSelected(true);
                if (vegetableFragment == null) {
                    vegetableFragment = new VegetableFragment();
                    transaction.add(R.id.mContent, vegetableFragment);
                } else {
                    transaction.show(vegetableFragment);
                }
                break;
            case 2:
                currentFragment = 2;
                tv2.setSelected(true);
                if (beanFragment == null) {
                    beanFragment = new BeanFragment();
                    transaction.add(R.id.mContent, beanFragment);
                } else {
                    transaction.show(beanFragment);
                }
                break;
            case 3:
                currentFragment = 3;
                tv3.setSelected(true);
                if (aquaticFragment == null) {
                    aquaticFragment = new AquaticFragment();
                    transaction.add(R.id.mContent, aquaticFragment);
                } else {
                    transaction.show(aquaticFragment);
                }
                break;
            case 4:
                currentFragment = 4;
                tv4.setSelected(true);
                if (fruitsFragment == null) {
                    fruitsFragment = new FruitsFragment();
                    transaction.add(R.id.mContent, fruitsFragment);
                } else {
                    transaction.show(fruitsFragment);
                }
                break;
            case 5:
                currentFragment = 5;
                tv5.setSelected(true);
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
        tv0.setSelected(false);
        tv1.setSelected(false);
        tv2.setSelected(false);
        tv3.setSelected(false);
        tv4.setSelected(false);
        tv5.setSelected(false);
    }

    public void refreshAllMateFlags(ArrayList<String> ids){
        switch (currentFragment){
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
