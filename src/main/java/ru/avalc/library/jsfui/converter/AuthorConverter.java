package ru.avalc.library.jsfui.converter;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.avalc.library.dao.AuthorDao;
import ru.avalc.library.domain.Author;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@Component
public class AuthorConverter implements Converter<Author> {

    private final AuthorDao authorDao;

    @Autowired
    public AuthorConverter(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Author getAsObject(FacesContext context, UIComponent component, String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        return authorDao.get(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Author author) {
        if (author == null) {
            return null;
        }
        return author.getId().toString();
    }
}
