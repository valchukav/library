package ru.avalc.library.jsfui.controller.util;

import lombok.SneakyThrows;
import org.omnifaces.cdi.GraphicImageBean;
import org.omnifaces.util.Faces;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author Alexei Valchuk, 21.04.2023, email: a.valchukav@gmail.com
 */

@Component
@GraphicImageBean
public class Images {

    @SneakyThrows
    public InputStream get(String imagePath) {
        File file = new File("C:/Users/avalc/Desktop/Java/javabegin/library/src/main/webapp/resources/" + imagePath);
        return file.exists() ? new FileInputStream(file) : Faces.getResourceAsStream("/resources/images/covers/no-cover.jpg");
    }
}
