package com.intuit.cg.backendtechassessment.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ApplicationConfig {

   @Bean
   public ModelMapper modelMapper() {
      return new ModelMapper();     
   }
   
   @Bean
   public MessageSource messageSource() {
       
	   final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        
       messageSource.setBasename("classpath:messages");
       messageSource.setDefaultEncoding("UTF-8");

       return messageSource;
   }
}