package ru.avalc.library.jsfui.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * @author Alexei Valchuk, 18.04.2023, email: a.valchukav@gmail.com
 */

public abstract class AbstractController <T> implements Serializable {

    public abstract Page<T> search(int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection);
}
