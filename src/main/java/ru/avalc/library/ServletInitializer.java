package ru.avalc.library;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"ru.avalc.library"})
@EnableAspectJAutoProxy
public class ServletInitializer extends SpringBootServletInitializer {

}
