package ${packageName}.common.utils;

import ${packageName}.common.http.HttpNewUtils;
import ${packageName}.common.http.RequestRes;
import ${packageName}.common.http.ResponseRes;
import com.yuntao.platform.common.exception.BizException;
import com.yuntao.platform.common.utils.DateUtil;
import com.yuntao.platform.common.utils.MD5Util;
import org.apache.commons.codec.binary.Base64;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shan on 2016/8/20.
 */
public class SendMsgUtils {

    public static void sendCheckCodeSMS(String mobile,String msg){
       sendSMS(mobile,40168L,msg);
    }

    private static void sendSMS(String mobile,Long templateId,String msg){
        try {
            RequestRes requestRes = new RequestRes();
            Map<String, String> headers = new HashMap();
            headers.put("Accept", "application/json");
            headers.put("Content-Type", "application/json;charset=utf-8");
            StringBuilder sb = new StringBuilder("https://app.cloopen.com:8883");
            String accountSid = "8a48b5514fa577af014fa79df581097e";
            StringBuilder sigSb = new StringBuilder(accountSid);
            sigSb.append("e8436729356e4267a34a2b1d13ddcdc3");
            String time = DateUtil.getFmt(new Date().getTime(), "yyyyMMddHHmmss");
            sigSb.append(time);
            String sig = MD5Util.MD5Encode(sigSb.toString());
            sb.append("/2013-12-26/Accounts/" + accountSid + "/SMS/TemplateSMS?sig=");
            sb.append(sig);
            requestRes.setUrl(sb.toString());
            byte[] authBytes = Base64.encodeBase64(new String(accountSid + ":" + time).getBytes());
            headers.put("Authorization", new String(authBytes));
            requestRes.setHeaders(headers);
            String paramText = "{\"to\":\""+mobile+"\",\"appId\":\"8a48b5514fba2f87014fba36e8fb001f\",\"templateId\":\""+templateId+"\",\"datas\":[\""+msg+"\"]}";
            requestRes.setParamText(paramText);
            ResponseRes execute = HttpNewUtils.execute(requestRes);
            byte[] result = execute.getResult();
            String s = new String(result);
            System.out.println(s);
        } catch (Exception e) {
            throw new BizException("send sms failed",e);
        }


    }
    public static void main(String[] args) {
//                /2013-12-26/Accounts/abcdefghijklmnopqrstuvwxyz012345/SMS/TemplateSMS?sig=
//                C1F20E7A9733CE94F680C70A1DBABCDE
    }
}
