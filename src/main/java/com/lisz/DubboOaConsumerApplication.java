package com.lisz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//这里为了不让springboot去找data source的url
public class DubboOaConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboOaConsumerApplication.class, args);
	}

}
