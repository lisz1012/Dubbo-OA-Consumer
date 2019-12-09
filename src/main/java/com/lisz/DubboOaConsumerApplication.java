package com.lisz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//这里为了不让springboot去找data source的url
@ServletComponentScan(basePackages = "com.lisz.controller.listener")
public class DubboOaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboOaConsumerApplication.class, args);
	}

}
