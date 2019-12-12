package com.boosal.smartlibrary.net;

public class UrlStore {

    //  http://47.98.201.108:8090  测试环境
    //  http://api.szjiyusc.com  正式环境
    public static final String BASE_HOST="http://dev.boosal.com:18200/";

    public static final String APP_FACED_API="api/v1/";

    public static final String BASEURL_APP = BASE_HOST+APP_FACED_API;

    public static final String OTHER_API = "http://app.freedpi.cn:18000/";

    //获取验证码接口
    public static final String Get_Sms_Check_Code = "smsCheckCode";

    //注册接口
    public static final String Register = "user/me/register?type=TELEPHONE";

    //密码登录接口
    public static final String Auth_Password = "user/auth/login?type=PASSWORD";

    //验证码登录接口
    public static final String Auth_Sms_Check_Code = "user/auth/login?type=SMS_CHECK_CODE";

    //刷新token接口
    public static final String Refresh_Token = "user/auth/refreshToken";

    //获取用户个人信息接口
    public static final String Get_User_Info = "user/me/profile";

    //上报用户信息
//    public static final String Update_Member = "member/updateMember";

    //获取版本信息接口
    public static final String GetVersion_Info = "versionApp?action=LATEST_VERSION&type=CLIENT_ANDROID";

    //微信登陆
    public static final String Wechat_Login = "user/auth/login?type=WECHAT_APP";

    //实时数据上报
    public static final String Dev_Info = "dev/devInfo";

    //纠错信息上报
    public static final String Update_Dev = "dev/updateDev";

    //意见反馈
    public static final String Feedback = "feedback";

    //上传图片
    public static final String Upload_Imag = "file/image";

}

