package ru.avalc.library.jsfui.controller.util;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Alexei Valchuk, 22.04.2023, email: a.valchukav@gmail.com
 */

@Component
public class Contents {

    @SneakyThrows
    public InputStream get(String contentPath) {
        File file = new File("C:/Users/avalc/Desktop/Java/javabegin/library/src/main/webapp/resources/" + contentPath);
        return file.exists() ? new FileInputStream(file) : null;
    }
}
