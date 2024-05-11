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
		printStartupMessage();
		SpringApplication.run(PpbackApplication.class, args);
		 printendMessage();

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
	        System.out.println("Service starting, please wait for seconds.");
	        System.out.println("");
	    }
	  private static void printendMessage() {
	        System.out.println("Service has started, refresh the website or open localhost:8081");
	        System.out.println("");
	        System.out.println("Please do not close this window while in use");
	        System.out.println("");
	    }


	  


}
