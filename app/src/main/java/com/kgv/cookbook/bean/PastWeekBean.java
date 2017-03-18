package com.kgv.cookbook.bean;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/23 14:12
 * Email : 18627241899@163.com
 * Description :
 */
public class PastWeekBean {

    private String date;
    private NutritionEntity Nutrition;
    private List<NameIdEntity> cookbook_blog;
    private String Er;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public NutritionEntity getNutrition() {
        return Nutrition;
    }

    public void setNutrition(NutritionEntity nutrition) {
        Nutrition = nutrition;
    }

    public List<NameIdEntity> getCookbook_blog() {
        return cookbook_blog;
    }

    public void setCookbook_blog(List<NameIdEntity> cookbook_blog) {
        this.cookbook_blog = cookbook_blog;
    }

    public String getEr() {
        return Er;
    }

    public void setEr(String er) {
        Er = er;
    }

    public static class ValAndStatusEntity {
        private String status;
        private double val;

        public ValAndStatusEntity() {
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public double getVal() {
            return val;
        }

        public void setVal(double val) {
            this.val = val;
        }
    }

    public static class NameIdEntity{
        private String cookbook_id;
        private String name;

        public NameIdEntity(String cookbook_id, String name) {
            this.cookbook_id = cookbook_id;
            this.name = name;
        }

        public String getCookbook_id() {
            return cookbook_id;
        }

        public void setCookbook_id(String cookbook_id) {
            this.cookbook_id = cookbook_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class NutritionEntity{
        private ValAndStatusEntity re_liang;
        private ValAndStatusEntity tan_shui;
        private ValAndStatusEntity zhi_fang;
        private ValAndStatusEntity dan_bai;
        private ValAndStatusEntity dan_gu;
        private ValAndStatusEntity mei;
        private ValAndStatusEntity gai;
        private ValAndStatusEntity tie;
        private ValAndStatusEntity xin;
        private ValAndStatusEntity xi;

        public ValAndStatusEntity getRe_liang() {
            return re_liang;
        }

        public void setRe_liang(ValAndStatusEntity re_liang) {
            this.re_liang = re_liang;
        }

        public ValAndStatusEntity getTan_shui() {
            return tan_shui;
        }

        public void setTan_shui(ValAndStatusEntity tan_shui) {
            this.tan_shui = tan_shui;
        }

        public ValAndStatusEntity getZhi_fang() {
            return zhi_fang;
        }

        public void setZhi_fang(ValAndStatusEntity zhi_fang) {
            this.zhi_fang = zhi_fang;
        }

        public ValAndStatusEntity getDan_bai() {
            return dan_bai;
        }

        public void setDan_bai(ValAndStatusEntity dan_bai) {
            this.dan_bai = dan_bai;
        }

        public ValAndStatusEntity getDan_gu() {
            return dan_gu;
        }

        public void setDan_gu(ValAndStatusEntity dan_gu) {
            this.dan_gu = dan_gu;
        }

        public ValAndStatusEntity getMei() {
            return mei;
        }

        public void setMei(ValAndStatusEntity mei) {
            this.mei = mei;
        }

        public ValAndStatusEntity getGai() {
            return gai;
        }

        public void setGai(ValAndStatusEntity gai) {
            this.gai = gai;
        }

        public ValAndStatusEntity getTie() {
            return tie;
        }

        public void setTie(ValAndStatusEntity tie) {
            this.tie = tie;
        }

        public ValAndStatusEntity getXin() {
            return xin;
        }

        public void setXin(ValAndStatusEntity xin) {
            this.xin = xin;
        }

        public ValAndStatusEntity getXi() {
            return xi;
        }

        public void setXi(ValAndStatusEntity xi) {
            this.xi = xi;
        }
    }

}
