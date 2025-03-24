package config;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Конфигурация для Spring MVC
    @Override
    protected Class<?>  [] getServletConfigClasses() {
        return new Class[]{ApplicationContextMVC.class};
    }


    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    @Override
    protected Class<?>  [] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        registerHiddenFieldFilter(servletContext);
    }

    private void registerHiddenFieldFilter(ServletContext context) {
        FilterRegistration.Dynamic filter = context.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter());
        filter.addMappingForUrlPatterns(null, true, "/*");
    }
}
