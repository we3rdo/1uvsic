package com.kgv.cookbook.holder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.SearchResult;
import com.kgv.cookbook.config.SpKeys;
import com.kgv.cookbook.config.Urls;
import com.kgv.cookbook.config.AppUtils;
import com.kgv.cookbook.util.ImageUtils;
import com.kgv.cookbook.util.Md5Helper;
import com.kgv.cookbook.util.SpUtils;

import java.io.File;

/**
 * Created by 陈可轩 on 2016/12/13 11:09
 * Email : 18627241899@163.com
 * Description :
 */
public class SearchResultHolder extends BaseHolder<SearchResult.ContentEntity> {

    private ImageView iv_img;
    private TextView tv_name;
    public TextView tv_m;
    public TextView tv_a;
    public TextView tv_n;

    public SearchResultHolder(ViewGroup parent) {
        super(parent);
    }

    @Override
    public View initHolderViewAndFBI(ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_recommend,null);
        iv_img = (ImageView) view.findViewById(R.id.iv_img);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_m = (TextView) view.findViewById(R.id.tv_m);
        tv_a = (TextView) view.findViewById(R.id.tv_a);
        tv_n = (TextView) view.findViewById(R.id.tv_n);
        return view;
    }

    @Override
    public void bindingData(SearchResult.ContentEntity data, ViewGroup parent) {
        String name = data.getName();
        if ("鲜嫩多汁团圆翠玉饺子".equals(name)){
            name = "鲜嫩翠玉饺子";
        }
        if("健康饮食----清蒸鲫鱼".equals(name)){
            name = "清蒸鲫鱼";
        }
        if("止咳化痰冰糖白果银耳粥".equals(name)){
            name = "冰糖白果银耳粥";
        }
        tv_name.setText(name);
        String imageUrl = Urls.BASE_IMG_URL + data.getImage_thumb();

        //是否下载过
        String download = SpUtils.getString(AppUtils.getContext(), SpKeys.DOWNLOAD_IMG_SUCCESS);
        if ("t".equals(download)){
            String md5ImageName = Md5Helper.toMD5(imageUrl);
            File file = ImageUtils.getImageByName(md5ImageName);
            if (file != null && file.exists()){
                Glide.with(parent.getContext())
                        .load(file)
                        .into(iv_img);
            }else{
                Glide.with(parent.getContext())
                        .load(imageUrl)
                        .into(iv_img);
            }
            return;
        }
        //没有下载过
        Glide.with(parent.getContext())
                .load(imageUrl)
                .into(iv_img);

    }
}
