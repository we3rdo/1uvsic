package com.kgv.cookbook.listener;

/**
 * Created by 陈可轩 on 2017/1/12 17:19
 * Email : 18627241899@163.com
 * Description :
 */
public interface IOnHealthItemClickListener {

    void onHealthItemDelete(String id);

    void onPeopleClick(String id,int position);

    void onHealthClick(String id,int position);
}
