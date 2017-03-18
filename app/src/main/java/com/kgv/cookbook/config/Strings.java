package com.kgv.cookbook.config;

import com.kgv.cookbook.bean.NameAndId;

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
        age.add(new NameAndId("8","孕妇  "));
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
}
