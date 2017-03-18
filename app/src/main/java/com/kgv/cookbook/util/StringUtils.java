package com.kgv.cookbook.util;

import java.util.regex.Pattern;

/**
 * Created by 陈可轩 on 2016/12/8 14:37
 * Email : 18627241899@163.com
 * Description : 字符串工具
 */
public class StringUtils {

    /**
     *  判断字符串是否为手机号
     */
    public static boolean isPhoneNumber(String phone) {
        Pattern pattern = Pattern.compile("^(13[0-9]|15[0-9]|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$");
        return pattern.matcher(phone).matches();
    }

    /**
     *  判断字符串是否为数字
     */
    public static boolean isNumber(String number){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(number).matches();
    }

}
