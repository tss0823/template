package ${packageName}.service.inter;

import ${packageName}.model.domain.User;
import ${packageName}.model.query.UserQuery;
import ${packageName}.model.vo.UserVo;
import com.yuntao.platform.common.web.Pagination;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User findByAccountAndType(String accountNo, Integer type);

    Pagination<UserVo> selectPage(UserQuery query);

    Pagination<MemberVo> selectMemberPage(UserQuery query);

    List<UserVo> selectList(UserQuery query);

    List<UserVo> selectByType(Integer type);

    User login(String accountNo, Integer type, String pwd);

    void logout(Long userId);

    User getCurrentUser();

    User register(User user);

    int updateById(User user);

    User updateByAccountAndType(User user);

    User selectOne(UserQuery query);

    User selectByOuter(Integer type,String openId);

    User saveOuterUser(Integer type,String openId,String nickname,String avatar);

    void deleteById(Long id);
}
