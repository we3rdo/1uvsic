package com.kgv.cookbook.bean;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/27 14:07
 * Email : 18627241899@163.com
 * Description : 食谱详情
 */
public class ShiPuDetail {

    private String id;
    private String status;
    private String image;
    private String name;
    private String image_thumb;
    private String description;
    private String people_num;
    private String gong_yi;
    private String hao_shi;
    private String kitchen;
    private String effect_label;
    private String effect_txt;
    private String proper_label;
    private String proper_txt;
    private String taboo_label;
    private String taboo_txt;
    private String tips;
    private String create_time;
    private String update_time;
    private String uid;
    private String view;
    private String like;
    private String maked;
    private String collected;
    private String level;
    private String category_id;
    private String flash_src;
    private String suggest;
    private String brand_id;
    private List<StepEntity> step;
    private List<ZhuLiaoAndTiaoliaoEntity> zhuliao;
    private List<ZhuLiaoAndTiaoliaoEntity> tiaoliao;
    private List<KitchenDetailEntity> kitchen_detail;
    private IdAndNameEntity hao_shi_detail;
    private IdAndNameEntity gong_yi_detail;
    private MemberDetailEntity member_detail;
    private List<IdAndNameEntity> effect_label_detail;
    private List<IdAndNameEntity> proper_label_detail;
    private List<IdAndNameEntity> taboo_label_detail;
    private List<NutritionDetailEntity> nutrition_detail;

    public List<NutritionDetailEntity> getNutrition_detail() {
        return nutrition_detail;
    }

