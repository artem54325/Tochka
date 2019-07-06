package com.example.test;

import com.example.test.controller.ApiController;
import com.example.test.service.ServiceHold;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
@ComponentScan({"com.example.test.service","com.example.test.controller"})
public class TestApplication {
	private static final Logger logger = LoggerFactory.getLogger(TestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}
	/*
	* Тестовое задание выполнил Артем Шеломенцев
	* Выполнил поставленные технические требования. С осуществлением 3(4) api
	* */
}