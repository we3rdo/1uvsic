package com.frescoimageviewer;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.image.ImageInfo;
import com.frescoimageviewer.drawee.ZoomableDraweeView;

import java.util.ArrayList;
import java.util.List;


class ImageViewerAdapter extends PagerAdapter {

    private Context context;
    private List<String> urls;
    private List<ZoomableDraweeView> drawees;

    private GenericDraweeHierarchyBuilder hierarchyBuilder;

    public ImageViewerAdapter(Context context, List<String> urls,
                              GenericDraweeHierarchyBuilder hierarchyBuilder) {
        this.context = context;
        this.urls = urls;
        this.hierarchyBuilder = hierarchyBuilder;
        generateDrawees();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position % urls.size();
        ZoomableDraweeView photoDraweeView = drawees.get(realPosition);

        try {
            container.addView(photoDraweeView, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return photoDraweeView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ZoomableDraweeView) object);
    }

    public boolean isScaled(int index) {
        int realPosition = index % urls.size();
        return drawees.get(realPosition).getScale() > 1.0f;
    }

    public void resetScale(int index) {
        int realPosition = index % urls.size();
        drawees.get(realPosition).setScale(1.0f, true);
    }

    public String getUrl(int index) {
        int realPosition = index % urls.size();
        return urls.get(realPosition);
    }

    private void generateDrawees() {
        drawees = new ArrayList<>();
        for (String url : urls) {
            drawees.add(getDrawee(url));
        }
    }

    private ZoomableDraweeView getDrawee(String url) {
        ZoomableDraweeView drawee = new ZoomableDraweeView(context);

        if (hierarchyBuilder != null) {
            hierarchyBuilder.setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
            drawee.setHierarchy(hierarchyBuilder.build());
        }

        PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder();
        controllerBuilder.setUri(url);
        controllerBuilder.setOldController(drawee.getController());
        controllerBuilder.setControllerListener(getDraweeControllerListener(drawee));
        drawee.setController(controllerBuilder.build());

        return drawee;
    }

    private BaseControllerListener<ImageInfo>
    getDraweeControllerListener(final ZoomableDraweeView drawee) {
        return new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null) {
                    return;
                }
                drawee.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        };
    }
}
