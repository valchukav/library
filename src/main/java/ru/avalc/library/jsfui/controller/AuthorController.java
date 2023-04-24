package ru.avalc.library.jsfui.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import ru.avalc.library.dao.AuthorDao;
import ru.avalc.library.domain.Author;
import ru.avalc.library.jsfui.controller.util.CommonSearch;
import ru.avalc.library.jsfui.model.LazyDataTable;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Alexei Valchuk, 19.04.2023, email: a.valchukav@gmail.com
 */

@ManagedBean
@SessionScope
@Component
@Getter
@Setter
@Log
public class AuthorController extends AbstractController<Author> {

    private int rowsCount = 20;
    private int first;

    private final AuthorDao authorDao;
    private final SprController sprController;

    private Author selectedAuthor;
    private LazyDataTable<Author> lazyModel;

    private Page<Author> authorPages;

    @Autowired
    public AuthorController(AuthorDao authorDao, SprController sprController) {
        this.authorDao = authorDao;
        this.sprController = sprController;
    }

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable<>(this);
    }

    public void save() {
        authorDao.save(selectedAuthor);
        RequestContext.getCurrentInstance().execute("PF('dialogAuthor').hide()");
    }

    @Override
    public Page<Author> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return CommonSearch.search(sprController, "fio", authorPages, authorDao, pageNumber, pageSize, sortField, sortDirection);
    }

    @Override
    public void addAction() {
        selectedAuthor = new Author();
        showEditDialog();
    }

    @Override
    public void editAction() {
        showEditDialog();
    }

    @Override
    public void deleteAction() {
        authorDao.delete(selectedAuthor);
    }

    private void showEditDialog() {
        RequestContext.getCurrentInstance().execute("PF('dialogAuthor').show()");
    }

    public List<Author> find(String fio) {
        return authorDao.search(fio);
    }
}
