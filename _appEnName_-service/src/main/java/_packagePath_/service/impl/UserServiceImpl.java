package ${packageName}.service.impl;

import ${packageName}.common.constant.CacheConstant;
import ${packageName}.dal.mapper.UserMapper;
import ${packageName}.model.domain.Account;
import ${packageName}.model.domain.OuterUser;
import ${packageName}.model.domain.User;
import ${packageName}.model.enums.*;
import ${packageName}.model.query.OuterUserQuery;
import ${packageName}.model.query.UserQuery;
import ${packageName}.model.vo.MemberVo;
import ${packageName}.model.vo.UserVo;
import ${packageName}.service.inter.AccountService;
import ${packageName}.service.inter.OuterUserService;
import ${packageName}.service.inter.UserService;
import com.yuntao.platform.common.auth.AuthUser;
import com.yuntao.platform.common.auth.AuthUserService;
import com.yuntao.platform.common.cache.CacheService;
import com.yuntao.platform.common.exception.BizException;
import com.yuntao.platform.common.utils.*;
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
import java.util.*;

@Service("userService")
public class UserServiceImpl extends AbstService implements UserService,AuthUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OuterUserService outerUserService;


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
    public Pagination<MemberVo> selectMemberPage(UserQuery query) {
        query.setType(UserType.member.getCode());
        Map<String, Object> queryMap = BeanUtils.beanToMap(query);
        long totalCount = userMapper.selectListCount(queryMap);
        Pagination<MemberVo> pageInfo = new Pagination<>(totalCount,
                query.getPageSize(), query.getPageNum());
        if (totalCount == 0) {
            return pageInfo;
        }
        queryMap.put("pagination", pageInfo);
        List<User> dataList = userMapper.selectList(queryMap);
        List<MemberVo> newDataList = new ArrayList<>(dataList.size());
        pageInfo.setDataList(newDataList);
        for (User user : dataList) {
            MemberVo memberVo = BeanUtils.beanCopy(user, MemberVo.class);
            if(user.getSex() != null){
                UserSex userSex = UserSex.getByCode(user.getSex());
                if(userSex != null){
                    memberVo.setSexText(userSex.getDescription());
                }
            }
//
            if(memberVo.getBindStatus() != null){
                UserBindStatus userBindStatus = UserBindStatus.getByCode(user.getBindStatus());
                if(userBindStatus != null){
                    memberVo.setBindStatusText(userBindStatus.getDescription());
                }
            }
            if(user.getCourseStatus() != null){
                UserCourseStatus userCourseStatus = UserCourseStatus.getByCode(user.getCourseStatus());
                if (userCourseStatus != null) {
                    memberVo.setCourseStatusText(userCourseStatus.getDescription());
                }
            }
            newDataList.add(memberVo);
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
            cacheService.remove(CacheConstant.loginSid+"_" + sid);
            cacheService.remove(CacheConstant.loginUser+"_" + userId);
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
        Object value = cacheService.get(CacheConstant.loginSid+"_" + sid);
        if (value == null) {
            return null;
        }
        Long userId = (Long) value;
        //从cache中获取
        User user = (User) cacheService.get(CacheConstant.loginUser+"_" + userId);
        if (user != null) {
            return user;
        }
        user = this.findById(userId);
        if (user != null) {
            cacheService.set(CacheConstant.loginUser+"_" + userId, user, 60 * 60 * 24 * 3);
        }
        return user;
    }

    /**
     * 仅仅修改当前用户操作
     * @param user
     */
    private void setCurrentUser(User user) {
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
        cacheService.set(CacheConstant.loginSid+"_" + sid, user.getId());

        //set cache user
        cacheService.set(CacheConstant.loginUser+"_" + user.getId(), user);

        //set msg cache user
        if(user.getType() == UserType.member.getCode() || user.getType() == UserType.trainer.getCode() ||
                user.getType() == UserType.bigScreen.getCode()){
            cacheService.setGlobal(CacheConstant.loginSid+"_" + sid, user.getId());
            cacheService.setGlobal(CacheConstant.loginUser+"_" + user.getId(), user);
        }
        //end



    }

    @Override
    @Transactional
    public User register(User user) {
        if(user.getSex() == null){
            user.setSex(UserSex.man.getCode());
        }
        userMapper.insert(user);

        if(user.getType() == UserType.member.getCode()){
            //账户余额数据初始化
            Account account = new Account();
            account.setUserId(user.getId());
            account.setStatus(1);  //正常
            account.setAmount(0);
            account.setCanUseAmount(0);
            account.setLockAmount(0);
            accountService.insert(account);
            //end

            user.setCourseStatus(UserCourseStatus.notJoin.getCode());
            this.updateById(user);
            //end

            //减脂数据初始化 TODO
        }

        setCurrentUser(user);
        return user;
    }

    @Override
    public int updateById(User user) {
        int result = userMapper.updateById(user);
        user = findById(user.getId());
        User currentUser = getCurrentUser();
        if(currentUser.getId().longValue() == user.getId()){  //仅仅修改当前用户去掉缓存
            setCurrentUser(user);
        }
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

    @Override
    public User selectByOuter(Integer type, String openId) {
        OuterUserQuery outerUserQuery = new OuterUserQuery();
        outerUserQuery.setBindType(type);
        outerUserQuery.setOpenId(openId);
        OuterUser outerUser = outerUserService.selectOne(outerUserQuery);
        if (outerUser == null) {
            return null;
        }
        Long userId = outerUser.getUserId();
        return this.findById(userId);
    }

    @Override
    public User saveOuterUser(Integer type, String openId, String nickname, String avatar) {
        User user = new User();
        user.setAccountNo(type + "_" + openId);
        user.setAvatar(avatar);
        user.setNickname(nickname);
        user.setPwd(new Date().toString());
        user.setMobile("1234567890");
        user.setType(UserType.member.getCode());
        user.setStatus(UserStatus.pass.getCode());
        user.setLevel(1);
        user.setBindStatus(UserBindStatus.notBind.getCode());
        this.register(user);

        //save outer user
        OuterUser outerUser = new OuterUser();
        outerUser.setOpenId(openId);
        outerUser.setBindType(type);
        outerUser.setAvatar(avatar);
        outerUser.setNickname(nickname);
        outerUser.setStatus(UserStatus.pass.getCode());
        outerUser.setUserId(user.getId());
        this.outerUserService.insert(outerUser);
        return user;
    }

    @Override
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }


    @Override
    public AuthUser getAuthUser() {
        User user = getCurrentUser();
        if(user != null){
            AuthUser authUser = new AuthUser();
            authUser.setUserId(user.getId());
            authUser.setMobile(user.getMobile());
            authUser.setUserName(user.getUserName());
            authUser.setBindStatus(user.getBindStatus());
            return authUser;
        }
        return null;
    }
}
