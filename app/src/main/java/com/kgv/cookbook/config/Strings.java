package com.kgv.cookbook.config;

import com.kgv.cookbook.bean.NameAndId;
import com.kgv.cookbook.bean.RecipeCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈可轩 on 2017/2/11 10:09
 * Email : 18627241899@163.com
 * Description :
 */
public class Strings {

    public static List<NameAndId> getMaterials(){
        List<NameAndId> materials = new ArrayList<>();
        materials.add(new NameAndId("5","肉禽类"));
        materials.add(new NameAndId("1","蔬菜"));
        materials.add(new NameAndId("45","米面豆乳类"));
        materials.add(new NameAndId("31","水产品类"));
        materials.add(new NameAndId("50","花果类"));
        materials.add(new NameAndId("13","调味品"));
        return materials;
    }

    public static List<NameAndId> getRecipes(){
        List<NameAndId> recipes = new ArrayList<>();
        recipes.add(new NameAndId("3","外国菜"));
        recipes.add(new NameAndId("2","中华菜系"));
        recipes.add(new NameAndId("6","功能调理"));
        recipes.add(new NameAndId("5","适宜人群"));
        recipes.add(new NameAndId("8","烘焙"));
        recipes.add(new NameAndId("10","场景"));
        recipes.add(new NameAndId("1","家常菜"));
        recipes.add(new NameAndId("4","各地小吃"));
        recipes.add(new NameAndId("7","疾病调理"));
        recipes.add(new NameAndId("250","家电"));
        recipes.add(new NameAndId("9","节日"));
        return recipes;
    }

    public static List<NameAndId> getAge(){
        ArrayList<NameAndId> age = new ArrayList<>();
        age.add(new NameAndId("1","婴儿   3月-1岁"));
        age.add(new NameAndId("2","幼儿   1岁-6岁"));
        age.add(new NameAndId("3","儿童   7岁-14岁"));
        age.add(new NameAndId("4","青少年 15岁-18岁"));
        age.add(new NameAndId("5","成年人 19岁-40岁"));
        age.add(new NameAndId("6","中年人 41岁-60岁"));
        age.add(new NameAndId("7","老年人 60岁以上"));
        age.add(new NameAndId("9","备孕  "));
        age.add(new NameAndId("8","孕妇  "));
        age.add(new NameAndId("10","产妇  "));
        return age;
    }

    public static List<NameAndId> getPeople(){
        ArrayList<NameAndId> people = new ArrayList<>();
        people.add(new NameAndId("1","1 人"));
        people.add(new NameAndId("2","2 人"));
        people.add(new NameAndId("3","3 人"));
        people.add(new NameAndId("4","4 人"));
        people.add(new NameAndId("5","5 人"));
        people.add(new NameAndId("6","6 人"));
        return people;
    }

    public static List<NameAndId> getSetMealTitle(){
        ArrayList<NameAndId> peoples = new ArrayList<>();
        peoples.add(new NameAndId("1","单人餐"));
        peoples.add(new NameAndId("2","双人餐"));
        peoples.add(new NameAndId("3","3 - 4人"));
        peoples.add(new NameAndId("4","5 - 6人"));
        peoples.add(new NameAndId("5","7 - 8人"));
        peoples.add(new NameAndId("6","9 - 10人"));
        peoples.add(new NameAndId("7","10人以上"));
        return peoples;
    }

    public static String getStringUnit(String unit){
        if ("re_liang".equals(unit)){
            unit = "热量（大卡）";
        }else if("tan_shui".equals(unit)){
            unit = "碳水化合物（克）";
        }else if("zhi_fang".equals(unit)){
            unit = "脂肪（克）";
        }else if("dan_bai".equals(unit)){
            unit = "蛋白质（克）";
        }else if("dan_gu".equals(unit)){
            unit = "胆固醇（毫克）";
        }else if("mei".equals(unit)){
            unit = "镁（毫克）";
        }else if("gai".equals(unit)){
            unit = "钙（毫克）";
        }else if("tie".equals(unit)){
            unit = "铁（毫克）";
        }else if("xin".equals(unit)){
            unit = "锌（毫克）";
        }else if("xi".equals(unit)){
            unit = "硒（毫克）";
        }else {
            unit = "";
        }
        return unit;
    }


