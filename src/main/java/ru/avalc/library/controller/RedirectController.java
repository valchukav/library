package ru.avalc.library.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.avalc.library.domain.Author;
import ru.avalc.library.repository.AuthorRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author Alexei Valchuk, 12.04.2023, email: a.valchukav@gmail.com
 */

@Controller
@Log
public class RedirectController {

    private final AuthorRepository authorRepository;

    @Autowired
    public RedirectController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping(value = "")
    public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        List<Author> authorList = authorRepository.findAll();
        return "ok";
    }
}
