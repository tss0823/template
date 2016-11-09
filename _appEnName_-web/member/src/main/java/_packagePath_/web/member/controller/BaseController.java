package ${packageName}.web.member.controller;

import ${packageName}.common.constant.AppConstant;
import ${packageName}.service.inter.UserService;
import com.yuntao.platform.common.CustomizedPropertyConfigurer;
import com.yuntao.platform.common.exception.AuthException;
import com.yuntao.platform.common.exception.BizException;
import com.yuntao.platform.common.log.HbLogContextMgr;
import com.yuntao.platform.common.log.LogContext;
import com.yuntao.platform.common.utils.ExceptionUtils;
import com.yuntao.platform.common.utils.ResponseObjectUtils;
import com.yuntao.platform.common.web.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static com.yuntao.platform.common.log.HbLogContextMgr.getLogContext;

/**
 * Created by shengshan.tang on 2015/11/25 at 18:57
 */
public class BaseController {

    private final static Logger stackLog = LoggerFactory.getLogger("stackLog");

    protected final static Logger log = org.slf4j.LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected UserService userService;


    @ExceptionHandler
    @ResponseBody
    public ResponseObject resolveException(HttpServletRequest request, Exception ex) {
        HbLogContextMgr.errorLog(ex);
        ResponseObject responseObject = ResponseObjectUtils.buildResObject();
        responseObject.setSuccess(false);
        responseObject.setMessage(ex.getMessage());
        LogContext context = getLogContext();
        if (ex instanceof BizException) {
            responseObject.setLevel(AppConstant.ResponseLevel.WARN);
            context.setLogLevel(LogContext.LogLevel.BIZ_ERROR);
        } else if (ex instanceof AuthException) {
            AuthException authException = (AuthException) ex;
            responseObject.setLevel(AppConstant.ResponseLevel.WARN);
            responseObject.setCode(authException.getCode());
            context.setLogLevel(LogContext.LogLevel.BIZ_ERROR);
        } else {
            responseObject.setLevel(AppConstant.ResponseLevel.ERROR);
            responseObject.setCode(AppConstant.ResponseCode.SYSTEM_ERROR);
        }
        if (!CustomizedPropertyConfigurer.getModel().equals("prod")) {
            responseObject.setData(ExceptionUtils.getPrintStackTrace(ex));
        }
        HbLogContextMgr.setResponse(responseObject);
        return responseObject;
    }

}
