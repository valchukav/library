package ru.avalc.library.jsfui.model;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.avalc.library.jsfui.controller.AbstractController;

import java.util.List;
import java.util.Map;

/**
 * @author Alexei Valchuk, 18.04.2023, email: a.valchukav@gmail.com
 */

@Getter
@Setter
public class LazyDataTable<T> extends LazyDataModel<T> {

    private AbstractController<T> abstractController;

    public LazyDataTable(AbstractController<T> controller) {
        this.abstractController = controller;
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        int pageNumber = first / pageSize;//

        Sort.Direction sortDirection = Sort.Direction.ASC;

        if (sortOrder != null) {
            if (sortOrder == SortOrder.DESCENDING) {
                sortDirection = Sort.Direction.DESC;
            }
        }

        Page<T> searchResult = abstractController.search(pageNumber, pageSize, sortField, sortDirection);
        this.setRowCount((int) searchResult.getTotalElements());
        return searchResult.getContent();
    }
}
