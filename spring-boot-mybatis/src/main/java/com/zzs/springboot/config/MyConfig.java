package com.zzs.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.sql.DataSource;

@Configuration
@ImportResource(locations= {"classpath:spring.xml"})
public class MyConfig {



}
