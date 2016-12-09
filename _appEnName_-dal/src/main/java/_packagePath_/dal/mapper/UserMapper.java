package ${packageName}.dal.mapper;

import ${packageName}.model.domain.User;

/**
 * 用户Mappper
 *
 * @author admin
 * @2016-08-03 22
 */
public interface UserMapper extends BaseMapper<User> {

    int updateByAccountAndType(User user);


}
