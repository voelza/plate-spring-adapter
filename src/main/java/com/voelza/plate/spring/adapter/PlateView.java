package com.voelza.plate.spring.adapter;

import com.voelza.plate.Model;
import com.voelza.plate.Plate;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

public class PlateView implements View {
    
    private final ApplicationContext applicationContext;
    protected final com.voelza.plate.view.View view;

    PlateView(final ApplicationContext applicationContext, final String viewName, final Locale locale) {
        this.applicationContext = applicationContext;
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
        for (String beanName : this.applicationContext.getBeanNamesForAnnotation(PlateBean.class)) {
            final Object bean = this.applicationContext.getBean(beanName);
            viewModel.add(beanName, bean);
        }

        printView(response, viewModel);
    }

    protected void printView(final HttpServletResponse response, final Model viewModel) throws IOException {
        final PrintWriter responseWriter = response.getWriter();
        responseWriter.write(view.render(viewModel));
        responseWriter.close();
    }
}
