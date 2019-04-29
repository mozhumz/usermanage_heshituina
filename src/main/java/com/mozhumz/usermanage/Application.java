package com.mozhumz.usermanage;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@MapperScan("com.mozhumz.usermanage.mapper")
@EnableRedisHttpSession
public class Application {

	public static void main(String[] args)  {
		SpringApplication.run(Application.class, args);
	}

}
