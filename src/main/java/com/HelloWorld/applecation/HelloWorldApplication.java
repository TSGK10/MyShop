package com.HelloWorld.applecation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.HelloWorld")
@MapperScan("com.HelloWorld.mapper")
public class HelloWorldApplication {
	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApplication.class, args);
	}
}
