package com.kgv.cookbook.adapter;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.base.FinalBaseAdapter;
import com.kgv.cookbook.bean.MenuAndBlogShiPu;
import com.kgv.cookbook.holder.HealthBlogHolder;
import com.kgv.cookbook.listener.IOnBlogDeleteListener;
import com.kgv.cookbook.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2017/1/10 16:02
 * Email : 18627241899@163.com
 * Description :
 */
public class HealthBlogAdapter extends FinalBaseAdapter<MenuAndBlogShiPu> {

    private IOnBlogDeleteListener listener;
    private int type = -1;
    private boolean opening;
    private boolean hasMore;
    private List<MenuAndBlogShiPu> all;
    private List<MenuAndBlogShiPu> part;
    private boolean hintDel = false;

    public HealthBlogAdapter(List<MenuAndBlogShiPu> datas, IOnBlogDeleteListener listener, int type,boolean hintDel) {
        super(datas);
        this.hintDel = hintDel;
        this.listener = listener;
        this.type = type;
        opening = false;
        hasMore = datas.size() > 3;
        if (hasMore) {
            all = datas;
            part = new ArrayList<>();
            part.add(datas.get(0));
            part.add(datas.get(1));
            part.add(datas.get(2));
            mDatas = part;
        } else {
            mDatas = datas;
        }
    }

    @Override
    protected BaseHolder getDifferentHolder(ViewGroup parent) {
        return new HealthBlogHolder(parent);
    }

    @Override
    protected void handleSubBusiness(final int position, BaseHolder baseHolder) {
        HealthBlogHolder holder = (HealthBlogHolder) baseHolder;
        String name = mDatas.get(position).getName();
        holder.iv_del.setVisibility(hintDel ? View.INVISIBLE : View.VISIBLE);
        if (TextUtils.isEmpty(name)) {
            holder.tv_name.setVisibility(View.INVISIBLE);
            holder.iv_del.setVisibility(View.INVISIBLE);
        }
        holder.tv_name.setText(position + 1 + "." + name);
        holder.iv_del.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.4f);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setAlpha(1.0f);
                        LogUtils.v("xkc","start : " + System.currentTimeMillis());
                        listener.onBlogDelete(mDatas.get(position).getId(), type);
                        LogUtils.v("xkc","activity end : " + System.currentTimeMillis());
                        LogUtils.v("what", "position = " + position);
                        if (hasMore) {
                            LogUtils.v("what", "0.start.  mData=" + mDatas.size() +
                                    "   all=" + all.size() + "  part=" + part.size());
                            all.remove(position);
                            LogUtils.v("what", "1.all remove.  mData=" + mDatas.size() +
                                    "   all=" + all.size() + "  part=" + part.size());
                            if (opening) {
                                mDatas = all;
                                LogUtils.v("what", "2.set  mDatas.  mData=" + mDatas.size() +
                                        "   all=" + all.size() + "  part=" + part.size());
                            } else {
                                part.remove(position);
                                LogUtils.v("what", "2.part remove.  mData=" + mDatas.size() +
                                        "   all=" + all.size() + "  part=" + part.size());
                            }
                            if (mDatas.size() < 3) {
                                if (all.size() <= 3) {
                                    part = all;
                                    hasMore = false;
                                    listener.onChangeTitle(type);
                                } else {
                                    part = new ArrayList<>();
                                    part.add(all.get(0));
                                    part.add(all.get(1));
                                    part.add(all.get(2));
                                }
                                mDatas = part;
                                LogUtils.v("what", "3.reset mDatas.  mData=" + mDatas.size() +
                                        "   all=" + all.size() + "  part=" + part.size());
                            }
                        } else {
                            //没有更多
                            mDatas.remove(position);
                        }
                        notifyDataSetChanged();
                        LogUtils.v("xkc","adapter end : " + System.currentTimeMillis());
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public int getCount() {
        if (hasMore) {
            return opening ? all.size() : part.size();
        } else {
            return mDatas.size();
        }
    }

    public void control(boolean flag) {
        if (hasMore) {
            opening = flag;
            mDatas = flag ? all : part;
            notifyDataSetChanged();
        }
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHintDel(boolean b){
        this.hintDel = b;
        notifyDataSetChanged();
    }

}
