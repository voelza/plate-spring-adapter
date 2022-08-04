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
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

class SpringI18nSupplier {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringI18nSupplier.class);

    static Map<Locale, Map<String, String>> getMessageProperties() {
        final Map<Locale, Map<String, String>> translations = new HashMap<>();
        final ClassLoader cl = SpringI18nSupplier.class.getClassLoader();
        final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        try {
            final Resource[] messageProperties = resolver.getResources("classpath*:/message*.properties");
            for (final Resource resource : messageProperties) {

                Locale locale = null;
                final String fileName = resource.getFilename();
                if (fileName != null && fileName.contains("_")) {
                    locale = Locale.forLanguageTag(fileName.substring(fileName.indexOf("_") + 1, fileName.indexOf(".")));
                } else {
                    locale = Locale.ENGLISH;
                }

                final Map<String, String> messages = new HashMap<>();
                final InputStream inputStream = resource.getInputStream();
                try (BufferedReader reader =
                        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                    while (reader.ready()) {
                        final String line = reader.readLine();
                        final int separatorIndex = line.indexOf("=");
                        if (separatorIndex == -1) {
                            continue;
                        }
                        final String key = line.substring(0, separatorIndex - 1).trim();
                        final String value = line.substring(separatorIndex + 1).trim();
                        messages.put(key, value);
                    }
                }

                translations.put(locale, messages);

            }
        } catch (final IOException e) {
            LOGGER.error("Could not read message.properties", e);
        }

        return translations;
    }
}
