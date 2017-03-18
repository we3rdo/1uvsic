package com.kgv.cookbook.adapter;

import android.animation.ObjectAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
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
    private int currentPeople = - 1;
    private int currentHealth = - 1;
    private String newIdByPeople;
    private String newIdByHealth;

    public FamilyContentAdapter (List<FamilyContent> datas, IOnHealthItemClickListener listener) {
        super(datas);
        this.listener = listener;
    }

    @Override
    protected BaseHolder getDifferentHolder (ViewGroup parent) {
        return new FamilyContentHolder(parent);
    }

    @Override
    protected void handleSubBusiness (final int position, BaseHolder baseHolder) {
        FamilyContentHolder holder = (FamilyContentHolder) baseHolder;
        String age = mDatas.get(position).getMember_name();
        String people = mDatas.get(position).getNum() + "个";
        if ("0个".equals(people)) {
            people = "";
        }
        String health = mDatas.get(position).getHealth_name();
        holder.tv_age.setText(age);
        holder.tv_people.setText(people);
        holder.tv_health.setText(health);
        holder.iv_delete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch (View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.4f);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setAlpha(1.0f);
                        listener.onHealthItemDelete(mDatas.get(position).getId());
                        mDatas.remove(position);
                        notifyDataSetChanged();
                        break;
                }
                return true;
            }
        });
        holder.tv_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                listener.onPeopleClick(mDatas.get(position).getId(), position);
            }
        });
        holder.tv_health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                listener.onHealthClick(mDatas.get(position).getId(), position);
            }
        });
        if (position == currentPeople) {
            startAlpha(holder.f_people);
        } else {
            holder.f_people.setVisibility(View.GONE);
        }
        if (position == currentHealth) {
            startAlpha(holder.f_health);
        } else {
            holder.f_health.setVisibility(View.GONE);
        }
        if (getItemIdByPosition(position).equals(newIdByPeople)) {
            startAlpha(holder.f_people);
        }
        if (getItemIdByPosition(position).equals(newIdByHealth)){
            startAlpha(holder.f_health);
        }
    }

    public String showPeopleByNewData (List<FamilyContent> datas) {
        ArrayList<String> old = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            old.add(mDatas.get(i).getId());
        }
        old.add("empty");
        for (int i = 0; i < datas.size(); i++) {
            String newId = datas.get(i).getId();
            if (!old.contains(newId)){
                newIdByPeople = newId;
                LogUtils.v("family", "for : i=" + i + ",id=" + newIdByPeople);
            }
        }
        LogUtils.v("family", "newIdByPeople = " + newIdByPeople);
        mDatas = datas;
        currentPeople = - 1;
        currentHealth = - 1;
        notifyDataSetChanged();
        return newIdByPeople;
    }

    public String showHealthByNewData(){
        newIdByHealth = newIdByPeople;
        newIdByPeople = "-1";
        LogUtils.v("abc","newIdByPeople = " + newIdByPeople);
        LogUtils.v("abc","newIdByHealth = " + newIdByHealth);
        currentPeople = - 1;
        currentHealth = - 1;
        notifyDataSetChanged();
        return newIdByHealth;
    }

    public void showPeople (int position) {
        currentPeople = position;
        hideHealth();
    }

    public void showHealth (int position) {
        currentHealth = position;
        hidePeople();
    }

    public void hidePeople () {
        currentPeople = - 1;
        notifyDataSetChanged();
    }

    public void hideHealth () {
        currentHealth = - 1;
        notifyDataSetChanged();
    }

    public void clearNewIdByPeople () {
        newIdByPeople = "-1";
        notifyDataSetChanged();
    }

    public void clearNewIdByHealth(){
        newIdByHealth = "-1";
        notifyDataSetChanged();
    }

    private void startAlpha (View view) {
        view.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        animator.setDuration(1200);
        animator.start();
    }

    private String getItemIdByPosition (int position) {
        return mDatas.get(position).getId();
    }

    public void refreshData (List<FamilyContent> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }
}
