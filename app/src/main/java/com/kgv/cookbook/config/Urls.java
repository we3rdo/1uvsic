package com.kgv.cookbook.config;

/**
 * Created by 陈可轩 on 2016/12/8 14:49
 * Email : 18627241899@163.com
 * Description : net api
 */
public class Urls {

    //图片地址主机
    public static final String BASE_IMG_URL = "http://pimi.kgv.cn";

    //食谱机主机名
    public static final String BASE_URL = "http://pimi.kgv.cn/RecipeMachine";

    //更新
    public static final String UPDATE_URL = BASE_URL + "/update";

    //新apk
    public static final String APK_URL = "http://pimi.kgv.cn/recipe.apk";

    //检查用户名是否存在 username 返回0表示已存在,返回1表示可用
    public static final String CHECK_USERNAME = BASE_URL + "/usernameCheck";

    //检查手机号是否存在 mobile 返回0表示已存在,返回1表示可用
    public static final String CHECK_PHONE_NUMBER = BASE_URL + "/mobileCheck";

    //注册 获取验证码 mobile
    public static final String REGISTER_GET_CODE = BASE_URL + "/sms";

    //注册 mobile username code password
    public static final String REGISTER = BASE_URL + "/userRegister";

    //登录 username password
    public static final String LOGIN = BASE_URL + "/userLogin";

    //主页-食谱->食材大全 : 搜索食材 keyword
    public static final String SEARCH_SHICAI = BASE_URL + "/searchShicai/keyword/";

    //主页-食谱->食材大全 : 热门/定制 食材列表
    public static final String HOT_SHICAI = BASE_URL + "/hot_shicai/";

    //主页-食谱->食材大全 : 定制食材 data : id,id,id..
    public static final String MADE_SHICAI = BASE_URL + "/shicaiMade/";

    //主页-食谱->食材大全 : 初始化定制食材
    public static final String RESET_SHICAI = BASE_URL + "/shicaicategoryRestore/";

    //食谱分类
    public static final String SHIPU_CATE_BASE_URL = BASE_URL + "/getCategory/cate_id/";

    //主页-食谱->食材大全-组合食材 : ground_id : 食材id
    public static final String GROUP_MARERIAL = BASE_URL + "/getGroupshicai/ground_id/-/p/";

    //主页-食谱->食材大全&美食天下-推荐页面(0)-功能调理
    public static final String FUNCTION_G = SHIPU_CATE_BASE_URL + "6";

    //主页-食谱->食材大全&美食天下-推荐页面(0)-疾病调理
    public static final String SICKNESS_G = SHIPU_CATE_BASE_URL + "7";

    //主页-食谱->食材大全&美食天下-推荐页面(0)-获取子分类 cate_id : 子分类id
    public static final String RECIPE_CATEGORY_CONTENT = BASE_URL + "/getCookbookCatecontent/cate_id/";

    //主页-食谱->食材大全&美食天下-推荐页面(0)-推荐食谱20条
    public static final String RECOMMEND_RECIPE = BASE_URL + "/coobook_content/p/";

    //主页-食谱->食材大全-查看全部(1)-所有食材列表
    public static final String SHICAI_CATEGORY = BASE_URL + "/shicai_category_list/cate_id/";

    //主页-食谱->菜单操作 : 读取早餐菜单数据 create_time : 2016-12-16..(if null is today)
    public static final String MENU_MORNING_LIST = BASE_URL + "/blogData/";

    //主页-食谱->菜单操作 : 读取午餐菜单数据 create_time : 2016-12-16..(if null is today)
    public static final String MENU_NOONING_LIST = BASE_URL + "/blogData/";

    //主页-食谱->菜单操作 : 读取晚餐菜单数据 create_time : 2016-12-16..(if null is today)
    public static final String MENU_EVENING_LIST = BASE_URL + "/blogData/";

    //主页-食谱->菜单操作 : 添加早餐 - -> id
    public static final String MENU_MORNING_ADD = BASE_URL + "/blogAction/";
    //主页-食谱->菜单操作 : 删除早餐 - -> del id
    public static final String MENU_MORNING_DELETE = BASE_URL + "/blogAction/";

    //主页-食谱->菜单操作 : 添加午餐 - -> id
    public static final String MENU_NOONING_ADD = BASE_URL + "/blogAction/";
    //主页-食谱->菜单操作 : 删除午餐 - -> del id
    public static final String MENU_NOONING_DELETE = BASE_URL + "/blogAction/";

    //主页-食谱->菜单操作 : 添加晚餐 - -> id
    public static final String MENU_EVENING_ADD = BASE_URL + "/blogAction/";
    //主页-食谱->菜单操作 : 删除晚餐 - -> del id
    public static final String MENU_EVENING_DELETE = BASE_URL + "/blogAction/";

    //主页-食谱->提交菜单到日志
    public static final String MENU_SUBMIT_2_BLOG = BASE_URL + "/leadBlog/";

    //主页-食谱->日志操作 : 读取早餐日志数据 create_time : 2016-12-16..(if null is today)
    public static final String BLOG_MORNING_LIST = BASE_URL + "/blogData/";

    //主页-食谱->日志操作 : 读取午餐日志数据 create_time : 2016-12-16..(if null is today)
    public static final String BLOG_NOONING_LIST = BASE_URL + "/blogData/";

