package com.kgv.cookbook.bean;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/14 18:00
 * Email : 18627241899@163.com
 * Description :
 */
public class WeekBean {


    /**
     * date : 2017-02-14
     * nooning : [{"cookbook_id":"482","proper_txt":"","effect_txt":"","description":"蜜汁叉烧，老娘肥叉\u2026\u2026 其实都是叉烧，在广东酒家菜名都是如此 凡是粤菜馆，都少不了一份\u201c金牌\u201d叉烧！每个大厨手艺味道各有千秋..现在每家厨娘都能做出一份正宗的叉烧了.. 在餐馆里，这是一道桌桌必点之菜，太下饭了，肥而不腻，入口就化","collected":"","taboo_txt":"","tips":"烤肉的时间,每家的烤箱温度有差异,肉的大小差异,自己把握一下","uid":"55","update_time":"1484634434","view":"","people_num":"2","category_id":"215,217,18,129,60","id":"482","maked":"","hao_shi":"3","kitchen":"21,28,41","effect_label":"48,69,66","image":"/Uploads/Picture/2016-08-29/800_57c39deb27df1.jpg","create_time":"1472439779","gong_yi":"21","like":"","level":"0","sort":"0","suggest":"","image_thumb":"/Uploads/Picture/2016-08-29/236_57c39deb27df1.jpg","brand_id":"16","taboo_label":"38,132","proper_label":"1,13,89","flash_src":"http://player.youku.com/player.php/sid/XOTU5Nzk5NjI0/v.swf","name":"娘惹蜜汁叉烧","status":"1"}]
     * evening : [{"cookbook_id":"1991","proper_txt":"","effect_txt":"","description":"西兰花不仅是营养丰富的蔬菜，更是一种保健蔬菜。它能够促进人体的新陈代谢，具有清肝、防癌抗癌、提高人体免疫功能等功效。新鲜的西兰花和虾仁、黑木耳等一起搭配，更健康美味。","collected":"","taboo_txt":"","tips":"焯西兰花时锅里滴几滴油，可保持西兰花翠绿","uid":"55","update_time":"1484725036","view":"","people_num":"2","category_id":"212,129,131,41,230,244,62","id":"1991","maked":"","hao_shi":"2","kitchen":"7,41,50","effect_label":"45,48,49,53,80,85,174,190,136","image":"/Uploads/Picture/2017-01-13/800_58784efd09b0a.jpg","create_time":"1484279763","gong_yi":"2","like":"","level":"0","sort":"0","suggest":"","image_thumb":"/Uploads/Picture/2017-01-13/236_58784efd09b0a.jpg","brand_id":"16","taboo_label":"63,84,88,124,94","proper_label":"29,41,46,140,143","flash_src":"","name":"虾仁西兰花","status":"1"}]
     * morning : [{"cookbook_id":"2077","proper_txt":"","effect_txt":"","description":"茄子的营养，也较丰富，含有蛋白质、脂肪、碳水化合物、维 生素以及钙、磷、铁等多种营养成分。","collected":"","taboo_txt":"","tips":"面另外过热水，将酱拌面就OK了。","uid":"56","update_time":"1486524974","view":"","people_num":"2","category_id":"154,129,42,225,233,43,59","id":"2077","maked":"","hao_shi":"2","kitchen":"2,50","effect_label":"48,66,79,97,117,174","image":"/Uploads/Picture/2017-02-08/800_589a89fea14e7.jpeg","create_time":"1486524974","gong_yi":"2","like":"","level":"0","sort":"0","suggest":"","image_thumb":"/Uploads/Picture/2017-02-08/236_589a89fea14e7.jpeg","brand_id":"16","taboo_label":"132,116","proper_label":"13,37","flash_src":"","name":"茄丁打卤面","status":"1"}]
     */
    private String date;
    private int selected;
    private List<RecipeEntity> nooning;
    private List<RecipeEntity> evening;
    private List<RecipeEntity> morning;

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<RecipeEntity> getNooning() {
        return nooning;
    }

    public void setNooning(List<RecipeEntity> nooning) {
        this.nooning = nooning;
    }

    public List<RecipeEntity> getEvening() {
        return evening;
    }

    public void setEvening(List<RecipeEntity> evening) {
        this.evening = evening;
    }

    public List<RecipeEntity> getMorning() {
        return morning;
    }

    public void setMorning(List<RecipeEntity> morning) {
        this.morning = morning;
    }

    public class RecipeEntity{
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
        private String taboo_label;
        private String proper_label;
        private String flash_src;
        private String name;
        private String status;
        private String crow_name;

        public String getCrow_name() {
            return crow_name;
        }

        public void setCrow_name(String crow_name) {
            this.crow_name = crow_name;
        }

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
