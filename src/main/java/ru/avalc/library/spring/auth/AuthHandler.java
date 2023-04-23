package ru.avalc.library.spring.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Alexei Valchuk, 23.04.2023, email: a.valchukav@gmail.com
 */

@Component
public class AuthHandler implements AuthenticationFailureHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("loginFailed", "login failed");
        }

        if (response.isCommitted()) {
            return;
        }

        redirectStrategy.sendRedirect(request, response, "/index.xhtml");
    }
}
