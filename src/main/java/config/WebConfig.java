package config;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.jspecify.annotations.Nullable;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Конфигурация для Spring MVC
    @Override
    protected Class<?> @Nullable [] getServletConfigClasses() {
        return new Class[]{ApplicationContextMVC.class};
    }


    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    @Override
    protected Class<?> @Nullable [] getRootConfigClasses() {
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
