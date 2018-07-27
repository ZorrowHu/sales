package com.platform.sales.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * Created by 18274 on 2017/8/9.
 */
@Configuration
public class MyWebAppConfig extends WebMvcConfigurerAdapter{
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                //registry.addResourceHandler("/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/goodsimg/");
                registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");
                registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
                super.addResourceHandlers(registry);
        }
}