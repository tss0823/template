package ${packageName}.common.utils;

import com.yuntao.platform.common.CustomizedPropertyConfigurer;
import com.yuntao.platform.common.exception.BizException;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by tangshengshan on 16-11-6.
 */
public class SMSCodeUtils {

    public static String getSMSCode(String mobile){
        if (mobile.length() != 11 || !mobile.startsWith("1")) {
            throw new BizException("手机账号不合法");
        }
        String smsCode = RandomStringUtils.random(4, false, true);
        if (CustomizedPropertyConfigurer.isProd()) {
            SendMsgUtils.sendCheckCodeSMS(mobile,smsCode);
        }
        return smsCode;
    }
}