    //主页-食谱->日志操作 : 读取晚餐日志数据 create_time : 2016-12-16..(if null is today)
    public static final String BLOG_EVENING_LIST = BASE_URL + "/blogData/";

    /*              食谱详情页    id=?         */
    public static final String SHIPU_DETAIL = BASE_URL + "/getCookbookdetail/id/";

    /*              搜索页       keyword=?     */
    public static final String SEARCH_COOKBOOK = BASE_URL + "/searchCookbook/search/-/p/";

    //主页-食谱->美食天下-默认页面(0)-热门/定制 食谱分类列表
    public static final String HOT_SHIPU_CATE = BASE_URL + "/hot_cookbook_category/";

    //主页-食谱->美食天下 : 初始化定制食谱
    public static final String RESET_SHIPU = BASE_URL + "/cookbookcategoryRestore/";

    //主页-食谱->美食天下 : 定制食谱 data : id,id,id..
    public static final String MADE_SHIPU = BASE_URL + "/cookbookcategoryMade/";

    //主页-食谱->一周菜谱 : 导出一周食谱列表
    public static final String WEEK_LIST = BASE_URL + "/week_cookbook/";

    //主页-食谱->一周菜谱 : 定制食材 shicai: id,id..
    public static final String WEEK_LIST_EDIT = BASE_URL + "/week_cookbook/";

    //主页-食谱->美味套餐 : 导出套餐列表 可选: [p:页码],[people_num_id:人数id],[cid:食谱分类id],[title:search key]
    public static final String SET_MEAL_GET_LIST = BASE_URL + "/set_meal";

    //主页-食谱->美味套餐 : 套餐详情
    public static final String SET_MEAL_DETAIL = BASE_URL + "/recipe_detail/id/";

    //主页-健康管理->日志操作 : 删除早餐日志 - -> del id
    public static final String BLOG_MORNING_DELETE = BASE_URL + "/blogAction/";

    //主页-健康管理->日志操作 : 删除午餐日志 - -> del id
    public static final String BLOG_NOONING_DELETE = BASE_URL + "/blogAction/";

    //主页-健康管理->日志操作 : 删除晚餐日志 - -> del id
    public static final String BLOG_EVENING_DELETE = BASE_URL + "/blogAction/";

    //主页-健康管理->获取今天营养信息总和 : create_time :指定日期
    public static final String DAY_NUTRITION_COUNT = BASE_URL + "/blogNutritionData/";

    //主页-健康管理->导出过去七天营养信息 : create_time:当天日期
    public static final String BLOG_PAST_WEEK_NUTRITION_DATA = BASE_URL + "/past_seven_days/";

    //主页-健康管理->导出建议营养信息表 create_time:日期 unit:营养名
    public static final String SUGGEST_NUTRITION_LIST = BASE_URL + "/healthSuggest/";

    //主页-设置->家庭设置 : 获取家庭成员列表
    public static final String FAMILY_LIST = BASE_URL + "/familyData/";

    //主页-设置->家庭设置 : 添加家庭成员 - -> 年龄id
    public static final String FAMILY_ADD = BASE_URL + "/familyAction/";

    //主页-设置->家庭设置 : 编辑家庭成员人数 field=num "-"=id "="=num id
    public static final String FAMILY_EDIT_PEOPLE = BASE_URL + "/familyAction/";

    //主页-设置->家庭设置 : 编辑家庭成员健康状况 field=health "-"=id "="=health id
    public static final String FAMILY_EDIT_HEALTH = BASE_URL + "/familyAction/";

    //主页-设置->家庭设置 : 删除家庭成员 - -> id
    public static final String FAMILY_DELETE = BASE_URL + "/familyAction/";

    //主页-设置->修改密码 : 修改密码
    public static final String CHANGE_PASSWORD = BASE_URL + "/changePassword";

    //主页-设置->忘记密码 : 发送手机验证码
    public static final String FORGET_GET_CODE = BASE_URL + "/sms";

    //主页-设置->忘记密码 : (post)检查输入的验证码是否正确 mobile手机号 code验证码
    public static final String CHECK_CODE = BASE_URL + "/checkMobilecode";

    //主页-设置->忘记密码 : (post)忘记密码 mobile手机号 code验证码 password新密码，repassword确认密码
    public static final String FORGET_PWD = BASE_URL + "/mobile_changePassword";

    //主页-设置->喜好设置 : 导出不喜欢的食谱分类id
    public static final String INTEREST_DISLIKE_RECIPES_LIST = BASE_URL + "/leadMemberdata/";

    //主页-设置->喜好设置 : 导出不喜欢的食材id
    public static final String INTEREST_DISLIKE_MATERIALS_LIST = BASE_URL + "/leadMemberdata/";

    //主页-设置->喜好设置 : 设置不喜欢的食谱分类 data:id,id,id..
    public static final String INTEREST_DISLIKE_RECIPES_SET = BASE_URL + "/setMemberdata/";

    //主页-设置->喜好设置 : 设置不喜欢的食材 data:id,id,id..
    public static final String INTEREST_DISLIKE_MATERIALS_SET = BASE_URL + "/setMemberdata/";
}
