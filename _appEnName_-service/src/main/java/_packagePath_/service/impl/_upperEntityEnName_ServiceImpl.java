/*
 * 
 * 
 * 
 * 
 */

package ${packageName}.service.impl;

import ${packageName}.model.domain.${upperEntityEnName};
import ${packageName}.model.query.${upperEntityEnName}Query;
import ${packageName}.model.vo.${upperEntityEnName}Vo;
import ${packageName}.dal.mapper.${upperEntityEnName}Mapper;
import ${packageName}.service.inter.${upperEntityEnName}Service;
import com.yuntao.platform.common.utils.BeanUtils;
import com.yuntao.platform.common.web.Pagination;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("${entityEnName}")
public class ${upperEntityEnName}ServiceImpl extends AbstService implements ${upperEntityEnName}Service {


    @Autowired
    private ${upperEntityEnName}Mapper ${entityEnName}Mapper;

    @Override
    public List<${upperEntityEnName}> selectList(${upperEntityEnName}Query query) {
        Map<String, Object> queryMap = BeanUtils.beanToMap(query);
        return ${entityEnName}Mapper.selectList(queryMap);
    }

    @Override
    public ${upperEntityEnName} selectOne(${upperEntityEnName}Query query) {
        Map<String, Object> queryMap = BeanUtils.beanToMap(query);
        List<${upperEntityEnName}> ${entityEnName}s = ${entityEnName}Mapper.selectList(queryMap);
        if (CollectionUtils.isNotEmpty(${entityEnName}s)) {
            return ${entityEnName}s.get(0);
        }
        return null;
    }

    @Override
    public Pagination<${upperEntityEnName}Vo> selectPage(${upperEntityEnName}Query query) {
        Map<String, Object> queryMap = BeanUtils.beanToMap(query);
        long totalCount = ${entityEnName}Mapper.selectListCount(queryMap);
        Pagination<${upperEntityEnName}Vo> pagination = new Pagination<>(totalCount,
                query.getPageSize(), query.getPageNum());
        if (totalCount == 0) {
            return pagination;
        }
        queryMap.put("pagination", pagination);
        List<${upperEntityEnName}> dataList = ${entityEnName}Mapper.selectList(queryMap);
        List<${upperEntityEnName}Vo> newDataList = new ArrayList<>(dataList.size());
        pagination.setDataList(newDataList);
        for (${upperEntityEnName} ${entityEnName} : dataList) {
            ${upperEntityEnName}Vo ${entityEnName}Vo = BeanUtils.beanCopy(${entityEnName}, ${upperEntityEnName}Vo.class);
            newDataList.add(${entityEnName}Vo);
        }
        return pagination;
    }

    @Override
    public ${upperEntityEnName} findById(Long id) {
        return ${entityEnName}Mapper.findById(id);
    }


    @Override
    public int insert(${upperEntityEnName} ${entityEnName}) {
        return ${entityEnName}Mapper.insert(${entityEnName});
    }

    @Override
    public int updateById(${upperEntityEnName} ${entityEnName}) {
        return ${entityEnName}Mapper.updateById(${entityEnName});
    }

    @Override
    public int deleteById(Long id) {
        return ${entityEnName}Mapper.deleteById(id);
    }


}