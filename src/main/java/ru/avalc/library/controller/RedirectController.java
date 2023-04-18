package ru.avalc.library.controller;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexei Valchuk, 12.04.2023, email: a.valchukav@gmail.com
 */

@Controller
@Log
public class RedirectController {

    @GetMapping(value = "")
    public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        return "redirect:" + request.getRequestURL().append("pages/books.xhtml").toString();
    }
}
