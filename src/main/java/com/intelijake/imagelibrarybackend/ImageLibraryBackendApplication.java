package com.intelijake.imagelibrarybackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@MapperScan("com.intelijake.imagelibrarybackend.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class ImageLibraryBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageLibraryBackendApplication.class, args);
    }

}
