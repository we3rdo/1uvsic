package com.kgv.cookbook.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.kgv.cookbook.R;


/**
 * Created by 陈可轩 on 2017/3/3 17:54
 * Email : 18627241899@163.com
 * Description :
 */
public class RecipeStepImageView extends RelativeLayout {

    private PhotoView photoView;
    private TextView textView;
    private TextView tv_position;
    private Context context;

    public RecipeStepImageView(Context context) {
        super(context);
        this.context = context;
        init(context);
    }

    public RecipeStepImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecipeStepImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.view_step_image,this);
        photoView = (PhotoView) this.findViewById(R.id.photoView);
        textView = (TextView) this.findViewById(R.id.textView);
        tv_position = (TextView) this.findViewById(R.id.tv_position);
        photoView.enable();
    }

    public void setImage(String url){
        Glide.with(context).load(url).into(photoView);
    }

    public void setDescription(String description){
        textView.setText(description);
    }

    public void setPosition(String position){
        tv_position.setText(position);
    }

    public void setListener(OnClickListener listener){
        photoView.setOnClickListener(listener);
    }

    public void hintPosition(){
        tv_position.setVisibility(View.GONE);
    }

}
