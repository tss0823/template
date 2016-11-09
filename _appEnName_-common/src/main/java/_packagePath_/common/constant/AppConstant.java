package ${packageName}.common.constant;

/**
 * Created by shengshan.tang on 2015/11/24 at 16:45
 */
public interface AppConstant {

    interface OrderNoType {
        String COURSE = "KC";
    }


    interface ResponseLevel {
        String INFO = "info";
        String WARN = "warn";
        String ERROR = "error";
    }

    interface ResponseType {

        String NORMAL = "normal";
        String DEPLOY_SCRIPT = "deploy_script";
        String MONITOR_SERVER = "monitor_server";
        String MONITOR_STATUS = "monitor_status";
        String TEST_HTTP_EXCUTE = "test_http_execute";
    }

    interface ResponseCode {
        String NORMAL = "00";
        String NOT_LOGIN = "01";
        String NOT_AUTHORITY = "02";
        String SYSTEM_ERROR = "03";
        String NOT_BIND = "04";
        String NOT_SUBMIT_USER_INFO = "05";
        String NOT_SUBMIT_TZ_INFO = "06";
    }

    interface AppLog {
        String APP_LOG_SHOW_ALL = "app_log_show_all";
    }

    interface Config{

        String userOtherInfo = "userOtherInfo";
    }

    interface  TuiSong{

        String nixiGetuiTest = "nixi_getui_test";
        String nixiGetuiProd = "nixi_getui_prod";

    }

}
