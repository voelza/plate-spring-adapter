package com.voelza.plate.spring.adapter;

import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

public class PlateStaticView implements View {

    private final String html;

    PlateStaticView(final String viewName) {
        html = PlateSpringFileLoader.loadStaticViewCode(viewName);
    }

    @Override
    public void render(final Map<String, ?> model, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        final PrintWriter responseWriter = response.getWriter();
        responseWriter.write(html);
    }

}
