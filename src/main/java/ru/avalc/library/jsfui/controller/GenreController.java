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
import ru.avalc.library.dao.GenreDao;
import ru.avalc.library.domain.Genre;
import ru.avalc.library.jsfui.controller.util.CommonSearch;
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
public class GenreController extends AbstractController<Genre> {

    private int rowsCount = 20;
    private int first;

    private final GenreDao genreDao;
    private final SprController sprController;

    private Genre selectedGenre;
    private LazyDataTable<Genre> lazyModel;
    private Page<Genre> genrePages;

    @Autowired
    public GenreController(GenreDao genreDao, SprController sprController) {
        this.genreDao = genreDao;
        this.sprController = sprController;
    }

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable<>(this);
    }

    public List<Genre> find(String name) {
        return genreDao.search(name);
    }

    public void save() {
        genreDao.save(selectedGenre);
        RequestContext.getCurrentInstance().execute("PF('dialogGenre').hide()");
    }

    @Override
    public Page<Genre> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return CommonSearch.search(sprController, "name", genrePages, genreDao, pageNumber, pageSize, sortField, sortDirection);
    }

    @Override
    public void addAction() {
        selectedGenre = new Genre();
        showEditDialog();
    }

    @Override
    public void editAction() {
        showEditDialog();
    }

    @Override
    public void deleteAction() {
        genreDao.delete(selectedGenre);
    }

    public List<Genre> getAll() {
        return genreDao.getAll(Sort.by(new Sort.Order(Sort.Direction.ASC, "name")));
    }

    private void showEditDialog() {
        RequestContext.getCurrentInstance().execute("PF('dialogGenre').show()");
    }
}
