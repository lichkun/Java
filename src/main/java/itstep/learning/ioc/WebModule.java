package itstep.learning.ioc;

import com.google.inject.servlet.ServletModule;
import itstep.learning.filters.*;
import itstep.learning.servlets.HomeServlet;
import itstep.learning.servlets.WebXmlServlet;

public class WebModule extends ServletModule {
    @Override
    protected void configureServlets() {
        //По наличиее IoC регистрация  фильтров и сервлетов происходит с ее
        // Не забыть !! убрать регистрацию фильтров с web.xml
        //И поставить @Singleton к классам в фильтрах
        filter("/*").through(CharsetFilter.class);
        filter("/*").through(SecurityFilter.class);
        //также само с сервлетами
        serve("/").with(HomeServlet.class);
        serve("/web-xml").with(WebXmlServlet.class);
    }
}
