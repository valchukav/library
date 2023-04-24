package ru.avalc.library.jsfui.converter;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.avalc.library.dao.PublisherDao;
import ru.avalc.library.domain.Publisher;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

@Component
public class PublisherConverter implements Converter<Publisher> {

    private final PublisherDao publisherDao;

    @Autowired
    public PublisherConverter(PublisherDao publisherDao) {
        this.publisherDao = publisherDao;
    }

    @Override
    public Publisher getAsObject(FacesContext context, UIComponent component, String value) {
        if (Strings.isNullOrEmpty(value)) {
            return null;
        }
        return publisherDao.get(Integer.parseInt(value));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Publisher publisher) {
        if (publisher == null) {
            return null;
        }
        return publisher.getId().toString();
    }
}
