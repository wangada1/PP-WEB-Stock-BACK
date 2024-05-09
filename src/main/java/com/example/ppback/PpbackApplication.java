package com.example.ppback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.ppback")
public class PpbackApplication{

	public static void main(String[] args) {
		SpringApplication.run(PpbackApplication.class, args);
		 printStartupMessage();

	}

	  @Bean
	    public MultipartConfigElement multipartConfigElement() {
	        MultipartConfigFactory factory = new MultipartConfigFactory();
	        //允许上传的文件最大值
//	        factory.setMaxFileSize(DataSize.parse(maxFileSize)); //KB,MB
//	        /// 设置总上传数据总大小
//	        factory.setMaxRequestSize(DataSize.parse(maxFileSize));
	        return factory.createMultipartConfig();
	    }
	  private static void printStartupMessage() {
	        System.out.println("服务已启动，请刷新网页,或打开 localhost:8081 ");
	    }


}
