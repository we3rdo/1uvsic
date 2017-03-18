package com.kgv.cookbook.bean;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/1/2 15:47
 * Email : 18627241899@163.com
 * Description :
 */
public class SearchResult {


    /**
     * gongyi_sort : [{"name":"蒸","id":"1"},{"name":"炒","id":"2"},{"name":"炖","id":"6"},{"name":"煮","id":"16"},{"name":"腌","id":"19"},{"name":"灼","id":"29"},{"name":"技巧","id":"31"}]
     * people_num : [1,2,3,4]
     * effect_sort : [{"name":"清热","id":"49"},{"name":"养血","id":"48"},{"name":"健脾","id":"47"},{"name":"益气","id":"45"},{"name":"补铁","id":"56"},{"name":"明目","id":"61"},{"name":"健胃","id":"64"},{"name":"补虚","id":"66"},{"name":"滋阴润燥","id":"69"},{"name":"补骨髓","id":"70"},{"name":"健脑","id":"71"},{"name":"解毒","id":"73"},{"name":"消食化积","id":"79"},{"name":"开胃","id":"80"},{"name":"养颜护肤","id":"85"},{"name":"利尿","id":"93"},{"name":"强身健体","id":"108"},{"name":"止痛","id":"112"},{"name":"化痰","id":"114"},{"name":"通乳","id":"116"},{"name":"消炎","id":"125"},{"name":"降血脂","id":"132"},{"name":"防癌","id":"136"},{"name":"降胆固醇","id":"137"},{"name":"健脾胃","id":"155"},{"name":"提高免疫力","id":"174"},{"name":"益肾","id":"177"}]
     * content : [{"proper_txt":"","effect_txt":"","description":"最近在减肥，一直研究有什么又素又好吃的菜肴，这道辣白菜豆腐汤就是我很喜欢的一款^_^","collected":"","taboo_txt":"","tips":"豆腐先用盐水泡半个钟，可以让豆腐更有味。","uid":"56","update_time":"1480907855","view":"","people_num":"1","category_id":"142,38","id":"1793","maked":"","hao_shi":"1","kitchen":"7,51","effect_label":"45,69,49","image":"/Uploads/Picture/2016-12-05/800_5844d8b528ef2.jpg","gong_yi":"16","create_time":"1480907855","like":"","level":"0","suggest":"","image_thumb":"/Uploads/Picture/2016-12-05/236_5844d8b528ef2.jpg","brand_id":"0","taboo_label":"39,43,63,145","proper_label":"13,26,89,108","flash_src":"","name":"辣白菜豆腐汤","status":"1"},{"proper_txt":"","effect_txt":"","description":"秋冬应季的大白菜，做上一道小菜，配粥配饭配面条等等都不错。辣白菜不仅可以当小菜，还可以做韩味十足的石锅拌饭，天气冷的冬天煮上一道辣白菜汤，加点牛肉再好不过了，吃完整个人都暖暖的。","collected":"","taboo_txt":"","tips":"","uid":"55","update_time":"1476436987","view":"","people_num":"4","category_id":"219,23","id":"1413","maked":"","hao_shi":"8","kitchen":"11,41,54","effect_label":"64,69,108,112,125","image":"/Uploads/Picture/2016-10-14/800_5800a263cde3c.jpg","gong_yi":"31","create_time":"1476436987","like":"","level":"0","suggest":"","image_thumb":"/Uploads/Picture/2016-10-14/236_5800a263cde3c.jpg","brand_id":"0","taboo_label":"116,114,99","proper_label":"13","flash_src":"http://player.youku.com/player.php/sid/XNjkzMzMxNDg4/v.swf","name":"韩式辣白菜","status":"1"},{"proper_txt":"","effect_txt":"","description":"最近又开始看韩剧,一出现吃辣白菜互赠的场景就手痒痒，朋友家刚好是延边的朝鲜族，赶紧向朋友妈妈讨教了做辣白菜的方法，阿姨又给了正宗原材料，速速做起来啦～^_^","collected":"","taboo_txt":"","tips":"常见配方是做好后室温1天就冷藏，这样的话，口感不会很酸，我个人喜欢酸的倒牙一口下去酸木脸那种的辣白菜，你也喜欢的话，那末就把做好的在密封盒的辣白菜搁在室温下（20度左右）放3天，会加速发酵，记得每天打开盒子一次，你会发现里面有小气泡产生，打开盒子可以放放气，这样放3天后你就可以闻到酸味儿了，此时再放到冰箱冷藏4、5天就可以了","uid":"55","update_time":"1476435202","view":"","people_num":"3","category_id":"215,219,23","id":"1411","maked":"","hao_shi":"5","kitchen":"11,41,54","effect_label":"49,64,80,108","image":"/Uploads/Picture/2016-10-14/800_58009b98b374c.jpg","gong_yi":"31","create_time":"1476435202","like":"","level":"0","suggest":"","image_thumb":"/Uploads/Picture/2016-10-14/236_58009b98b374c.jpg","brand_id":"0","taboo_label":"96,99,116,120","proper_label":"13","flash_src":"","name":"酸辣劲爽的韩国辣白菜","status":"1"},{"proper_txt":"","effect_txt":"","description":"又到了小白菜上市的季节，小时候每到这个时候总能吃上妈妈做的油泼小白菜，现在自己也会做了，下面就把这道制作简单又美味爽口的小菜与大家分享。","collected":"","taboo_txt":"","tips":"爆香蒜碎的时候最好用小火，不然很容易糊的哦。","uid":"56","update_time":"1476168141","view":"","people_num":"4","category_id":"208,38,39,42","id":"1348","maked":"","hao_shi":"1","kitchen":"7,50","effect_label":"108","image":"/Uploads/Picture/2016-10-11/800_57fc88e5c963f.jpg","gong_yi":"29","create_time":"1476168141","like":"","level":"0","suggest":"","image_thumb":"/Uploads/Picture/2016-10-11/236_57fc88e5c963f.jpg","brand_id":"0","taboo_label":"116,117","proper_label":"13","flash_src":"","name":"油泼小白菜","status":"1"},{"proper_txt":"","effect_txt":"","description":"家里的白菜很多，但是不知什么原因，今年种的白菜味道没有往年的好，所以想换个方法让白菜变得更美味，于是我就看中了这个白菜卷，里面可以包虾仁也可以是肉馅，由于虾仁比较麻烦，就一大早出去买了猪肉，折腾大半天，最终成品味道真是很不错，鲜美多汁，好吃！","collected":"","taboo_txt":"","tips":"这道菜是做给宝宝吃的，菜量可根据自己的情况调节","uid":"55","update_time":"1474961381","view":"","people_num":"2","category_id":"204,215,216","id":"1112","maked":"","hao_shi":"3","kitchen":"41,52","effect_label":"48,61,57,70","image":"/Uploads/Picture/2016-09-27/800_57ea1d85ae9da.jpg","gong_yi":"1","create_time":"1474961381","like":"","level":"0","suggest":"","image_thumb":"/Uploads/Picture/2016-09-27/236_57ea1d85ae9da.jpg","brand_id":"0","taboo_label":"22,68,117,132,141,161","proper_label":"4,13,5,3","flash_src":"","name":"白菜鲜味卷","status":"1"},{"proper_txt":"","effect_txt":"","description":"在寒冬腊月里能吃上这一口很暖呼呼的哦~~","collected":"","taboo_txt":"","tips":"","uid":"56","update_time":"1474528001","view":"","people_num":"3","category_id":"131,141,41,44","id":"979","maked":"","hao_shi":"4","kitchen":"50","effect_label":"57,80,85,116,136,155,48","image":"/Uploads/Picture/2016-09-22/800_57e37fd841722.jpg","gong_yi":"6","create_time":"1474528001","like":"","level":"0","suggest":"","image_thumb":"/Uploads/Picture/2016-09-22/236_57e37fd841722.jpg","brand_id":"0","taboo_label":"39,94,116,117,75","proper_label":"13,65,108,123,122,46,121","flash_src":"","name":"干贝白菜炖粉丝","status":"1"},{"proper_txt":"","effect_txt":"","description":"冬天快到了，随处可见的萝卜白菜，换换吃法！","collected":"","taboo_txt":"","tips":"本人选择的浓缩鸡汁，有条件的情况下最好选择高汤！","uid":"56","update_time":"1473847377","view":"","people_num":"4","category_id":"215,18","id":"853","maked":"","hao_shi":"3","kitchen":"7,58","effect_label":"49,73,79,108,114,174","image":"/Uploads/Picture/2016-09-14/800_57d91e5999340.jpg","gong_yi":"6","create_time":"1473847377","like":"","level":"0","suggest":"","image_thumb":"/Uploads/Picture/2016-09-14/236_57d91e5999340.jpg","brand_id":"0","taboo_label":"56,116,117,124,129","proper_label":"5,13,107,116","flash_src":"","name":"猪油渣炖白菜萝卜","status":"1"},{"proper_txt":"","effect_txt":"","description":"好吃。","collected":"","taboo_txt":"","tips":"1、这是我特别爱吃的一道菜，尤其是夏天，那么热的天，吃这个简单，下饭！\r\n\n2、切五花肉不能过厚，我个人喜欢薄一些，这样容易煎；\r\n\n3、我比较喜欢吃辣白菜，所以买的袋装的辣白菜就是130克，我全部放了进去，炒出来觉得和五花肉的比例还是可以的。","uid":"56","update_time":"1473672184","view":"","people_num":"4","category_id":"218,62","id":"810","maked":"","hao_shi":"3","kitchen":"7,42,50","effect_label":"56,57,66","image":"/Uploads/Picture/2016-09-12/800_57d672d78824f.jpg","gong_yi":"2","create_time":"1473672184","like":"","level":"0","suggest":"","image_thumb":"/Uploads/Picture/2016-09-12/236_57d672d78824f.jpg","brand_id":"0","taboo_label":"22,116,117,21","proper_label":"13","flash_src":"","name":"辣白菜炒五花肉","status":"1"},{"proper_txt":"","effect_txt":"","description":"广式烫水似乎讲究挺多,合适的锅,合适的火候,而有时候家菜快手汤手就零厨艺也能做出美味来。","collected":"","taboo_txt":"","tips":"1.选择放入猪油汤会更香,当然其它油也是可以的\r\n\n2.要先放肉丸油豆腐,最后放白菜,不然白菜会煮烂掉","uid":"56","update_time":"1472633034","view":"","people_num":"3","category_id":"209,18,128,139,142,62","id":"554","maked":"","hao_shi":"1","kitchen":"12,42,50","effect_label":"66,136,108,93,69,137","image":"/Uploads/Picture/2016-08-31/800_57c69773181b1.jpg","gong_yi":"16","create_time":"1472633034","like":"","level":"0","suggest":"","image_thumb":"/Uploads/Picture/2016-08-31/236_57c69773181b1.jpg","brand_id":"0","taboo_label":"20,43,22,21","proper_label":"1,2,5,13,89","flash_src":"","name":"白菜豆腐肉丸汤","status":"1"},{"proper_txt":"","effect_txt":"","description":"天气渐冷，白菜上市，又到了做泡菜的时候，酸辣的泡菜既可以作为下粥的小菜，又可以作为大鱼大肉之后解腻清肠的好帮手，一次可以多做点，放入冰箱冷藏保存，随吃随拿，非常方便。","collected":"","taboo_txt":"","tips":"用韩式辣椒酱做出的泡菜，颜色比较好看，比用辣椒面做出的辣度要低一些，另外没用鱼露，用虾皮带出鲜味。","uid":"56","update_time":"1472459143","view":"","people_num":"4","category_id":"214,23,226","id":"509","maked":"","hao_shi":"8","kitchen":"79","effect_label":"49,73,79,108,114,132,174","image":"/Uploads/Picture/2016-08-29/800_57c3eed9af09f.jpg","gong_yi":"19","create_time":"1472459143","like":"","level":"0","suggest":"","image_thumb":"/Uploads/Picture/2016-08-29/236_57c3eed9af09f.jpg","brand_id":"0","taboo_label":"56,116,117,124,129","proper_label":"5,13,107,116,140","flash_src":"","name":"韩式白菜泡菜","status":"1"}]
     * time_sort : [{"name":"十分钟","id":"1"},{"name":"廿分钟","id":"2"},{"name":"半小时","id":"3"},{"name":"三刻钟","id":"4"},{"name":"一小时","id":"5"},{"name":"数小时","id":"6"},{"name":"数天","id":"8"}]
     */
    private List<NameAndIdEntity> gongyi_sort;
    private List<Integer> people_num;
    private List<NameAndIdEntity> effect_sort;
    private List<ContentEntity> content;
    private List<NameAndIdEntity> time_sort;
    private List<RecommendEntity> recommend;

