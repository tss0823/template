/*
 * 
 * 
 * 
 * 
 */

package ${packageName}.service.inter;

import ${packageName}.model.domain.${upperEntityEnName};
import ${packageName}.model.query.${upperEntityEnName}Query;
import ${packageName}.model.vo.${upperEntityEnName}Vo;
import com.yuntao.platform.common.web.Pagination;

import java.util.List;


/**
 * ${entityCnName} 服务接口
 * @author ${author}
 *
 * @${time}
 */
public interface $!{upperEntityEnName}Service {
	
    /**
     * 查询列表
     *
     * @param query
     * @return
     */
    List<${upperEntityEnName}> selectList(${upperEntityEnName}Query query);

    /**
     * 查询对象
     *
     * @param query
     * @return
     */
    ${upperEntityEnName} selectOne(${upperEntityEnName}Query query);


    /**
     * 分页查询
     *
     * @param query
     * @return
     */
    Pagination<${upperEntityEnName}Vo> selectPage(${upperEntityEnName}Query query);

    /**
     * 根据id获得对象
     *
     * @param id
     * @return
     */
    ${upperEntityEnName} findById(Long id);

    /**
     * 新增
     *
     * @param ${entityEnName}
     * @return
     */
    int insert(${upperEntityEnName} ${entityEnName});

    /**
     * 根据id修改
     *
     * @param ${entityEnName}
     * @return
     */
    int updateById(${upperEntityEnName} ${entityEnName});

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    int deleteById(Long id);


    

}

