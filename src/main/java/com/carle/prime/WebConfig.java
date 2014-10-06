package com.carle.prime;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

  /**
    * Override Spring Boot configuration to return JSON rather than XML as the default
    */
  @Override
  public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
    configurer.favorPathExtension(true).
      ignoreAcceptHeader(true).
      defaultContentType(MediaType.APPLICATION_JSON).
      mediaType("xml", MediaType.APPLICATION_XML).
      mediaType("json", MediaType.APPLICATION_JSON);
    }
}
