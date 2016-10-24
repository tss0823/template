package ${packageName}.dal.config;

import ${packageName}.dal.mapper.*;
import ${packageName}.dal.mybatis.IdTypeHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

/**
 * 等价于spring-dao.xml
 */
@Configuration
@ImportResource("classpath:applicationContext-dal.xml")
public class DalConfig {

    @Value("classpath:mybatis-config.xml")
    Resource mybatisMapperConfig;

    @Autowired
    DataSource dataSource;


    #foreach($entity in $!entityBoList)
    @Bean
    public $!{entity.upperEntityEnName}Mapper $!{entity.enName}Mapper() throws Exception {
        return newMapperFactoryBean($!{entity.upperEntityEnName}Mapper.class).getObject();
    }
    #end

    <T> MapperFactoryBean<T> newMapperFactoryBean(Class<T> clazz)
            throws Exception {
        MapperFactoryBean<T> b = new MapperFactoryBean<T>();
        b.setMapperInterface(clazz);
        b.setSqlSessionFactory(sqlSessionFactory());
        return b;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setConfigLocation(mybatisMapperConfig);
        fb.setDataSource(dataSource);
        fb.setTypeAliases(new Class<?>[]{IdTypeHandler.class});
        return fb.getObject();
    }
}
