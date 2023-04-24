package ru.avalc.library.jsfui.converter;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.avalc.library.dao.GenreDao;
import ru.avalc.library.domain.Genre;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@Component
public class GenreConverter implements Converter<Genre> {

    private final GenreDao genreDao;

    @Autowired
    public GenreConverter(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public Genre getAsObject(FacesContext context, UIComponent component, String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        return genreDao.get(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Genre genre) {
        if (genre == null) {
            return null;
        }
        return genre.getId().toString();
    }
}
