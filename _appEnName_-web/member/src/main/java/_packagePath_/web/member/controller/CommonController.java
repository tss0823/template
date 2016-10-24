package ${packageName}.web.member.controller;

import ${packageName}.model.domain.User;
import ${packageName}.service.inter.UserService;
import com.yuntao.platform.common.log.HbLogContextMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;

/**
 * Created by shengshan.tang on 2015/12/16 at 23:45
 */
@Controller
public class CommonController extends BaseController {


    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        //初始化statck日志参数
        HbLogContextMgr.initData();
    }


    @RequestMapping("/")
    public String index() {
        User user = userService.getCurrentUser();
        if (user == null) {
            return "redirect:/login.html";
        } else {
            return "redirect:/list.html";

        }

    }

    @RequestMapping("checkServerStatus")
    @ResponseBody
    public String checkServerStatus() {
        return "checkServerStatusIsOK";
    }

    @RequestMapping("favicon.ico")
    String favicon() {
        return "forward:/_resources/images/favicon.ico";
    }

}
