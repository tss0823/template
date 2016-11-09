package ${packageName}.service.auth;

import ${packageName}.common.annotation.Auth;
import ${packageName}.common.constant.AppConstant;
import ${packageName}.model.domain.User;
import ${packageName}.service.inter.UserService;
import com.yuntao.platform.common.annotation.NeedLogin;
import com.yuntao.platform.common.exception.AuthException;
import com.yuntao.platform.common.log.HbLogContextMgr;
import com.yuntao.platform.common.utils.NumberUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
public class AuthAspect {

    private final static Logger stackLog = LoggerFactory.getLogger("stackLog");

    @Autowired
    private UserService userService;

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object aroundController(final ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        NeedLogin needLogin = method.getAnnotation(NeedLogin.class);
        if (needLogin != null) {  //需要权限校验
            User user = userService.getCurrentUser();
            if (user == null) {
                AuthException exception = new AuthException("您已退出登录，请重新登录", AppConstant.ResponseCode.NOT_LOGIN);
                throw exception;
            }
            HbLogContextMgr.setUser(user.getId(), user.getMobile(), user.getNickname());

            //需要绑定权限
            Auth auth = method.getAnnotation(Auth.class);
            if (auth != null && auth.needBind()) {
                if (NumberUtil.getNumber(user.getBindStatus()).equals(UserBindStatus.notBind.getCode())) {
                    AuthException exception = new AuthException("您需要先绑定手机号才能操作", AppConstant.ResponseCode.NOT_BIND);
                    throw exception;
                }
            }
        }
        //资源权限校验 TODO
        Object returnObj = joinPoint.proceed();
//        stackLog.info("mark reponse start");
        HbLogContextMgr.setResponse(returnObj);
        //
        return returnObj;
    }
}
