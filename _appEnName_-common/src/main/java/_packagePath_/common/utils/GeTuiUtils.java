package ${packageName}.common.utils;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.NotificationTemplate;
import ${packageName}.common.constant.AppConstant;
import com.yuntao.platform.common.CustomizedPropertyConfigurer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class GeTuiUtils {

    protected final static Logger stackLog = LoggerFactory.getLogger("stackLog");

    //采用"Java SDK 快速入门"， "第二步 获取访问凭证 "中获得的应用配置，用户可以自行替换
    private static String appId = "fCyMvHKARl871jGk4vkhb3";
    private static String appKey = "yw4mL8VMOn7cEX4wNS2mQ7";
    private static String masterSecret = "xFJJpybAmQ8me65BAE3rQ1";

    //别名推送方式
    // static String Alias = "";
    static String host = "http://sdk.open.api.igexin.com/apiex.htm";

    private static NotificationTemplate notificationTemplateDemo(String title,String content) {
        NotificationTemplate template = new NotificationTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 设置通知栏标题与内容
        template.setTitle(title);
        template.setText(content);
        // 配置通知栏图标
        template.setLogo("icon.png");
        // 配置通知栏网络图标
        template.setLogoUrl("");
        // 设置通知是否响铃，震动，或者可清除
        template.setIsRing(true);
        template.setIsVibrate(true);
        template.setIsClearable(true);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent("请输入您要透传的内容");
        return template;
    }

    public static void pushMessage(Long userId,String title,String content) {
        List<Long> userIds = new ArrayList<>();
        userIds.add(userId);
        pushMessage(userIds,title,content);

    }

    public static void pushMessage(List<Long> userIds,String title,String content) {

        // 配置返回每个用户返回用户状态，可选
        System.setProperty("gexin.rp.sdk.pushlist.needDetails", "true");
        // 配置返回每个别名及其对应cid的用户状态，可选
        // System.setProperty("gexin_pushList_needAliasDetails", "true");
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        // 通知透传模板
        NotificationTemplate template = notificationTemplateDemo(title,content);
        ListMessage message = new ListMessage();
        message.setData(template);
        // 设置消息离线，并设置离线时间
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        // 配置推送目标
        List targets = new ArrayList();
        for (Long userId : userIds) {
            Target target = new Target();
            target.setAppId(appId);
            String alias = AppConstant.TuiSong.nixiGetuiProd;
            if (!CustomizedPropertyConfigurer.isProd()) {
                alias = AppConstant.TuiSong.nixiGetuiTest;
            }
            alias += "_"+userId;
            target.setAlias(alias);
            targets.add(target);
        }
        // taskId用于在推送时去查找对应的message
        String taskId = push.getContentId(message);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToList(taskId, targets);
            stackLog.info("getui info " + ret.getResponse().toString());
        } catch (RequestException e) {
            stackLog.error("getui res error,userIds=" + StringUtils.join(userIds,","));
        }
    }

    public static void main(String[] args) throws Exception {
        pushMessage(27L,"测试推送","我的测试内容");
    }

}