package com.drajer.ecrapp;

import com.drajer.ecrapp.security.AuthorizationService;
import com.drajer.ecrapp.security.RequestMDCFilter;
import com.drajer.ecrapp.security.SampleAuthorizationServiceImpl;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@EnableTransactionManagement
@ServletComponentScan
public class EcrApp extends SpringBootServletInitializer {

  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(EcrApp.class);
  }

  public static void main(String[] args) {
    TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
    SpringApplication.run(EcrApp.class, args);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public AuthorizationService authorizationService() {
    return new SampleAuthorizationServiceImpl();
  }

  @Bean
  public FilterRegistrationBean<RequestMDCFilter> loggingFilter() {
    FilterRegistrationBean<RequestMDCFilter> registrationBean = new FilterRegistrationBean<>();

    registrationBean.setFilter(new RequestMDCFilter());
    registrationBean.addUrlPatterns("/api/*");

    return registrationBean;
  }
}
