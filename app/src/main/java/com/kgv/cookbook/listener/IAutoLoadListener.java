package com.kgv.cookbook.listener;

import android.view.View;
import android.widget.AbsListView;

import com.kgv.cookbook.util.LogUtils;

/**
 * Created by 陈可轩 on 2017/2/25 10:37
 * Email : 18627241899@163.com
 * Description :
 */
public class IAutoLoadListener implements AbsListView.OnScrollListener {

    private AutoLoadCallBack callBack;
    private int getLastVisiblePosition = 0, lastVisiblePositionY = 0;

    public interface AutoLoadCallBack {
        void execute();
    }

    public IAutoLoadListener(AutoLoadCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            //滚动到底部
            if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                View v = view.getChildAt(view.getChildCount() - 1);
                int[] location = new int[2];
                v.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
                int y = location[1];

                if (view.getLastVisiblePosition() != getLastVisiblePosition && lastVisiblePositionY != y) {//第一次拖至底部
                    LogUtils.v("listener", "加载一次");
                    callBack.execute();
                    //getLastVisiblePosition = view.getLastVisiblePosition();
                    //lastVisiblePositionY = y;
                    return;
                } else if (view.getLastVisiblePosition() == getLastVisiblePosition && lastVisiblePositionY == y) {//第二次拖至底部{
                    //LogUtils.v("listener", "第二次");
                }

            }
            //未滚动到底部，第二次拖至底部都初始化
            getLastVisiblePosition = 0;
            lastVisiblePositionY = 0;
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
