package com.voelza.plate.spring.adapter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

import java.util.Locale;

@ConfigurationProperties
public class PlateViewResolver extends AbstractCachingViewResolver {

    @Override
    protected View loadView(final String viewName, final Locale locale)  {
        if (viewName.startsWith("forward:")) {
            return new PlateStaticView(viewName.substring("forward:".length()));
        }
        return new PlateView(viewName, locale);
    }

}
