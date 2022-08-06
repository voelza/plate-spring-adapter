package com.voelza.plate.spring.adapter;

import com.voelza.plate.Model;
import com.voelza.plate.Plate;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

public class PlateStreamView implements View {

    private final com.voelza.plate.view.View view;

    PlateStreamView(final String viewName, final Locale locale) {
        view = Plate.createView(viewName, locale);
    }

    @Override
    public String getContentType() {
        return "text/html;";
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        final Model viewModel = new Model();
        if (model != null) {
            model.forEach(viewModel::add);
        }

        final PrintWriter responseWriter = response.getWriter();
        view.stream(responseWriter, viewModel);
        responseWriter.close();
    }
}
