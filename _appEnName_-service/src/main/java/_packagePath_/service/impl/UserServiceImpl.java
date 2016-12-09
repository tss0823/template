package ${packageName}.service.impl;

import ${packageName}.dal.mapper.UserMapper;
import ${packageName}.model.domain.User;
import ${packageName}.model.enums.*;
import ${packageName}.model.query.UserQuery;
import ${packageName}.model.vo.UserVo;
import ${packageName}.service.inter.UserService;
import com.yuntao.platform.common.cache.CacheService;
import com.yuntao.platform.common.exception.BizException;
import com.yuntao.platform.common.utils.BeanUtils;
import com.yuntao.platform.common.utils.CollectUtils;
import com.yuntao.platform.common.utils.DateUtil;
import com.yuntao.platform.common.utils.ResponseHolder;
import com.yuntao.platform.common.web.Pagination;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl extends AbstService implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CacheService cacheService;


    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User findByAccountAndType(String accountNo, Integer type) {
        UserQuery query = new UserQuery();
        query.setAccountNo(accountNo);
        query.setType(type);
        Map<String, Object> queryMap = BeanUtils.beanToMap(query);
        List<User> userList = userMapper.selectList(queryMap);
        if (CollectionUtils.isNotEmpty(userList)) {
            return userMapper.selectList(queryMap).get(0);
        }
        return null;
    }


    @Override
    public Pagination<UserVo> selectPage(UserQuery query) {
        Map<String, Object> queryMap = BeanUtils.beanToMap(query);
        long totalCount = userMapper.selectListCount(queryMap);
        Pagination<UserVo> pageInfo = new Pagination<>(totalCount,
                query.getPageSize(), query.getPageNum());
        if (totalCount == 0) {
            return pageInfo;
        }
        queryMap.put("pagination", pageInfo);
        List<User> dataList = userMapper.selectList(queryMap);
        List<UserVo> resultList = CollectUtils.transList(dataList, UserVo.class);
        pageInfo.setDataList(resultList);
        return pageInfo;
    }

    @Override
    public Pagination<UserVo> selectMemberPage(UserQuery query) {
        query.setType(UserType.member.getCode());
        Map<String, Object> queryMap = BeanUtils.beanToMap(query);
        long totalCount = userMapper.selectListCount(queryMap);
        Pagination<UserVo> pageInfo = new Pagination<>(totalCount,
                query.getPageSize(), query.getPageNum());
        if (totalCount == 0) {
            return pageInfo;
        }
        queryMap.put("pagination", pageInfo);
        List<User> dataList = userMapper.selectList(queryMap);
        List<UserVo> newDataList = new ArrayList<>(dataList.size());
        pageInfo.setDataList(newDataList);
        for (User user : dataList) {
            UserVo userVo = BeanUtils.beanCopy(user, UserVo.class);
            if(userVo.getSex() != null){
                UserSex userSex = UserSex.getByCode(userVo.getSex());
                if(userSex != null){
                    userVo.setSexText(userSex.getDescription());
                }
            }

//            if(userVo.getBindStatus() != null){
//                UserBindStatus userBindStatus = UserBindStatus.getByCode(userVo.getBindStatus());
//                if(userBindStatus != null){
//                    userVo.setBindStatusText(userBindStatus.getDescription());
//                }
//            }
//            if(userVo.getCourseStatus() != null){
//                UserCourseStatus userCourseStatus = UserCourseStatus.getByCode(userVo.getCourseStatus());
//                if (userCourseStatus != null) {
//                    userVo.setCourseStatusText(userCourseStatus.getDescription());
//                }
//            }
            newDataList.add(userVo);
        }
        return pageInfo;
    }

    @Override
    public List<UserVo> selectList(UserQuery query) {
        Map<String, Object> queryMap = BeanUtils.beanToMap(query);
        List<User> dataList = userMapper.selectList(queryMap);
        return CollectUtils.transList(dataList,UserVo.class);
    }

    @Override
    public List<UserVo> selectByType(Integer type) {
        UserQuery userQuery = new UserQuery();
        userQuery.setType(type);
        return selectList(userQuery);
    }

    @Override
    public User login(String accountNo, Integer type, String pwd) {
        User user = this.findByAccountAndType(accountNo, type);
        if (user == null) {
            throw new BizException("账号不存在");
        }
        if (!user.getPwd().equals(pwd)) {
            throw new BizException("密码不正确");
        }
        setCurrentUser(user);
        return user;
    }

    @Override
    public void logout(Long userId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Cookie cookie = WebUtils.getCookie(request, "sid");
        if (cookie == null || StringUtils.isEmpty(cookie.getValue())) {
            String sid = cookie.getValue();
            cacheService.remove("sid_" + sid);
            cacheService.remove("login_user_" + userId);
        }
    }

    @Override
    public User getCurrentUser() {
        //浏览器中cookie 缓存
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Cookie cookie = WebUtils.getCookie(request, "sid");
        if (cookie == null || StringUtils.isEmpty(cookie.getValue())) {
            HttpServletResponse response = (HttpServletResponse) ResponseHolder.get();
            String uuid = UUID.randomUUID().toString();
            Cookie sidCookie = new Cookie("sid", uuid);
            sidCookie.setMaxAge(DateUtil.MONTH_SECONDS);
            sidCookie.setPath("/");
            response.addCookie(sidCookie);
            return null;
        }
        //获取userId
        String sid = cookie.getValue();
        Object value = cacheService.get("sid_" + sid);
        if (value == null) {
            return null;
        }
        Long userId = (Long) value;
        //从cache中获取
        User user = (User) cacheService.get("login_user_" + userId);
        if (user != null) {
            return user;
        }
        user = this.findById(userId);
        if (user != null) {
            cacheService.set("login_user_" + userId, user, 60 * 60 * 24 * 3);
        }
        return user;
    }

    @Override
    public void setCurrentUser(User user) {
        //浏览器中cookie 缓存
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Cookie cookie = WebUtils.getCookie(request, "sid");
        String sid = null;
        if (cookie == null || StringUtils.isEmpty(cookie.getValue())) {
            HttpServletResponse response = (HttpServletResponse) ResponseHolder.get();
            sid = UUID.randomUUID().toString();
            Cookie sidCookie = new Cookie("sid", sid);
            sidCookie.setMaxAge(DateUtil.MONTH_SECONDS);
            sidCookie.setPath("/");
            response.addCookie(sidCookie);
        } else {
            sid = cookie.getValue();
        }
        //set cache userId
        cacheService.set("sid_" + sid, user.getId());

        //set cache user
        cacheService.set("login_user_" + user.getId(), user);

    }

    @Override
    @Transactional
    public User register(User user) {
        if(user.getSex() == null){
            user.setSex(UserSex.man.getCode());
        }
        userMapper.insert(user);

        if(user.getType() == UserType.member.getCode()){
            //减脂数据初始化 TODO
        }

        setCurrentUser(user);
        return user;
    }

    @Override
    public int updateById(User user) {
        int result = userMapper.updateById(user);
        user = findById(user.getId());
        setCurrentUser(user);
        return result;
    }

    @Override
    public User updateByAccountAndType(User user) {
        userMapper.updateByAccountAndType(user);
        //以后更新相关属性，要先拉一把数据，再同步到cookie currentUser
        return user;
    }

    @Override
    public User selectOne(UserQuery query) {
        Map<String, Object> queryMap = BeanUtils.beanToMap(query);
        List<User> dataList = this.userMapper.selectList(queryMap);
        if (dataList.size() == 0) {
            return null;
        }
        if (dataList.size() > 1) {
            throw new BizException("too many datas");
        }
        return dataList.get(0);
    }


}
