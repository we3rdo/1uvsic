package com.kgv.cookbook.bean;

import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/18 9:48
 * Email : 18627241899@163.com
 * Description :
 */
public class NutritionCount {

    private NutritionEntity nutrition;
    private List<IdAndName> cookbook_blog;

    public NutritionEntity getNutrition() {
        return nutrition;
    }

    public void setNutrition(NutritionEntity nutrition) {
        this.nutrition = nutrition;
    }

    public List<IdAndName> getCookbook_blog() {
        return cookbook_blog;
    }

    public void setCookbook_blog(List<IdAndName> cookbook_blog) {
        this.cookbook_blog = cookbook_blog;
    }

    public class NutritionEntity{
        private ValueAndStatus mei;
        private ValueAndStatus tie;
        private ValueAndStatus xi;
        private ValueAndStatus dan_gu;
        private ValueAndStatus dan_bai;
        private ValueAndStatus zhi_fang;
        private ValueAndStatus re_liang;
        private ValueAndStatus xin;
        private ValueAndStatus tan_shui;
        private ValueAndStatus gai;

        public ValueAndStatus getMei() {
            return mei;
        }

        public void setMei(ValueAndStatus mei) {
            this.mei = mei;
        }

        public ValueAndStatus getTie() {
            return tie;
        }

        public void setTie(ValueAndStatus tie) {
            this.tie = tie;
        }

        public ValueAndStatus getXi() {
            return xi;
        }

        public void setXi(ValueAndStatus xi) {
            this.xi = xi;
        }

        public ValueAndStatus getDan_gu() {
            return dan_gu;
        }

        public void setDan_gu(ValueAndStatus dan_gu) {
            this.dan_gu = dan_gu;
        }

        public ValueAndStatus getDan_bai() {
            return dan_bai;
        }

        public void setDan_bai(ValueAndStatus dan_bai) {
            this.dan_bai = dan_bai;
        }

        public ValueAndStatus getZhi_fang() {
            return zhi_fang;
        }

        public void setZhi_fang(ValueAndStatus zhi_fang) {
            this.zhi_fang = zhi_fang;
        }

        public ValueAndStatus getRe_liang() {
            return re_liang;
        }

        public void setRe_liang(ValueAndStatus re_liang) {
            this.re_liang = re_liang;
        }

        public ValueAndStatus getXin() {
            return xin;
        }

        public void setXin(ValueAndStatus xin) {
            this.xin = xin;
        }

        public ValueAndStatus getTan_shui() {
            return tan_shui;
        }

        public void setTan_shui(ValueAndStatus tan_shui) {
            this.tan_shui = tan_shui;
        }

        public ValueAndStatus getGai() {
            return gai;
        }

        public void setGai(ValueAndStatus gai) {
            this.gai = gai;
        }
    }

    public class IdAndName{
        private String cookbook_id;
        private String name;

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

    public class ValueAndStatus{
        private double val;
        private String status;

        public double getVal() {
            return val;
        }

        public void setVal(double val) {
            this.val = val;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
