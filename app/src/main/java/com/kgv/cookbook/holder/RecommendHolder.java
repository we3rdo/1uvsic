package com.kgv.cookbook.holder;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kgv.cookbook.R;
import com.kgv.cookbook.base.BaseHolder;
import com.kgv.cookbook.bean.ShiPu;
import com.kgv.cookbook.config.Urls;

/**
 * Created by 陈可轩 on 2016/12/13 11:09
 * Email : 18627241899@163.com
 * Description :
 */
public class RecommendHolder extends BaseHolder<ShiPu> {

    private ImageView iv_img;
    private TextView tv_name;
    public TextView tv_m;
    public TextView tv_a;
    public TextView tv_n;
    private ImageView iv_play;

    public RecommendHolder(ViewGroup parent) {
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
        iv_play = (ImageView) view.findViewById(R.id.iv_play);
        return view;
    }

    @Override
    public void bindingData(ShiPu data, ViewGroup parent) {
        tv_name.setText(data.getName());
        String imageUrl = Urls.BASE_IMG_URL + data.getImage();
        iv_play.setVisibility(TextUtils.isEmpty(data.getFlash_src()) ? View.GONE : View.VISIBLE);
//        //是否下载过
//        String download = SpUtils.getString(AppUtils.getContext(), SpKeys.DOWNLOAD_IMG_SUCCESS);
//        if ("t".equals(download)){
//            String md5ImageName = Md5Helper.toMD5(imageUrl);
//            File file = ImageUtils.getImageByName(md5ImageName);
//            if (file != null && file.exists()){
//                Glide.with(parent.getContext())
//                        .load(file)
//                        .into(iv_img);
//            }else{
//                Glide.with(parent.getContext())
//                        .load(imageUrl)
//                        .into(iv_img);
//            }
//            return;
//        }
        //没有下载过
        Glide.with(parent.getContext())
                .load(imageUrl)
                .into(iv_img);
    }
}
