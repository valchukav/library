package ru.avalc.library.jsfui.locale;


import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.avalc.library.jsfui.util.CookieHelper;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Alexei Valchuk, 24.04.2023, email: a.valchukav@gmail.com
 */

@ManagedBean(eager = true)
@Component
@SessionScoped
@Getter
public class LocaleChanger implements Serializable {

    private Locale currentLocale = new Locale("ru");

    public LocaleChanger() {
        if (CookieHelper.getCookie(CookieHelper.COOKIE_LANG) == null) {
            return;
        }

        String cookieLang = CookieHelper.getCookie(CookieHelper.COOKIE_LANG).getValue();
        if (cookieLang != null) {
            currentLocale = new Locale(cookieLang);
        }
    }

    public void changeLocale(String localeCode) {
        currentLocale = new Locale(localeCode);
        CookieHelper.setCookie(CookieHelper.COOKIE_LANG, currentLocale.getLanguage(), 3600);
    }
}
