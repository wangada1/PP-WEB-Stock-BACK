package com.example.ppback;

import java.awt.print.Printable;
import java.io.Console;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.example.ppback.model.DataEntry;
import com.example.ppback.repository.DataEntryRepository;

import jakarta.servlet.MultipartConfigElement;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

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

	
//	@Bean
//	CommandLineRunner runner(DataEntryRepository dataEntryRepository){
//		System.out.println("hello");
//		return args->{
//			List<Integer> list = new ArrayList<>();
//			list.add(1);
//			list.add(2);
//			DataEntry dEntry = new DataEntry("1", 
//					"1234", "1234", "CAA",list,list,list, "2023/2");
//			dataEntryRepository.save(dEntry);
//
//			
//	
//		
//		};
//	}
}
