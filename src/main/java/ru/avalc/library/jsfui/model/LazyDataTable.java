package ru.avalc.library.jsfui.model;

import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.avalc.library.jsfui.controller.AbstractController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public List<T> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
        int pageNumber = first / pageSize;
        Sort.Direction sortDirection = Sort.Direction.ASC;

        if (sortBy.values().stream().anyMatch(sortMeta -> sortMeta.getOrder().equals(SortOrder.DESCENDING)))
            sortDirection = Sort.Direction.DESC;

        Optional<SortMeta> sortMeta = sortBy.values().stream().findFirst();
        String field = null;
        if (sortMeta.isPresent()) {
            field = sortMeta.get().getField();
        }

        Page<T> search = abstractController.search(pageNumber, pageSize, field, sortDirection);
        this.setRowCount((int) search.getTotalElements());
        return search.getContent();
    }
}
