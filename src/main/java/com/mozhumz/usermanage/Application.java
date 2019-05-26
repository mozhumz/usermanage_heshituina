package com.mozhumz.usermanage;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.mozhumz.usermanage.mapper")
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 7200)
@EnableAsync
@EnableTransactionManagement
@EnableEurekaClient
@EnableFeignClients
public class Application {

	public static void main(String[] args)  {
		SpringApplication.run(Application.class, args);
	}

}
