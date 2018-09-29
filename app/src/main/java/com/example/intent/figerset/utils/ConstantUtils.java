package com.example.intent.figerset.utils;

import android.net.Uri;

public class ConstantUtils {
    /** service  标识服务类型 **/
    public static int DEFAULT_WRITE_FLAG = 0;
    public static int SMS_WRITE_FLAG = 1;
    public static int CALL_WRITE_FLAG = 2;
    public static int SMS_READ_FLAG = 3;
    public static int CALL_READ_FLAG = 4;
    public static int CONTACT_READ_FLAG = 5;
    public static int CONTACT_WRITE_FLAG = 6;

    /** sms call SD卡中的文件名 **/
    public static String SMS_FILENAME ="Sms_List.out";
    public static String CALL_FILENAME ="Call_List.out";

    /** contentProvider 访问的数据库 **/
    public static Uri SMS_URI = Uri.parse("content://sms/");
    public static Uri CALL_URI = Uri.parse("content://call_log/calls");

    /** key 保存到SD卡的秘钥 **/
    public static String SMS_KEY="123";
    public static String CALL_KEY="123";

    public static final String INDEX_FLAG = "index_flag";
}
