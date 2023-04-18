package ru.avalc.library.jsfui.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.avalc.library.dao.BookDao;
import ru.avalc.library.domain.Book;
import ru.avalc.library.jsfui.enums.SearchType;
import ru.avalc.library.jsfui.model.LazyDataTable;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Alexei Valchuk, 18.04.2023, email: a.valchukav@gmail.com
 */

@ManagedBean
@SessionScope
@Component
@Getter
@Setter
@Log
public class BookController extends AbstractController<Book> {

    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int TOP_BOOKS_LIMIT = 5;

    private int rowsCount = DEFAULT_PAGE_SIZE;
    private SearchType searchType;
    private final BookDao bookDao;
    private LazyDataTable<Book> lazyModel;
    private Page<Book> bookPages;

    public BookController(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable<>(this);
    }

    @Override
    public Page<Book> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        if (sortField == null) sortField = "name";

        if (searchType == null) bookPages = bookDao.getAll(pageNumber, pageSize, sortField, sortDirection);
        else {
            switch (searchType) {
                case SEARCH_GENRE: break;
                case SEARCH_TEXT: break;
                case ALL: bookPages = bookDao.getAll(pageNumber, pageSize, sortField, sortDirection);
            }
        }
        return bookPages;
    }

    public List<Book> getTopBooks() {
        return bookDao.findTopBooks(TOP_BOOKS_LIMIT);
    }
}
