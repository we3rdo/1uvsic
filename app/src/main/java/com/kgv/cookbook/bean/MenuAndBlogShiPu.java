package com.kgv.cookbook.bean;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/30 17:46
 * Email : 18627241899@163.com
 * Description :
 */
public class MenuAndBlogShiPu {

    private String cookbook_id;
    private String proper_txt;
    private String effect_txt;
    private String description;
    private String collected;
    private String taboo_txt;
    private String tips;
    private String uid;
    private String update_time;
    private String view;
    private String people_num;
    private String category_id;
    private String id;
    private String maked;
    private String hao_shi;
    private String kitchen;
    private String effect_label;
    private String image;
    private String create_time;
    private String gong_yi;
    private String like;
    private String level;
    private String sort;
    private String suggest;
    private String image_thumb;
    private String brand_id;
    private List<NutritionEntity> nutrition;
    private String taboo_label;
    private String proper_label;
    private String flash_src;
    private String name;
    private String status;

    public MenuAndBlogShiPu(String name) {
        this.name = name;
    }

    public void setCookbook_id(String cookbook_id) {
        this.cookbook_id = cookbook_id;
    }

    public void setProper_txt(String proper_txt) {
        this.proper_txt = proper_txt;
    }

    public void setEffect_txt(String effect_txt) {
        this.effect_txt = effect_txt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCollected(String collected) {
        this.collected = collected;
    }

    public void setTaboo_txt(String taboo_txt) {
        this.taboo_txt = taboo_txt;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setPeople_num(String people_num) {
        this.people_num = people_num;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMaked(String maked) {
        this.maked = maked;
    }

    public void setHao_shi(String hao_shi) {
        this.hao_shi = hao_shi;
    }

    public void setKitchen(String kitchen) {
        this.kitchen = kitchen;
    }

    public void setEffect_label(String effect_label) {
        this.effect_label = effect_label;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setGong_yi(String gong_yi) {
        this.gong_yi = gong_yi;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public void setNutrition(List<NutritionEntity> nutrition) {
        this.nutrition = nutrition;
    }

    public void setTaboo_label(String taboo_label) {
        this.taboo_label = taboo_label;
    }

    public void setProper_label(String proper_label) {
        this.proper_label = proper_label;
    }

    public void setFlash_src(String flash_src) {
        this.flash_src = flash_src;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCookbook_id() {
        return cookbook_id;
    }

    public String getProper_txt() {
        return proper_txt;
    }

    public String getEffect_txt() {
        return effect_txt;
    }

    public String getDescription() {
        return description;
    }

    public String getCollected() {
        return collected;
    }

    public String getTaboo_txt() {
        return taboo_txt;
    }

    public String getTips() {
        return tips;
    }

    public String getUid() {
        return uid;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public String getView() {
        return view;
    }

    public String getPeople_num() {
        return people_num;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getId() {
        return id;
    }

    public String getMaked() {
        return maked;
    }

    public String getHao_shi() {
        return hao_shi;
    }

    public String getKitchen() {
        return kitchen;
    }

    public String getEffect_label() {
        return effect_label;
    }

    public String getImage() {
        return image;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getGong_yi() {
        return gong_yi;
    }

    public String getLike() {
        return like;
    }

    public String getLevel() {
        return level;
    }

    public String getSort() {
        return sort;
    }

    public String getSuggest() {
        return suggest;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public List<NutritionEntity> getNutrition() {
        return nutrition;
    }

    public String getTaboo_label() {
        return taboo_label;
    }

    public String getProper_label() {
        return proper_label;
    }

    public String getFlash_src() {
        return flash_src;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public static class NutritionEntity {
        /**
         * val : null
         * unit : 大卡
         * name : 热量
         */
        private double val;
        private String unit;
        private String name;

        public void setVal(double val) {
            this.val = val;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getVal() {
            return val;
        }

        public String getUnit() {
            return unit;
        }

        public String getName() {
            return name;
        }
    }
}
