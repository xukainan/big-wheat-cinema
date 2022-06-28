package com.wheat.cinema.conf;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@MapperScan("com.wheat.cinema.dao")
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {

    private static final Long MAX_LIMIT = 10000L;

    @Autowired
    private DataSource dataSource;

    @Autowired
    MybatisPlusInterceptor mybatisPlusInterceptor;

    @Bean("sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Interceptor[] plugins = {mybatisPlusInterceptor};
        sqlSessionFactoryBean.setPlugins(plugins);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath" +
                    "*:com/wheat/cinema/dao/**/*Mapper.xml"));
        return sqlSessionFactoryBean.getObject();
    }



}
