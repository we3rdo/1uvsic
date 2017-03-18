package com.kgv.cookbook.base;

import java.util.List;

/**
 *  Created by ckx on 2017/3/16.
 */
public abstract class BaseHasMoreAdapter<T> extends FinalBaseAdapter<T> {

    protected boolean hasMore;
    protected boolean isOpening;

    public BaseHasMoreAdapter (List<T> datas) {
        super(datas);
        isOpening = false;
        hasMore = mDatas.size() > getDefaultShowSize();
    }

    @Override
    public int getCount () {
        if (hasMore){
            return isOpening ? mDatas.size() : getDefaultShowSize();
        }else{
            return mDatas.size();
        }
    }

    public void control(boolean flag){
        if (hasMore){
            isOpening = flag;
            notifyDataSetChanged();
        }
    }

    public boolean isOpening(){
        return isOpening;
    }

    public boolean isHasMore(){
        return hasMore;
    }

    protected abstract int getDefaultShowSize ();


}
