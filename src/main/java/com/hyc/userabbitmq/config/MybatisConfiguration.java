package com.hyc.userabbitmq.config;

import com.hyc.userabbitmq.enums.AutoEnumTypeHandler;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "mybatis")
public class MybatisConfiguration {

    private String configLocation;

    @Bean
    public SqlSessionFactory sqlSessionFactory(
            DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);


        // 设置配置文件及mapper文件地址
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setConfigLocation(resolver.getResource(configLocation));


        SqlSessionFactory sqlSessionFactory = factory.getObject();


        // 取得类型转换注册器
        TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
        // 注册默认枚举转换器，这样就不需要再mybatis里面去逐个声明 <typeHandlers></typeHandlers>
        typeHandlerRegistry.setDefaultEnumTypeHandler(AutoEnumTypeHandler.class);

        return sqlSessionFactory;
    }

    public String getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(String configLocation) {
        this.configLocation = configLocation;
    }
}
