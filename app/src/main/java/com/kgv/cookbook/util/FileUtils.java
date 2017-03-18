package com.kgv.cookbook.util;

import android.os.Environment;

/**
 * Created by 陈可轩 on 2016/11/25 17:48
 * Email : 18627241899@163.com
 * Description :
 */
public class FileUtils {

    /**
     * 检查是否插入SD卡
     */
    public static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

}
