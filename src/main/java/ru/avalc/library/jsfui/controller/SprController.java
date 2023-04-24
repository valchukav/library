package ru.avalc.library.jsfui.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.ManagedBean;

/**
 * @author Alexei Valchuk, 24.04.2023, email: a.valchukav@gmail.com
 */

@ManagedBean
@SessionScope
@Component
@Getter
@Setter
@Log
public class SprController {

    private final AuthorController authorController;
    private final GenreController genreController;
    private final PublisherController publisherController;

    private String listId;
    private String searchText;
    private String selectedTab;

    @Autowired
    public SprController(AuthorController authorController, GenreController genreController, PublisherController publisherController) {
        this.authorController = authorController;
        this.genreController = genreController;
        this.publisherController = publisherController;

        this.listId = "tabView:authorForm:authorList";
        this.selectedTab = "tabAuthors";
    }

    public void search() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update(listId);
    }

    public void onTabChange(TabChangeEvent event) {
        searchText = null;
        selectedTab = event.getTab().getId();

        switch (selectedTab) {
            case "tabAuthors":
                listId = "tabView:authorForm:authorList";
                break;

            case "tabGenres":
                listId = "tabView:genreForm:genreList";
                break;

            case "tabPublishers":
                listId = "tabView:publisherForm:publisherList";
                break;
        }

        RequestContext.getCurrentInstance().update("searchForm:searchInput");
    }

    public void addAction() {
        switch (selectedTab) {
            case "tabAuthors":
                authorController.addAction();
                RequestContext.getCurrentInstance().update("tabView:dialogAuthor");
                break;

            case "tabGenres":
                genreController.addAction();
                RequestContext.getCurrentInstance().update("tabView:dialogGenre");
                break;

            case "tabPublishers":
                publisherController.addAction();
                RequestContext.getCurrentInstance().update("tabView:dialogPublisher");
                break;
        }
    }
}
