package ru.avalc.library.jsfui.locale;


import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.avalc.library.jsfui.util.CookieHelper;

import javax.annotation.ManagedBean;
import java.util.Locale;

/**
 * @author Alexei Valchuk, 24.04.2023, email: a.valchukav@gmail.com
 */

@ManagedBean
@Component
@SessionScope
@Getter
public class LocaleChanger {

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