    public List<RecommendEntity> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<RecommendEntity> recommend) {
        this.recommend = recommend;
    }

    public static class RecommendEntity{

        private String proper_txt;
        private String effect_txt;
        private String description;
        private String collected;
        private String taboo_txt;
        private String tips;
        private String uid;
        private int score;
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

        public void setScore(int score) {
            this.score = score;
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

        public int getScore() {
            return score;
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

    public void setGongyi_sort(List<NameAndIdEntity> gongyi_sort) {
        this.gongyi_sort = gongyi_sort;
    }

    public void setPeople_num(List<Integer> people_num) {
        this.people_num = people_num;
    }

    public void setEffect_sort(List<NameAndIdEntity> effect_sort) {
        this.effect_sort = effect_sort;
    }

    public void setContent(List<ContentEntity> content) {
        this.content = content;
    }

    public void setTime_sort(List<NameAndIdEntity> time_sort) {
        this.time_sort = time_sort;
    }

    public List<NameAndIdEntity> getGongyi_sort() {
        return gongyi_sort;
    }

    public List<Integer> getPeople_num() {
        return people_num;
    }

    public List<NameAndIdEntity> getEffect_sort() {
        return effect_sort;
    }

    public List<ContentEntity> getContent() {
        return content;
    }

    public List<NameAndIdEntity> getTime_sort() {
        return time_sort;
    }

    public static class NameAndIdEntity {
        /**
         * name : 蒸
         * id : 1
         */
        private String name;
        private String id;

        public void setName(String name) {
            this.name = name;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
    }

    public static class ContentEntity {
        /**
         * proper_txt :
         * effect_txt :
         * description : 最近在减肥，一直研究有什么又素又好吃的菜肴，这道辣白菜豆腐汤就是我很喜欢的一款^_^
         * collected :
         * taboo_txt :
         * tips : 豆腐先用盐水泡半个钟，可以让豆腐更有味。
         * uid : 56
         * update_time : 1480907855
         * view :
         * people_num : 1
         * category_id : 142,38
         * id : 1793
         * maked :
         * hao_shi : 1
         * kitchen : 7,51
         * effect_label : 45,69,49
         * image : /Uploads/Picture/2016-12-05/800_5844d8b528ef2.jpg
         * gong_yi : 16
         * create_time : 1480907855
         * like :
         * level : 0
         * suggest :
         * image_thumb : /Uploads/Picture/2016-12-05/236_5844d8b528ef2.jpg
         * brand_id : 0
         * taboo_label : 39,43,63,145
         * proper_label : 13,26,89,108
         * flash_src :
         * name : 辣白菜豆腐汤
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
