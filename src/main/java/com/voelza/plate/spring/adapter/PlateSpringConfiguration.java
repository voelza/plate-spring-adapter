package com.voelza.plate.spring.adapter;

import com.voelza.plate.Plate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableConfigurationProperties(PlateViewResolver.class)
public class PlateSpringConfiguration implements WebMvcConfigurer {

    private final PlateViewResolver plateViewResolver;

    public PlateSpringConfiguration(final PlateViewResolver templateViewResolver) {
        Plate.setVersion(this.getClass().getPackage().getImplementationVersion());
        Plate.setCustomFileLoader(PlateSpringFileLoader::loadViewCode);
        Plate.setTranslations(SpringI18nSupplier::getMessageProperties);
        this.plateViewResolver = templateViewResolver;
    }

    @Override
    public void configureViewResolvers(final ViewResolverRegistry registry) {
        registry.viewResolver(plateViewResolver);
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
    }

    @Bean
    public PlateSupplyController TemplateEngineController() {
        return new PlateSupplyController();
    }
}
