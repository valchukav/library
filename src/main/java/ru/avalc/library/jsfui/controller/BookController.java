package ru.avalc.library.jsfui.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.FileUploadEvent;
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
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;

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

    private final BookDao bookDao;
    private final GenreDao genreDao;

    private SearchType searchType = SearchType.ALL;
    private String searchText;
    private long selectedGenreId;

    private Book selectedBook;
    private byte[] uploadedContent;
    private String uploadedImagePath;

    private LazyDataTable<Book> lazyModel;
    private Page<Book> bookPages;


    @Autowired
    public BookController(BookDao bookDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.genreDao = genreDao;
    }

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable<>(this);
    }

    public void save() {
        if (uploadedImagePath != null) {
            String currentImagePath = selectedBook.getImagePath();
            selectedBook.setImagePath(uploadedImagePath);
            if (!currentImagePath.equals("images/covers/no-cover.jpg")) {
                File oldFile = new File("C:/Users/avalc/Desktop/Java/javabegin/library/src/main/webapp/resources/" + currentImagePath);
                oldFile.delete();
            }
        }

        if (uploadedContent != null) {
            selectedBook.setContent(uploadedContent);
        }

        bookDao.save(selectedBook);
        RequestContext.getCurrentInstance().execute("PF('dialogEditBook').hide()");
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

    public void onCloseDialog(CloseEvent event) {
        uploadedContent = null;
        uploadedImagePath = null;
    }

    @Override
    public void addAction() {

    }

    @SneakyThrows
    @Override
    public void editAction() {
        RequestContext.getCurrentInstance().execute("PF('dialogEditBook').show()");
    }

    @Override
    public void deleteAction() {

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

    public byte[] getContent(long id) {
        byte[] content;
        if (uploadedContent != null) {
            content = uploadedContent;
        } else {
            content = bookDao.getContent(id);
        }
        return content;
    }

    public void updateViewCount(long viewCount, long id) {
        bookDao.updateViewCount(++viewCount, id);
    }

    @SneakyThrows
    public void uploadImage(FileUploadEvent event) {
        if (event.getFile() != null) {
            String uploadedFileName = event.getFile().getFileName();
            uploadedImagePath = "images/covers/" + UUID.randomUUID() + uploadedFileName.substring(uploadedFileName.lastIndexOf("."));

            File fileOnServer = new File("C:/Users/avalc/Desktop/Java/javabegin/library/src/main/webapp/resources/" + uploadedImagePath);
            if (fileOnServer.createNewFile()) {
                try (FileOutputStream fileOutputStream = new FileOutputStream(fileOnServer)) {
                    event.getFile().getInputstream().transferTo(fileOutputStream);
                    fileOutputStream.flush();
                }
            }
        }
    }

    public void uploadContent(FileUploadEvent event) {
        if (event.getFile() != null) {
            uploadedContent = event.getFile().getContents();
        }
    }
}
