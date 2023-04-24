package ru.avalc.library.dao.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.avalc.library.spring.config.ServletInitializer;

/**
 * @author Alexei Valchuk, 22.04.2023, email: a.valchukav@gmail.com
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {ServletInitializer.class})
@Sql(scripts = "classpath:data.sql")
public abstract class AbstractServiceTest<T> {

    protected T service;

    public AbstractServiceTest(T service) {
        this.service = service;
    }

    @Test
    abstract void get();
}
