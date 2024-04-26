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


}
