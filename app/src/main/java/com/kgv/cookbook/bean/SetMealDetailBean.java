package com.kgv.cookbook.bean;

/**
 *  Created by ckx on 2017/3/13.
 */
public class SetMealDetailBean {

    private String name;
    private String id;
    private String image;
    private String image_thumb;


    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getImage () {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
    }

    public String getImage_thumb () {
        return image_thumb;
    }

    public void setImage_thumb (String image_thumb) {
        this.image_thumb = image_thumb;
    }
}
