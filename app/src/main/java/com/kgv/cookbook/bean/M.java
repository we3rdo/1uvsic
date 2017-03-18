package com.kgv.cookbook.bean;

import java.util.List;

/**
 * Created by 陈可轩 on 2016/12/21 16:02
 * Email : 18627241899@163.com
 * Description : 食材列表
 */
public class M {

    private List<ChildrenEntity> children;
    private String pid;
    private String id;
    private String title;

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

    public static class ChildrenEntity {

        private List<SubChildrenEntity> children;
        private String pid;
        private String id;
        private String title;

        public void setChildren(List<SubChildrenEntity> children) {
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

        public List<SubChildrenEntity> getChildren() {
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

        public static class SubChildrenEntity {
            /**
             * image : /Uploads/Picture/2016-07-18/200_578ca69372dec.jpg
             * pid : 14
             * id : 476
             * title : 花生油
             * image_thumb : /Uploads/Picture/2016-07-18/100_578ca69372dec.jpg
             */
            private String image;
            private String pid;
            private String id;
            private String title;
            private String image_thumb;

            public SubChildrenEntity(String title) {
                this.title = title;
            }

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
}