    public static ArrayList<RecipeCategory.ChildrenEntity> getHealthByAge(int ageID){
        ArrayList<RecipeCategory.ChildrenEntity> list = new ArrayList<>();
        switch (ageID){
            case 1://婴儿
                list.add(new RecipeCategory.ChildrenEntity("116","发烧"));
                list.add(new RecipeCategory.ChildrenEntity("118","感冒"));
                list.add(new RecipeCategory.ChildrenEntity("122","便秘"));
                break;
            case 2://幼儿
                list.add(new RecipeCategory.ChildrenEntity("116","发烧"));
                list.add(new RecipeCategory.ChildrenEntity("118","感冒"));
                list.add(new RecipeCategory.ChildrenEntity("122","便秘"));
                list.add(new RecipeCategory.ChildrenEntity("39","清热去火"));
                list.add(new RecipeCategory.ChildrenEntity("230","补钙"));
                list.add(new RecipeCategory.ChildrenEntity("233","提高免疫力"));
                list.add(new RecipeCategory.ChildrenEntity("226","健脾开胃"));
                break;
            case 3://儿童
                list.add(new RecipeCategory.ChildrenEntity("116","发烧"));
                list.add(new RecipeCategory.ChildrenEntity("118","感冒"));
                list.add(new RecipeCategory.ChildrenEntity("122","便秘"));
                list.add(new RecipeCategory.ChildrenEntity("39","清热去火"));
                list.add(new RecipeCategory.ChildrenEntity("230","补钙"));
                list.add(new RecipeCategory.ChildrenEntity("233","提高免疫力"));
                list.add(new RecipeCategory.ChildrenEntity("226","健脾开胃"));
                list.add(new RecipeCategory.ChildrenEntity("43","贫血"));
                break;
            case 4://青少年
                list.add(new RecipeCategory.ChildrenEntity("95","痛经"));
                list.add(new RecipeCategory.ChildrenEntity("103","胃病"));
                list.add(new RecipeCategory.ChildrenEntity("105","月经不调"));
                list.add(new RecipeCategory.ChildrenEntity("116","发烧"));
                list.add(new RecipeCategory.ChildrenEntity("118","感冒"));
                list.add(new RecipeCategory.ChildrenEntity("122","便秘"));
                list.add(new RecipeCategory.ChildrenEntity("39","清热去火"));
                list.add(new RecipeCategory.ChildrenEntity("42","排毒养颜"));
                list.add(new RecipeCategory.ChildrenEntity("226","健脾开胃"));
                list.add(new RecipeCategory.ChildrenEntity("230","补钙"));
                list.add(new RecipeCategory.ChildrenEntity("233","提高免疫力"));
                list.add(new RecipeCategory.ChildrenEntity("242","调经"));
                list.add(new RecipeCategory.ChildrenEntity("43","贫血"));
                list.add(new RecipeCategory.ChildrenEntity("38","减肥瘦身"));
                break;
            case 5://成年人
                list.add(new RecipeCategory.ChildrenEntity("45","糖尿病"));
                list.add(new RecipeCategory.ChildrenEntity("46","高血压"));
                list.add(new RecipeCategory.ChildrenEntity("47","冠心病"));
                list.add(new RecipeCategory.ChildrenEntity("92","高血脂"));
                list.add(new RecipeCategory.ChildrenEntity("93","中风"));
                list.add(new RecipeCategory.ChildrenEntity("95","痛经"));
                list.add(new RecipeCategory.ChildrenEntity("103","胃病"));
                list.add(new RecipeCategory.ChildrenEntity("105","月经不调"));
                list.add(new RecipeCategory.ChildrenEntity("107","咽喉炎"));
                list.add(new RecipeCategory.ChildrenEntity("116","发烧"));
                list.add(new RecipeCategory.ChildrenEntity("118","感冒"));
                list.add(new RecipeCategory.ChildrenEntity("122","便秘"));
                list.add(new RecipeCategory.ChildrenEntity("39","清热去火"));
                list.add(new RecipeCategory.ChildrenEntity("42","排毒养颜"));
                list.add(new RecipeCategory.ChildrenEntity("226","健脾开胃"));
                list.add(new RecipeCategory.ChildrenEntity("230","补钙"));
                list.add(new RecipeCategory.ChildrenEntity("233","提高免疫力"));
                list.add(new RecipeCategory.ChildrenEntity("242","调经"));
                list.add(new RecipeCategory.ChildrenEntity("43","贫血"));
                list.add(new RecipeCategory.ChildrenEntity("38","减肥瘦身"));
                break;
            case 6://中年人
                list.add(new RecipeCategory.ChildrenEntity("45","糖尿病"));
                list.add(new RecipeCategory.ChildrenEntity("46","高血压"));
                list.add(new RecipeCategory.ChildrenEntity("92","高血脂"));
                list.add(new RecipeCategory.ChildrenEntity("47","冠心病"));
                list.add(new RecipeCategory.ChildrenEntity("93","中风"));
                list.add(new RecipeCategory.ChildrenEntity("103","胃病"));
                list.add(new RecipeCategory.ChildrenEntity("105","月经不调"));
                list.add(new RecipeCategory.ChildrenEntity("107","咽喉炎"));
                list.add(new RecipeCategory.ChildrenEntity("116","发烧"));
                list.add(new RecipeCategory.ChildrenEntity("118","感冒"));
                list.add(new RecipeCategory.ChildrenEntity("122","便秘"));
                list.add(new RecipeCategory.ChildrenEntity("39","清热去火"));
                list.add(new RecipeCategory.ChildrenEntity("42","排毒养颜"));
                list.add(new RecipeCategory.ChildrenEntity("226","健脾开胃"));
                list.add(new RecipeCategory.ChildrenEntity("230","补钙"));
                list.add(new RecipeCategory.ChildrenEntity("233","提高免疫力"));
                list.add(new RecipeCategory.ChildrenEntity("242","调经"));
                list.add(new RecipeCategory.ChildrenEntity("38","减肥瘦身"));
                break;
            case 7://老年人
                list.add(new RecipeCategory.ChildrenEntity("45","糖尿病"));
                list.add(new RecipeCategory.ChildrenEntity("46","高血压"));
                list.add(new RecipeCategory.ChildrenEntity("47","冠心病"));
                list.add(new RecipeCategory.ChildrenEntity("92","高血脂"));
                list.add(new RecipeCategory.ChildrenEntity("93","中风"));
                list.add(new RecipeCategory.ChildrenEntity("103","胃病"));
                list.add(new RecipeCategory.ChildrenEntity("107","咽喉炎"));
                list.add(new RecipeCategory.ChildrenEntity("116","发烧"));
                list.add(new RecipeCategory.ChildrenEntity("118","感冒"));
                list.add(new RecipeCategory.ChildrenEntity("122","便秘"));
                list.add(new RecipeCategory.ChildrenEntity("39","清热去火"));
                list.add(new RecipeCategory.ChildrenEntity("226","健脾开胃"));
                list.add(new RecipeCategory.ChildrenEntity("230","补钙"));
                list.add(new RecipeCategory.ChildrenEntity("233","提高免疫力"));
                break;
            case 8://备孕
                list.add(new RecipeCategory.ChildrenEntity("95","痛经"));
                list.add(new RecipeCategory.ChildrenEntity("103","胃病"));
                list.add(new RecipeCategory.ChildrenEntity("105","月经不调"));
                list.add(new RecipeCategory.ChildrenEntity("107","咽喉炎"));
                list.add(new RecipeCategory.ChildrenEntity("116","发烧"));
                list.add(new RecipeCategory.ChildrenEntity("118","感冒"));
                list.add(new RecipeCategory.ChildrenEntity("122","便秘"));
                list.add(new RecipeCategory.ChildrenEntity("38","减肥瘦身"));
                list.add(new RecipeCategory.ChildrenEntity("39","清热去火"));
                list.add(new RecipeCategory.ChildrenEntity("42","排毒养颜"));
                list.add(new RecipeCategory.ChildrenEntity("226","健脾开胃"));
                list.add(new RecipeCategory.ChildrenEntity("230","补钙"));
                list.add(new RecipeCategory.ChildrenEntity("233","提高免疫力"));
                list.add(new RecipeCategory.ChildrenEntity("242","调经"));
                break;
            case 9://孕妇
                list.add(new RecipeCategory.ChildrenEntity("92","高血脂"));
                list.add(new RecipeCategory.ChildrenEntity("107","咽喉炎"));
                list.add(new RecipeCategory.ChildrenEntity("116","发烧"));
                list.add(new RecipeCategory.ChildrenEntity("118","感冒"));
                list.add(new RecipeCategory.ChildrenEntity("122","便秘"));
                list.add(new RecipeCategory.ChildrenEntity("39","清热去火"));
                list.add(new RecipeCategory.ChildrenEntity("42","排毒养颜"));
                list.add(new RecipeCategory.ChildrenEntity("226","健脾开胃"));
                list.add(new RecipeCategory.ChildrenEntity("230","补钙"));
                list.add(new RecipeCategory.ChildrenEntity("233","提高免疫力"));
                break;
            case 10://产妇
                list.add(new RecipeCategory.ChildrenEntity("107","咽喉炎"));
                list.add(new RecipeCategory.ChildrenEntity("116","发烧"));
                list.add(new RecipeCategory.ChildrenEntity("118","感冒"));
                list.add(new RecipeCategory.ChildrenEntity("122","便秘"));
                list.add(new RecipeCategory.ChildrenEntity("39","清热去火"));
                list.add(new RecipeCategory.ChildrenEntity("42","排毒养颜"));
                list.add(new RecipeCategory.ChildrenEntity("226","健脾开胃"));
                list.add(new RecipeCategory.ChildrenEntity("230","补钙"));
                list.add(new RecipeCategory.ChildrenEntity("233","提高免疫力"));
                list.add(new RecipeCategory.ChildrenEntity("38","减肥瘦身"));
                break;
        }
        return list;
    }
}
