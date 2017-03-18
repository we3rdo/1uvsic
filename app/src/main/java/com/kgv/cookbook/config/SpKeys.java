package com.kgv.cookbook.config;

/**
 * Created by 陈可轩 on 2016/12/8 17:42
 * Email : 18627241899@163.com
 * Description : SharedPreferences Keys
 */
public class SpKeys {

    public static final String LOGIN_USERNAME = "login_username";
    public static final String LOGIN_PASSWORD = "login_password";

    public static final String SP_FILE_NAME = "user_data";

    //下载图片结果标记
    public static final String DOWNLOAD_IMG_SUCCESS = "download_img";

    //缓存超时时间
    public static final long OUT_TIME = 24 * 60 * 60 * 1000;

    //推荐食谱缓存
    public static final String ALL_RECOMMEND_JSON = "all_recommend_json";
    public static final String ALL_RECOMMEND_TIME = "all_recommend_time";

    public static final String ALL_FUNCTION_JSON = "all_function_json";
    public static final String ALL_FUNCTION_TIME = "all_function_time";

    public static final String ALL_SICKNESS_JSON = "all_sickness_json";
    public static final String ALL_SICKNESS_TIME = "all_sickness_time";

    //食材大全 食材列表缓存
    public static final String ALL_M_LIST_JSON = "all_m_list_json_";
    public static final String ALL_M_LIST_TIME = "all_m_list_time_";

    //美食天下 食谱分类列表缓存
    public static final String FOODWORLD_R_LIST_JSON = "food_r_list_json_";
    public static final String FOODWORLD_R_LIST_TIME = "food_r_list_time_";
}
