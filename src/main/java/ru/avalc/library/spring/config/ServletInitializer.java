package ru.avalc.library.spring.config;

import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import ru.avalc.library.jsfui.servlets.PdfContent;

import javax.faces.webapp.FacesServlet;
import javax.servlet.MultipartConfigElement;
import javax.servlet.annotation.MultipartConfig;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.avalc.library.spring", "ru.avalc.library.dao", "ru.avalc.library.jsfui"})
@EntityScan(basePackages = {"ru.avalc.library.domain"})
@EnableJpaRepositories(basePackages = {"ru.avalc.library.repository"})
@EnableAspectJAutoProxy
public class ServletInitializer extends SpringBootServletInitializer {

    private static final String LOCATION = "C:\\Users\\avalc\\Desktop\\Java\\javabegin\\library\\temp";
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 25;
    private static final long MAX_REQUEST_SIZE = 1024 * 1024 * 30;
    private static final int FILE_SIZE_THRESHOLD = 1024 * 1024 * 5;

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return servletContext -> {
            servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
            servletContext.setInitParameter("primefaces.THEME", "bootstrap");
            servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
            servletContext.setInitParameter("primefaces.UPLOADER", "commons");
            servletContext.setInitParameter("primefaces.UPLOADER", "commons");
        };
    }

    @Bean
    public FilterRegistrationBean<FileUploadFilter> fileUploadFilterRegistrationBean() {
        FilterRegistrationBean<FileUploadFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.addUrlPatterns("*.xhtml", "/javax.faces.resource/*");
        registrationBean.setFilter(new FileUploadFilter());
        return registrationBean;
    }

    @Bean
    public MultipartConfigElement getMultipartConfigElement() {
        return new MultipartConfigElement(LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
    }

    @Bean
    public ServletRegistrationBean<FacesServlet> servletRegistrationBean(MultipartConfigElement multipartConfigElement) {
        FacesServlet servlet = new FacesServlet();
        ServletRegistrationBean<FacesServlet> servletRegistrationBean = new ServletRegistrationBean<>(servlet, "*.xhtml", "/javax.faces.resource/*");
        servletRegistrationBean.setMultipartConfig(multipartConfigElement);
        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean<PdfContent> pdfServletRegistration() {
        ServletRegistrationBean<PdfContent> pdfServlet = new ServletRegistrationBean<>(new PdfContent(), "/PdfContent");
        pdfServlet.setName("PdfContent");
        return pdfServlet;
    }
}
