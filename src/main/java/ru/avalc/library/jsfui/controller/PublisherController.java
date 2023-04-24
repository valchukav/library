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
import ru.avalc.library.dao.PublisherDao;
import ru.avalc.library.domain.Publisher;
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
public class PublisherController extends AbstractController<Publisher> {

    private int rowsCount = 20;
    private int first;

    private final PublisherDao publisherDao;
    private final SprController sprController;

    private Page<Publisher> publisherPages;
    private Publisher selectedPublisher;
    private LazyDataTable<Publisher> lazyModel;

    @Autowired
    public PublisherController(PublisherDao publisherDao, SprController sprController) {
        this.publisherDao = publisherDao;
        this.sprController = sprController;
    }

    @PostConstruct
    public void init() {
        lazyModel = new LazyDataTable<>(this);
    }

    public void save() {
        publisherDao.save(selectedPublisher);
        RequestContext.getCurrentInstance().execute("PF('dialogPublisher').hide()");
    }

    @Override
    public Page<Publisher> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        return CommonSearch.search(sprController, "name", publisherPages, publisherDao, pageNumber, pageSize, sortField, sortDirection);
    }

    @Override
    public void addAction() {
        selectedPublisher = new Publisher();
        showEditDialog();
    }

    @Override
    public void editAction() {
        showEditDialog();
    }

    @Override
    public void deleteAction() {
        publisherDao.delete(selectedPublisher);
    }

    private void showEditDialog() {
        RequestContext.getCurrentInstance().execute("PF('dialogPublisher').show()");
    }

    public List<Publisher> find(String name) {
        return publisherDao.search(name);
    }
}
