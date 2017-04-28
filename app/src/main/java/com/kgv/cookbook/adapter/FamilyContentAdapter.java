package com.kgv.cookbook.adapter;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.BaseOnTouchListener;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.FamilyContent;
import com.kgv.cookbook.holder.FamilyContentHolder;
import com.kgv.cookbook.listener.IOnHealthItemClickListener;
import com.kgv.cookbook.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2017/1/12 16:27
 * Email : 18627241899@163.com
 * Description :
 */
public class FamilyContentAdapter extends FinalBaseAdapter<FamilyContent> {

    private IOnHealthItemClickListener listener;
    private int currentPeople = -1;
    private int currentHealth = -1;
    private String newIdByPeople;
    private String newIdByHealth;
    private int doNotSetPeople = -1;
    private int doNotSetHealth = -1;

    public FamilyContentAdapter(List<FamilyContent> datas, IOnHealthItemClickListener listener) {
        super(datas);
        this.listener = listener;
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new FamilyContentHolder(parent);
    }

    @Override
    protected void handleSubBusiness(final int position, BaseHolder baseHolder) {
        final FamilyContentHolder holder = (FamilyContentHolder) baseHolder;
        BaseOnTouchListener onTouchListener = new BaseOnTouchListener() {
            @Override
            protected void onViewTouch(View v) {
                switch (v.getId()) {
                    case R.id.tv_people:
                        if ("点击设置".equals(holder.tv_people.getText().toString())) {
                            doNotSetPeople = position;
                            doNotSetHealth = -1;
                            notifyDataSetChanged();
                        }else{
                            doNotSetPeople = -1;
                            doNotSetHealth = -1;
                        }
                        listener.onPeopleClick(mDatas.get(position).getId(), position);
                        break;
                    case R.id.tv_health:
                        if ("点击设置".equals(holder.tv_health.getText().toString())) {
                            doNotSetHealth = position;
                            doNotSetPeople = -1;
                            notifyDataSetChanged();
                        }
                        else{
                            doNotSetPeople = -1;
                            doNotSetHealth = -1;
                        }
                        listener.onHealthClick(mDatas.get(position).getId(), position);
                        break;
                    case R.id.iv_delete:
                        listener.onItemDelete(mDatas.get(position).getId());
                        mDatas.remove(position);
                        notifyDataSetChanged();
                        break;
                    case R.id.iv_people_del:
                        mDatas.get(position).setNum("0");
                        listener.onPeopleDeleteClick(mDatas.get(position).getId());
                        notifyDataSetChanged();
                        break;
                    case R.id.iv_health_del:
                        mDatas.get(position).setHealth_name("");
                        listener.onHealthDeleteClick(mDatas.get(position).getId());
                        notifyDataSetChanged();
                        break;
                }
            }
        };
        holder.iv_delete.setOnTouchListener(onTouchListener);
        holder.tv_people.setOnTouchListener(onTouchListener);
        holder.tv_health.setOnTouchListener(onTouchListener);
        holder.iv_people_del.setOnTouchListener(onTouchListener);
        holder.iv_health_del.setOnTouchListener(onTouchListener);
        if (position == currentPeople) {
            startAlpha(holder.f_people);
            holder.iv_people_del.setVisibility(View.VISIBLE);
        } else {
            holder.f_people.setVisibility(View.GONE);
            holder.iv_people_del.setVisibility(View.GONE);
        }
        if (position == currentHealth) {
            startAlpha(holder.f_health);
            holder.iv_health_del.setVisibility(View.VISIBLE);
        } else {
            holder.f_health.setVisibility(View.GONE);
            holder.iv_health_del.setVisibility(View.GONE);
        }
        if (getItemIdByPosition(position).equals(newIdByPeople)) {
            startAlpha(holder.f_people);
        }
        if (getItemIdByPosition(position).equals(newIdByHealth)) {
            startAlpha(holder.f_health);
        }
        if (position == doNotSetPeople) {
            holder.tv_people.setText("");
            //doNotSetPeople = -1;
            holder.iv_people_del.setVisibility(View.GONE);
        }
        if (position == doNotSetHealth) {
            holder.tv_health.setText("");
            //doNotSetHealth = -1;
            holder.iv_health_del.setVisibility(View.GONE);
        }
    }

    /*
        比对集合 根据id显示新添加item
     */
    public String showPeopleByNewData(List<FamilyContent> datas) {

        if(mDatas == null || mDatas.isEmpty() || mDatas.size() == 0){
            newIdByPeople = datas.get(0).getId();
        }else{
            ArrayList<String> old = new ArrayList<>();
            for (int i = 0; i < mDatas.size(); i++) {
                old.add(mDatas.get(i).getId());
            }
            old.add("empty");
            for (int i = 0; i < datas.size(); i++) {
                String newId = datas.get(i).getId();
                if (!old.contains(newId)) {
                    newIdByPeople = newId;
                }
            }
        }
        mDatas = datas;
        currentPeople = -1;
        currentHealth = -1;
        notifyDataSetChanged();
        return newIdByPeople;
    }

    /*
       根据people id显示新添加item
     */
    public String showHealthByNewData() {
        newIdByHealth = newIdByPeople;
        newIdByPeople = "-1";
        LogUtils.v("abc", "newIdByPeople = " + newIdByPeople);
        LogUtils.v("abc", "newIdByHealth = " + newIdByHealth);
        currentPeople = -1;
        currentHealth = -1;
        notifyDataSetChanged();
        return newIdByHealth;
    }

    public void showPeople(int position) {
        currentPeople = position;
        hideHealth();
    }

    public void showHealth(int position) {
        currentHealth = position;
        hidePeople();
    }

    public void hidePeople() {
        currentPeople = -1;
        notifyDataSetChanged();
    }

    public void hideHealth() {
        currentHealth = -1;
        notifyDataSetChanged();
    }

    public void clearNewIdByPeople() {
        newIdByPeople = "-1";
        notifyDataSetChanged();
    }

    public void clearNewIdByHealth() {
        newIdByHealth = "-1";
        notifyDataSetChanged();
    }

    private void startAlpha(View view) {
        view.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        animator.setDuration(1200);
        animator.start();
    }

    private String getItemIdByPosition(int position) {
        return mDatas.get(position).getId();
    }

    public int getAgeIdByPosition(int position){
        String name = mDatas.get(position).getMember_name();
        if ("婴儿".equals(name)){
            return 0;
        }else if ("幼儿".equals(name)){
            return 1;
        }else if ("儿童".equals(name)){
            return 2;
        }else if ("青少年".equals(name)){
            return 3;
        }else if ("成年人".equals(name)){
            return 4;
        }else if ("中年人".equals(name)){
            return 5;
        }else if ("老年人".equals(name)){
            return 6;
        }else if ("备孕".equals(name)){
            return 7;
        }else if ("孕妇".equals(name)){
            return 8;
        }else if ("产妇".equals(name)){
            return 9;
        }
        return 0;
    }

    public void refreshData(List<FamilyContent> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }
}