    public void setNutrition_detail(List<NutritionDetailEntity> nutrition_detail) {
        this.nutrition_detail = nutrition_detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPeople_num() {
        return people_num;
    }

    public void setPeople_num(String people_num) {
        this.people_num = people_num;
    }

    public String getGong_yi() {
        return gong_yi;
    }

    public void setGong_yi(String gong_yi) {
        this.gong_yi = gong_yi;
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

    public String getEffect_txt() {
        return effect_txt;
    }

    public void setEffect_txt(String effect_txt) {
        this.effect_txt = effect_txt;
    }

    public String getProper_label() {
        return proper_label;
    }

    public void setProper_label(String proper_label) {
        this.proper_label = proper_label;
    }

    public String getProper_txt() {
        return proper_txt;
    }

    public void setProper_txt(String proper_txt) {
        this.proper_txt = proper_txt;
    }

    public String getTaboo_label() {
        return taboo_label;
    }

    public void setTaboo_label(String taboo_label) {
        this.taboo_label = taboo_label;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getMaked() {
        return maked;
    }

    public void setMaked(String maked) {
        this.maked = maked;
    }

    public String getCollected() {
        return collected;
    }

    public void setCollected(String collected) {
        this.collected = collected;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getFlash_src() {
        return flash_src;
    }

    public void setFlash_src(String flash_src) {
        this.flash_src = flash_src;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public List<StepEntity> getStep() {
        return step;
    }

    public void setStep(List<StepEntity> step) {
        this.step = step;
    }

    public List<ZhuLiaoAndTiaoliaoEntity> getZhuliao() {
        return zhuliao;
    }

    public void setZhuliao(List<ZhuLiaoAndTiaoliaoEntity> zhuliao) {
        this.zhuliao = zhuliao;
    }

    public List<ZhuLiaoAndTiaoliaoEntity> getTiaoliao() {
        return tiaoliao;
    }

    public void setTiaoliao(List<ZhuLiaoAndTiaoliaoEntity> tiaoliao) {
        this.tiaoliao = tiaoliao;
    }

    public List<KitchenDetailEntity> getKitchen_detail() {
        return kitchen_detail;
    }

    public void setKitchen_detail(List<KitchenDetailEntity> kitchen_detail) {
        this.kitchen_detail = kitchen_detail;
    }

    public IdAndNameEntity getHao_shi_detail() {
        return hao_shi_detail;
    }

    public void setHao_shi_detail(IdAndNameEntity hao_shi_detail) {
        this.hao_shi_detail = hao_shi_detail;
    }

    public IdAndNameEntity getGong_yi_detail() {
        return gong_yi_detail;
    }

    public void setGong_yi_detail(IdAndNameEntity gong_yi_detail) {
        this.gong_yi_detail = gong_yi_detail;
    }

    public MemberDetailEntity getMember_detail() {
        return member_detail;
    }

    public void setMember_detail(MemberDetailEntity member_detail) {
        this.member_detail = member_detail;
    }

    public List<IdAndNameEntity> getEffect_label_detail() {
        return effect_label_detail;
    }

    public void setEffect_label_detail(List<IdAndNameEntity> effect_label_detail) {
        this.effect_label_detail = effect_label_detail;
    }

    public List<IdAndNameEntity> getProper_label_detail() {
        return proper_label_detail;
    }

    public void setProper_label_detail(List<IdAndNameEntity> proper_label_detail) {
        this.proper_label_detail = proper_label_detail;
    }

    public List<IdAndNameEntity> getTaboo_label_detail() {
        return taboo_label_detail;
    }

    public void setTaboo_label_detail(List<IdAndNameEntity> taboo_label_detail) {
        this.taboo_label_detail = taboo_label_detail;
    }


    public static class StepEntity {
        private String id;
        private String cookbook_id;
        private String step_id;
        private String image_thumb;
        private String image;
        private String note;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCookbook_id() {
            return cookbook_id;
        }

        public void setCookbook_id(String cookbook_id) {
            this.cookbook_id = cookbook_id;
        }

        public String getStep_id() {
            return step_id;
        }

        public void setStep_id(String step_id) {
            this.step_id = step_id;
        }

        public String getImage_thumb() {
            return image_thumb;
        }

        public void setImage_thumb(String image_thumb) {
            this.image_thumb = image_thumb;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }
    }

    public static class ZhuLiaoAndTiaoliaoEntity {
        private String id;
        private String cookbook_id;
        private String shicai_id;
        private String shicai_name;
        private String yongliang;
        private String sort_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCookbook_id() {
            return cookbook_id;
        }

        public void setCookbook_id(String cookbook_id) {
            this.cookbook_id = cookbook_id;
        }

        public String getShicai_id() {
            return shicai_id;
        }

        public void setShicai_id(String shicai_id) {
            this.shicai_id = shicai_id;
        }

        public String getShicai_name() {
            return shicai_name;
        }

        public void setShicai_name(String shicai_name) {
            this.shicai_name = shicai_name;
        }

        public String getYongliang() {
            return yongliang;
        }

        public void setYongliang(String yongliang) {
            this.yongliang = yongliang;
        }

        public String getSort_id() {
            return sort_id;
        }

        public void setSort_id(String sort_id) {
            this.sort_id = sort_id;
        }
    }

    public static class TiaoLiaoEntity {

        private String id;
        private String cookbook_id;
        private String shicai_id;
        private String shicai_name;
        private String yongliang;
        private String sort_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCookbook_id() {
            return cookbook_id;
        }

        public void setCookbook_id(String cookbook_id) {
            this.cookbook_id = cookbook_id;
        }

        public String getShicai_id() {
            return shicai_id;
        }

        public void setShicai_id(String shicai_id) {
            this.shicai_id = shicai_id;
        }

        public String getShicai_name() {
            return shicai_name;
        }

        public void setShicai_name(String shicai_name) {
            this.shicai_name = shicai_name;
        }

        public String getYongliang() {
            return yongliang;
        }

        public void setYongliang(String yongliang) {
            this.yongliang = yongliang;
        }

        public String getSort_id() {
            return sort_id;
        }

        public void setSort_id(String sort_id) {
            this.sort_id = sort_id;
        }
    }

    public static class KitchenDetailEntity {

        private String id;
        private String name;
        private String image;
        private String image_thumb;
        private String cid;
        private String status;
        private String create_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }

    public static class IdAndNameEntity {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class MemberDetailEntity {
        private String uid;
        private String figureurl;
        private String nickname;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getFigureurl() {
            return figureurl;
        }

        public void setFigureurl(String figureurl) {
            this.figureurl = figureurl;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }

    public static class NutritionDetailEntity {
        private String name;
        private String val;
        private String unit;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVal() {
            return val;
        }

        public void setVal(String val) {
            this.val = val;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
