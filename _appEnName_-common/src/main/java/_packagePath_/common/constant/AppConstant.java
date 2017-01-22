package ${packageName}.common.constant;
import ${packageName}.common.annotation.FieldComment;

/**
 * Created by shengshan.tang on 2015/11/24 at 16:45
 */
public interface AppConstant {

    interface OrderNoType {
        String COURSE = "KC";
    }

    interface System{
        String memberHttpUrl = "member.http.url";
    }


    interface ResponseCode {
        @FieldComment("正常")
        String NORMAL = "00";  //正常

        @FieldComment("未登录")
        String NOT_LOGIN = "01";  //未登录

        @FieldComment("没有权限")
        String NOT_AUTHORITY = "02";  //没有权限

        @FieldComment("系统错误")
        String SYSTEM_ERROR = "03";   //系统错误

        @FieldComment("第三方登录,未绑定")
        String NOT_BIND = "04";   //第三方登录的,手机账号未绑定

        @FieldComment("未完善个人信息")
        String NOT_SUBMIT_USER_INFO = "05";  //未完善个人信息

        @FieldComment("未提交体脂信息")
        String NOT_SUBMIT_TZ_INFO = "06";   //未提交体脂信息

    }

    interface AppLog {
        String APP_LOG_SHOW_ALL = "app_log_show_all";
    }

    interface Config{

        String userOtherInfo = "userOtherInfo";
    }


}
