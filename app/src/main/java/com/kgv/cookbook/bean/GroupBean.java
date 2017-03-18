package com.kgv.cookbook.bean;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/25 9:57
 * Email : 18627241899@163.com
 * Description :
 */
public class GroupBean {

    /**
     * taboo : []
     * content : [{"proper_txt":"吞拿鱼三明治适宜","effect_txt":"吞拿鱼三明治功效","description":"描述","collected":"","taboo_txt":"吞拿鱼三明治禁忌","tips":"小贴士","uid":"1","update_time":"1470983301","view":"","people_num":"4","category_id":"18,30,47,61","id":"24","maked":"","hao_shi":"1","kitchen":"2,7","effect_label":"6,26,27,41","image":"/Uploads/Picture/2016-07-04/800_577a0721b637e.jpg","gong_yi":"6","create_time":"1466733104","like":"","level":"0","suggest":"吞拿鱼三明治建议","image_thumb":"/Uploads/Picture/2016-07-04/236_577a0721b637e.jpg","brand_id":"0","taboo_label":"6,3","proper_label":"2,5","flash_src":"http://player.youku.com/player.php/sid/XMTU1OTc2NDA2MA==/v.swf","name":"吞拿鱼三明治","status":"1"},{"proper_txt":"烤蓝莓重芝士蛋糕适宜","effect_txt":"烤蓝莓重芝士蛋糕功效","description":"描述22","collected":"","taboo_txt":"烤蓝莓重芝士蛋糕禁忌","tips":"小贴士","uid":"1","update_time":"1467614937","view":"","people_num":"3","category_id":"25,36,61","id":"30","maked":"","hao_shi":"3","kitchen":"2,7","effect_label":"1,6,26","image":"/Uploads/Picture/2016-07-04/800_577a06d5518ac.jpg","gong_yi":"2","create_time":"1467097320","like":"","level":"0","suggest":"烤蓝莓重芝士蛋糕建议","image_thumb":"/Uploads/Picture/2016-07-04/236_577a06d5518ac.jpg","brand_id":"0","taboo_label":"5,2","proper_label":"1,3,6","flash_src":"","name":"烤蓝莓重芝士蛋糕","status":"1"},{"proper_txt":"基础原味蛋糕卷适宜","effect_txt":"基础原味蛋糕卷功效\n","description":"描述\n","collected":"","taboo_txt":"基础原味蛋糕卷禁忌","tips":"","uid":"1","update_time":"1467615061","view":"","people_num":"3","category_id":"16,39,61","id":"53","maked":"","hao_shi":"2","kitchen":"7","effect_label":"","image":"/Uploads/Picture/2016-07-04/800_577a075131949.jpg","gong_yi":"2","create_time":"1467365042","like":"","level":"0","suggest":"基础原味蛋糕卷建议","image_thumb":"/Uploads/Picture/2016-07-04/236_577a075131949.jpg","brand_id":"0","taboo_label":"","proper_label":"","flash_src":"","name":"基础原味蛋糕卷","status":"1"},{"proper_txt":"脆皮蛋筒和脆皮碗适宜","effect_txt":"脆皮蛋筒和脆皮碗功效","description":"脆皮蛋筒和脆皮碗","collected":"","taboo_txt":"脆皮蛋筒和脆皮碗禁忌","tips":"脆皮蛋筒和脆皮碗","uid":"1","update_time":"1467619818","view":"","people_num":"2","category_id":"61","id":"56","maked":"","hao_shi":"2","kitchen":"7","effect_label":"6","image":"/Uploads/Picture/2016-07-04/800_577a19bbb7879.jpg","gong_yi":"6","create_time":"1467619818","like":"","level":"0","suggest":"脆皮蛋筒和脆皮碗建议","image_thumb":"/Uploads/Picture/2016-07-04/236_577a19bbb7879.jpg","brand_id":"0","taboo_label":"9","proper_label":"3","flash_src":"","name":"脆皮蛋筒和脆皮碗","status":"1"}]
     */
    private List<TabooEntity> taboo;
    private List<ContentEntity> content;

    public void setTaboo(List<TabooEntity> taboo) {
        this.taboo = taboo;
    }

    public void setContent(List<ContentEntity> content) {
        this.content = content;
    }

    public List<TabooEntity> getTaboo() {
        return taboo;
    }

    public List<ContentEntity> getContent() {
        return content;
    }

    public static class TabooEntity{

        /**
         * taboo : [{"effect":"中毒","name":"大蒜","id":"524"}]
         * name : 鸭肉
         * id : 141
         */
        private List<SubTabooEntity> taboo;
        private String name;
        private String id;

        public void setTaboo(List<SubTabooEntity> taboo) {
            this.taboo = taboo;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<SubTabooEntity> getTaboo() {
            return taboo;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public static class SubTabooEntity {
            /**
             * effect : 中毒
             * name : 大蒜
             * id : 524
             */
            private String effect;
            private String name;
            private String id;

            public void setEffect(String effect) {
                this.effect = effect;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getEffect() {
                return effect;
            }

            public String getName() {
                return name;
            }

            public String getId() {
                return id;
            }
        }
    }

    public static class ContentEntity {
        /**
         * proper_txt : 吞拿鱼三明治适宜
         * effect_txt : 吞拿鱼三明治功效
         * description : 描述
         * collected :
         * taboo_txt : 吞拿鱼三明治禁忌
         * tips : 小贴士
         * uid : 1
         * update_time : 1470983301
         * view :
         * people_num : 4
         * category_id : 18,30,47,61
         * id : 24
         * maked :
         * hao_shi : 1
         * kitchen : 2,7
         * effect_label : 6,26,27,41
         * image : /Uploads/Picture/2016-07-04/800_577a0721b637e.jpg
         * gong_yi : 6
         * create_time : 1466733104
         * like :
         * level : 0
         * suggest : 吞拿鱼三明治建议
         * image_thumb : /Uploads/Picture/2016-07-04/236_577a0721b637e.jpg
         * brand_id : 0
         * taboo_label : 6,3
         * proper_label : 2,5
         * flash_src : http://player.youku.com/player.php/sid/XMTU1OTc2NDA2MA==/v.swf
         * name : 吞拿鱼三明治
         * status : 1
         */
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
        private String gong_yi;
        private String create_time;
        private String like;
        private String level;
        private String suggest;
        private String image_thumb;
        private String brand_id;
        private String taboo_label;
        private String proper_label;
        private String flash_src;
        private String name;
        private String status;

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

        public void setGong_yi(String gong_yi) {
            this.gong_yi = gong_yi;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public void setLike(String like) {
            this.like = like;
        }

        public void setLevel(String level) {
            this.level = level;
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

        public String getGong_yi() {
            return gong_yi;
        }

        public String getCreate_time() {
            return create_time;
        }

        public String getLike() {
            return like;
        }

        public String getLevel() {
            return level;
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
    }
}



