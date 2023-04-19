package ru.avalc.library.jsfui.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.avalc.library.dao.BookDao;
import ru.avalc.library.dao.GenreDao;
import ru.avalc.library.domain.Book;
import ru.avalc.library.jsfui.enums.SearchType;
import ru.avalc.library.jsfui.model.LazyDataTable;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.ResourceBundle;

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
    private SearchType searchType = SearchType.ALL;
    private String searchText;
    private final BookDao bookDao;
    private final GenreDao genreDao;
    private LazyDataTable<Book> lazyModel;
    private Page<Book> bookPages;
    private long selectedGenreId;

    @Autowired
    public BookController(BookDao bookDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.genreDao = genreDao;
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
                case SEARCH_GENRE:
                    bookPages = bookDao.findByGenre(pageNumber, pageSize, sortField, sortDirection, selectedGenreId);
                    break;
                case SEARCH_TEXT:
                    bookPages = bookDao.search(pageNumber, pageSize, sortField, sortDirection, searchText);
                    break;
                case ALL:
                    bookPages = bookDao.getAll(pageNumber, pageSize, sortField, sortDirection);
            }
        }
        return bookPages;
    }

    public List<Book> getTopBooks() {
        return bookDao.findTopBooks(TOP_BOOKS_LIMIT);
    }

    public void showBooksByGenre(long selectedGenreId) {
        searchType = SearchType.SEARCH_GENRE;
        this.selectedGenreId = selectedGenreId;
    }

    public void showAll() {
        searchType = SearchType.ALL;
    }

    public void searchAction() {
        searchType = SearchType.SEARCH_TEXT;
    }

    public String getSearchMessage() {
        ResourceBundle bundle = ResourceBundle.getBundle("library", FacesContext.getCurrentInstance().getViewRoot().getLocale());

        String message = null;
        switch (searchType) {
            case SEARCH_GENRE:
                message = bundle.getString("genre") + ": '" + genreDao.get(selectedGenreId) + "'";
                break;
            case SEARCH_TEXT:
                if (searchText == null || searchText.trim().length() == 0) {
                    return null;
                }

                message = bundle.getString("search") + ": '" + searchText + "'";
        }
        return message;
    }
}
