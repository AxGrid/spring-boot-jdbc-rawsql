package com.axgrid.jdbc.rawsql.processors;

import com.axgrid.jdbc.rawsql.processors.dto.RawDAODescription;
import com.axgrid.jdbc.rawsql.processors.dto.RawObjectDescription;
import com.github.jknack.handlebars.*;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.IOException;

public class RawTemplate {

    private final TemplateLoader templateLoader = new ClassPathTemplateLoader();
    private final Handlebars handlebars;

    public RawTemplate() {
        templateLoader.setPrefix("/templates/");
        templateLoader.setSuffix(".tpl");
        handlebars = new Handlebars(templateLoader)
                .with(EscapingStrategy.NOOP)
        ;
        handlebars.registerHelper("capitalize", new Helper<String>() {
            @Override
            public Object apply(String s, Options options) throws IOException {
                return StringUtils.capitalize(s);
            }
        });
    }

    @NotNull
    public String generate(@NotNull RawDAODescription target) throws IOException {
        var template = handlebars.compile("RawDAO");
        return template.apply(target);
    }

    @NotNull
    public String generate(@NotNull RawObjectDescription description) throws IOException {

        var template = handlebars.compile("RawMapper");
        return template.apply(description);
    }

}
