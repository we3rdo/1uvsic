package com.kgv.cookbook.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.M;
import com.kgv.cookbook.holder.MHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/13 11:08
 * Email : 18627241899@163.com
 * Description :
 */
public class MAdapter extends FinalBaseAdapter<M.ChildrenEntity.SubChildrenEntity> {

    private ArrayList<String> ids;
    private boolean hasMore;
    private boolean opening = false;
    private List<M.ChildrenEntity.SubChildrenEntity> allData;
    private List<M.ChildrenEntity.SubChildrenEntity> partData;


    public MAdapter(List<M.ChildrenEntity.SubChildrenEntity> datas, ArrayList<String> ids) {
        super(datas);
        this.ids = ids;
        hasMore = mDatas.size() > 7;
        if (hasMore) {
            M.ChildrenEntity.SubChildrenEntity more = new M.ChildrenEntity.SubChildrenEntity("更多");
            allData = datas;
            allData.add(more);
            partData = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                partData.add(mDatas.get(i));
            }
            partData.add(more);
            mDatas = partData;
        }
    }

    //假设 10条数据   mDatas=10  allData=11  partData=8
    @Override
    public int getCount() {
        if (hasMore) {
            return opening ? allData.size() : partData.size();
        } else {
            return mDatas.size();
        }
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new MHolder(parent);
    }

    @Override
    protected void handleSubBusiness(int position, BaseHolder holder) {
        MHolder mHolder = (MHolder) holder;
        mHolder.iv_ok.setVisibility(ids.contains(getItemIdByPosition(position)) ? View.VISIBLE : View.GONE);
        if (hasMore) {
            if (position == getDataSize() - 1) {
                String tip = opening ? "收起" : "更多";
                mHolder.tv_name.setText(tip);
                mHolder.iv_tip.setImageResource(opening ? R.drawable.all_m_opened : R.drawable.all_m_closed);
            }
            if ("收起".equals(mHolder.tv_name.getText()) || "更多".equals(mHolder.tv_name.getText())) {
                mHolder.iv_tip.setVisibility(View.VISIBLE);
            } else {
                mHolder.iv_tip.setVisibility(View.INVISIBLE);
            }
        }
    }

    public String getItemIdByPosition(int position) {
        return mDatas.get(position).getId();
    }

    public String getNameByPosition(int position) {
        return mDatas.get(position).getTitle();
    }

    public void refreshFlags(ArrayList<String> ids) {
        this.ids = ids;
        notifyDataSetChanged();
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public boolean isOpening() {
        return opening;
    }

    public void control(boolean flag) {
        if (hasMore) {
            opening = flag;
            mDatas = flag ? allData : partData;
            notifyDataSetChanged();
        }
    }

    public int getDataSize() {
        if (hasMore) {
            return opening ? allData.size() : partData.size();
        } else {
            return mDatas.size();
        }
    }
}
