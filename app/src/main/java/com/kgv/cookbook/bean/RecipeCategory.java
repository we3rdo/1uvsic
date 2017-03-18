package com.kgv.cookbook.bean;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/13 10:53
 * Email : 18627241899@163.com
 * Description : 食谱分类
 */
public class RecipeCategory {


    /**
     * image :
     * children : [{"image":"","pid":"6","id":"38","title":"减肥瘦身","image_thumb":""},{"image":"","pid":"6","id":"39","title":"清热祛火","image_thumb":""},{"image":"","pid":"6","id":"40","title":"滋阴","image_thumb":""},{"image":"","pid":"6","id":"41","title":"壮阳","image_thumb":""},{"image":"","pid":"6","id":"42","title":"排毒养颜","image_thumb":""},{"image":"","pid":"6","id":"225","title":"润肤","image_thumb":""},{"image":"","pid":"6","id":"226","title":"健脾开胃","image_thumb":""},{"image":"","pid":"6","id":"227","title":"清肝明目","image_thumb":""},{"image":"","pid":"6","id":"228","title":"止咳祛痰","image_thumb":""},{"image":"","pid":"6","id":"229","title":"通乳","image_thumb":""},{"image":"","pid":"6","id":"230","title":"补钙","image_thumb":""},{"image":"","pid":"6","id":"231","title":"补铁","image_thumb":""},{"image":"","pid":"6","id":"232","title":"醒酒","image_thumb":""},{"image":"","pid":"6","id":"233","title":"提高免疫力","image_thumb":""},{"image":"","pid":"6","id":"234","title":"祛寒","image_thumb":""},{"image":"","pid":"6","id":"235","title":"秋冬进补","image_thumb":""},{"image":"","pid":"6","id":"236","title":"消暑解渴","image_thumb":""},{"image":"","pid":"6","id":"237","title":"益智健脑","image_thumb":""},{"image":"","pid":"6","id":"238","title":"养发","image_thumb":""},{"image":"","pid":"6","id":"240","title":"消食化积","image_thumb":""},{"image":"","pid":"6","id":"241","title":"丰胸","image_thumb":""},{"image":"","pid":"6","id":"242","title":"调经","image_thumb":""},{"image":"","pid":"6","id":"243","title":"安胎","image_thumb":""},{"image":"","pid":"6","id":"244","title":"补肾","image_thumb":""},{"image":"","pid":"6","id":"245","title":"安神","image_thumb":""}]
     * pid : 0
     * id : 6
     * title : 功能调理
     * image_thumb :
     */
    private String image;
    private List<ChildrenEntity> children;
    private String pid;
    private String id;
    private String title;
    private String image_thumb;

    public void setImage(String image) {
        this.image = image;
    }

    public void setChildren(List<ChildrenEntity> children) {
        this.children = children;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }

    public String getImage() {
        return image;
    }

    public List<ChildrenEntity> getChildren() {
        return children;
    }

    public String getPid() {
        return pid;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_thumb() {
        return image_thumb;
    }

    public static class ChildrenEntity {
        /**
         * image :
         * pid : 6
         * id : 38
         * title : 减肥瘦身
         * image_thumb :
         */
        private String image;
        private String pid;
        private String id;
        private String title;
        private String image_thumb;

        public void setImage(String image) {
            this.image = image;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImage_thumb(String image_thumb) {
            this.image_thumb = image_thumb;
        }

        public String getImage() {
            return image;
        }

        public String getPid() {
            return pid;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getImage_thumb() {
            return image_thumb;
        }
    }
}
