package com.kgv.cookbook.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/23 15:55
 * Email : 18627241899@163.com
 * Description :
 */
public class SuggestNutrition implements Serializable{

    private String uint;
    private List<RecipeEntity> blog_breakfast;
    private List<RecipeEntity> blog_lunch;
    private List<RecipeEntity> blog_dinner;
    private List<RecipeEntity> health_breakfast;
    private List<RecipeEntity> health_lunch;
    private List<RecipeEntity> health_dinner;
    private double blog_cookbook;
    private double blog_sum;
    private String blog_status;
    private double health_cookbook;
    private String health_status;
    private double health_sum;
    private double gu_wu_sum;
    private double milk_sum;
    private double suggest_l;
    private double suggest_h;
    private String suggest;

    public String getUint() {
        return uint;
    }

    public void setUint(String uint) {
        this.uint = uint;
    }

    public List<RecipeEntity> getBlog_breakfast() {
        return blog_breakfast;
    }

    public void setBlog_breakfast(List<RecipeEntity> blog_breakfast) {
        this.blog_breakfast = blog_breakfast;
    }

    public List<RecipeEntity> getBlog_lunch() {
        return blog_lunch;
    }

    public void setBlog_lunch(List<RecipeEntity> blog_lunch) {
        this.blog_lunch = blog_lunch;
    }

    public List<RecipeEntity> getBlog_dinner() {
        return blog_dinner;
    }

    public void setBlog_dinner(List<RecipeEntity> blog_dinner) {
        this.blog_dinner = blog_dinner;
    }

    public List<RecipeEntity> getHealth_breakfast() {
        return health_breakfast;
    }

    public void setHealth_breakfast(List<RecipeEntity> health_breakfast) {
        this.health_breakfast = health_breakfast;
    }

    public List<RecipeEntity> getHealth_lunch() {
        return health_lunch;
    }

    public void setHealth_lunch(List<RecipeEntity> health_lunch) {
        this.health_lunch = health_lunch;
    }

    public List<RecipeEntity> getHealth_dinner() {
        return health_dinner;
    }

    public void setHealth_dinner(List<RecipeEntity> health_dinner) {
        this.health_dinner = health_dinner;
    }

    public double getBlog_cookbook() {
        return blog_cookbook;
    }

    public void setBlog_cookbook(double blog_cookbook) {
        this.blog_cookbook = blog_cookbook;
    }

    public double getBlog_sum() {
        return blog_sum;
    }

    public void setBlog_sum(double blog_sum) {
        this.blog_sum = blog_sum;
    }

    public String getBlog_status() {
        return blog_status;
    }

    public void setBlog_status(String blog_status) {
        this.blog_status = blog_status;
    }

    public double getHealth_cookbook() {
        return health_cookbook;
    }

    public void setHealth_cookbook(double health_cookbook) {
        this.health_cookbook = health_cookbook;
    }

    public String getHealth_status() {
        return health_status;
    }

    public void setHealth_status(String health_status) {
        this.health_status = health_status;
    }

    public double getHealth_sum() {
        return health_sum;
    }

    public void setHealth_sum(double health_sum) {
        this.health_sum = health_sum;
    }

    public double getGu_wu_sum() {
        return gu_wu_sum;
    }

    public void setGu_wu_sum(double gu_wu_sum) {
        this.gu_wu_sum = gu_wu_sum;
    }

    public double getMilk_sum() {
        return milk_sum;
    }

    public void setMilk_sum(double milk_sum) {
        this.milk_sum = milk_sum;
    }

    public double getSuggest_l() {
        return suggest_l;
    }

    public void setSuggest_l(double suggest_l) {
        this.suggest_l = suggest_l;
    }

    public double getSuggest_h() {
        return suggest_h;
    }

    public void setSuggest_h(double suggest_h) {
        this.suggest_h = suggest_h;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public class RecipeEntity implements Serializable{
        private String cookbook_id;
        private String proper_txt;
        private double nutrition_val;
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
        private String taboo_label;
        private String proper_label;
        private String flash_src;
        private String name;
        private String status;

        public String getCookbook_id() {
            return cookbook_id;
        }

        public void setCookbook_id(String cookbook_id) {
            this.cookbook_id = cookbook_id;
        }

        public String getProper_txt() {
            return proper_txt;
        }

        public void setProper_txt(String proper_txt) {
            this.proper_txt = proper_txt;
        }

        public double getNutrition_val() {
            return nutrition_val;
        }

        public void setNutrition_val(double nutrition_val) {
            this.nutrition_val = nutrition_val;
        }

        public String getEffect_txt() {
            return effect_txt;
        }

        public void setEffect_txt(String effect_txt) {
            this.effect_txt = effect_txt;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCollected() {
            return collected;
        }

        public void setCollected(String collected) {
            this.collected = collected;
        }

        public String getTaboo_txt() {
            return taboo_txt;
        }

        public void setTaboo_txt(String taboo_txt) {
            this.taboo_txt = taboo_txt;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getView() {
            return view;
        }

        public void setView(String view) {
            this.view = view;
        }

        public String getPeople_num() {
            return people_num;
        }

        public void setPeople_num(String people_num) {
            this.people_num = people_num;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMaked() {
            return maked;
        }

        public void setMaked(String maked) {
            this.maked = maked;
        }

        public String getHao_shi() {
            return hao_shi;
        }

        public void setHao_shi(String hao_shi) {
            this.hao_shi = hao_shi;
        }

        public String getKitchen() {
            return kitchen;
        }

        public void setKitchen(String kitchen) {
            this.kitchen = kitchen;
        }

        public String getEffect_label() {
            return effect_label;
        }

        public void setEffect_label(String effect_label) {
            this.effect_label = effect_label;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getGong_yi() {
            return gong_yi;
        }

        public void setGong_yi(String gong_yi) {
            this.gong_yi = gong_yi;
        }

        public String getLike() {
            return like;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getSuggest() {
            return suggest;
        }

        public void setSuggest(String suggest) {
            this.suggest = suggest;
        }

        public String getImage_thumb() {
            return image_thumb;
        }

        public void setImage_thumb(String image_thumb) {
            this.image_thumb = image_thumb;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getTaboo_label() {
            return taboo_label;
        }

        public void setTaboo_label(String taboo_label) {
            this.taboo_label = taboo_label;
        }

        public String getProper_label() {
            return proper_label;
        }

        public void setProper_label(String proper_label) {
            this.proper_label = proper_label;
        }

        public String getFlash_src() {
            return flash_src;
        }

        public void setFlash_src(String flash_src) {
            this.flash_src = flash_src;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
