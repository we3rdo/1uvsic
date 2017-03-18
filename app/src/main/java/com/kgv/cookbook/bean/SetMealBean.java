package com.kgv.cookbook.bean;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/20 17:47
 * Email : 18627241899@163.com
 * Description :
 */
public class SetMealBean {

    private String id;
    private String uid;
    private String create_time;
    private String title;
    private String cid;
    private String people_num;
    private String note;
    private List<ImageEntity> recipe_cookbook;
    private ImageEntity cover;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPeople_num() {
        return people_num;
    }

    public void setPeople_num(String people_num) {
        this.people_num = people_num;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<ImageEntity> getRecipe_cookbook() {
        return recipe_cookbook;
    }

    public void setRecipe_cookbook(List<ImageEntity> recipe_cookbook) {
        this.recipe_cookbook = recipe_cookbook;
    }

    public ImageEntity getCover() {
        return cover;
    }

    public void setCover(ImageEntity cover) {
        this.cover = cover;
    }

    public static class ImageEntity {
        private String name;
        private String id;
        private String image;
        private String image_thumb;

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
    }
}
