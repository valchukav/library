package ru.avalc.library.jsfui.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alexei Valchuk, 24.04.2023, email: a.valchukav@gmail.com
 */

public class CookieHelper {

    public static final String COOKIE_LANG = "spring-library-lang";

    public static void setCookie(String name, String value, int expiry) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        Cookie cookie = null;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (Cookie userCookie : userCookies) {
                if (userCookie.getName().equals(name)) {
                    cookie = userCookie;
                    break;
                }
            }
        }

        if (cookie != null) {
            cookie.setValue(value);
        } else {
            cookie = new Cookie(name, value);
        }

        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(expiry);

        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        response.addCookie(cookie);
    }

    public static Cookie getCookie(String name) {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpServletRequest request;
        try {
            request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
        } catch (NullPointerException e) {
            return null;
        }
        Cookie cookie;

        Cookie[] userCookies = request.getCookies();
        if (userCookies != null && userCookies.length > 0) {
            for (Cookie userCookie : userCookies) {
                if (userCookie.getName().equals(name)) {
                    cookie = userCookie;
                    return cookie;
                }
            }
        }
        return null;
    }
}
