package com.intelijake.imagelibrarybackend.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.intelijake.imagelibrarybackend.mapper")
public class MybatisPlusConfig {

    /**
     * Add pagination plugin
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // If configuring multiple plugins, ensure pagination is added last
        // For multi-data sources, you can omit the specific DbType. Otherwise, it's recommended to specify it.
        return interceptor;
    }
}