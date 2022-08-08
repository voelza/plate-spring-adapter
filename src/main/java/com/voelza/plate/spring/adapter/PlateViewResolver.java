package com.voelza.plate.spring.adapter;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

import java.util.Locale;

@ConfigurationProperties
public class PlateViewResolver extends AbstractCachingViewResolver {

    private ApplicationContext applicationContext;

    @Override
    protected View loadView(final String viewName, final Locale locale) {
        if (viewName.startsWith("forward:")) {
            return new PlateStaticView(viewName.substring("forward:".length()));
        }

        if (viewName.startsWith("stream:")) {
            return new PlateStreamView(applicationContext, viewName.substring("stream:".length()), locale);
        }

        return new PlateView(applicationContext, viewName, locale);
    }

    @Override
    protected void initApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
