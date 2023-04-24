package ru.avalc.library.jsfui.controller.util;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.avalc.library.dao.Dao;
import ru.avalc.library.jsfui.controller.SprController;

/**
 * @author Alexei Valchuk, 24.04.2023, email: a.valchukav@gmail.com
 */

@Getter
@Setter
public class CommonSearch {

    public static <T> Page<T> search(SprController sprController, String defaultSortField, Page<T> pages, Dao<T> dao,
                                     int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        if (sortField == null) {
            sortField = defaultSortField;
        }

        if (Strings.isNullOrEmpty(sprController.getSearchText())) {
            pages = dao.getAll(pageNumber, pageSize, sortField, sortDirection);
        } else {
            pages = dao.search(pageNumber, pageSize, sortField, sortDirection, sprController.getSearchText());
        }

        return pages;
    }
}
