package com.sh.financial.auth.config;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  private List<Locale> locales = Arrays.asList(new Locale("en"));
  
  @Bean("localeResolver")
  LocaleResolver acceptHeaderLocaleResolver() {
    return new AcceptHeaderLocaleResolver() {
      @Override
      public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty() ? locales.get(0): Locale.lookup(Locale.LanguageRange.parse(headerLang), locales);
      }
    };
  }
  
  @Bean(name="globalMessageSource")
  ResourceBundleMessageSource resourceBundleMessageSource() {
    ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
    rs.setBasename("messages");
    rs.setDefaultEncoding("UTF-8");
    rs.setUseCodeAsDefaultMessage(true);
    rs.setAlwaysUseMessageFormat(true);
    
    return rs;
  }
}