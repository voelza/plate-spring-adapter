package com.voelza.plate.spring.adapter;

import com.voelza.plate.Plate;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@RestController
public class PlateSupplyController {

    @RequestMapping(
                    method = RequestMethod.GET,
                    path = "/plate/js/{js}",
                    produces = "text/javascript")
    public ResponseEntity<String> getTemplateJS(@PathVariable("js") final String jsFileName, final Locale locale) {
        return Plate.getJavaScript(jsFileName, locale)
                .map(this::serve)
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(
                    method = RequestMethod.GET,
                    path = "/plate/css/{css}",
                    produces = "text/css")
    public ResponseEntity<String> getTemplateCss(@PathVariable("css") final String cssFileName, final Locale locale) {
        return Plate.getCSS(cssFileName, locale)
                .map(this::serve)
                .orElse(ResponseEntity.notFound().build());
    }

    private ResponseEntity<String> serve(final String code) {
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setCacheControl(CacheControl.maxAge(31536000, TimeUnit.SECONDS));
        return new ResponseEntity<>(code, responseHeaders, HttpStatus.OK);
    }

}
