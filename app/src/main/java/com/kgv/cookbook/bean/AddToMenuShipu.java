package com.kgv.cookbook.bean;

/**
 * Created by 陈可轩 on 2016/12/29 17:50
 * Email : 18627241899@163.com
 * Description :
 */
public class AddToMenuShipu {


    private String id;
    private String image;
    private String name;
    private String image_thumb;
    private String description;

    private String delId;
    private int type;


    public AddToMenuShipu(String id, String image, String name, String image_thumb, String description, int type) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.image_thumb = image_thumb;
        this.description = description;
        this.type = type;
    }

    public AddToMenuShipu(String id, String image, String name, String image_thumb, String description, String delId, int type) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.image_thumb = image_thumb;
        this.description = description;
        this.delId = delId;
        this.type = type;
    }

    public String getDelId() {

        return delId;
    }

    public void setDelId(String delId) {
        this.delId = delId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}


