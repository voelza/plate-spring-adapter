package com.voelza.plate.spring.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class PlateSpringFileLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(PlateSpringFileLoader.class);

    private PlateSpringFileLoader() {
        // hide
    }

    public static String loadViewCode(String viewName) {
        viewName = viewName.replace("\\", "/");
        if (viewName.endsWith(".html")) {
            viewName = viewName.substring(0, viewName.length() - ".html".length());
        }
        if (viewName.startsWith(".")) {
            viewName = viewName.substring(1);
        }
        if (viewName.startsWith("@")) {
            viewName = viewName.substring(1);
        }
        if (viewName.startsWith("/")) {
            viewName = viewName.substring(1);
        }
        return loadFile("classpath*:/templates/" + viewName + ".html");
    }

    public static String loadStaticViewCode(final String viewName) {
        return loadFile("classpath*:/static/" + viewName);
    }

    private static String loadFile(final String path) {
        final ClassLoader cl = PlateSpringFileLoader.class.getClassLoader();
        final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        try {
            final Resource[] resources = resolver.getResources(path);
            for (final Resource resource : resources) {
                final InputStream inputStream = resource.getInputStream();
                final StringBuilder textBuilder = new StringBuilder();
                try (Reader reader =
                        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                    int c;
                    while ((c = reader.read()) != -1) {
                        textBuilder.append((char) c);
                    }
                }
                return textBuilder.toString();
            }
            LOGGER.error("Could not read view {}.", path);
            throw new RuntimeException("File '" + path + "' not found");
        } catch (final IOException e) {
            LOGGER.error("Could not read view {}.", path, e);
            throw new RuntimeException(e);
        }
    }
}
