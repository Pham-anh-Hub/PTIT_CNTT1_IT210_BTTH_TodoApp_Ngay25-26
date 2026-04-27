package todo_app.ptit_cntt1_it210_personaltodoapp.config;


import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.time.Duration;
import java.util.Locale;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {


        // LocaleResolver - set Cookie/Session ngôn ngữ mà đã lưu trữ
        @Bean
        public LocaleResolver localeResolver(){
            CookieLocaleResolver resolver = new CookieLocaleResolver();
            resolver.setCookieMaxAge(Duration.ofDays(30));
            resolver.setDefaultLocale(new Locale("vi"));
            return resolver;
        }
        // LocaleChangeInterceptor - Bắt sự kiện khi thay đổi thì gọi ra LocaleResolver cập nhật ngôn ngữ
        @Bean
        public LocaleChangeInterceptor localeChangeInterceptor(){
            LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
            lci.setParamName("lang");
            return lci;
        }
        // Theem vào trình quản lý của Interceptor
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(localeChangeInterceptor());
        }
}
