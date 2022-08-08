package com.voelza.plate.spring.adapter;

import com.voelza.plate.Model;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

public class PlateStreamView extends PlateView {

    PlateStreamView(final ApplicationContext applicationContext,
                    final String viewName,
                    final Locale locale) {
        super(applicationContext, viewName, locale);
    }

    @Override
    protected void printView(final HttpServletResponse response, final Model viewModel) throws IOException {
        final PrintWriter responseWriter = response.getWriter();
        view.stream(responseWriter, viewModel);
        responseWriter.close();
    }
}
