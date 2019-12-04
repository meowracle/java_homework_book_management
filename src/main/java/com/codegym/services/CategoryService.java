package com.codegym.services;

import com.codegym.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<Category> findALl(Pageable pageable);

    Category findById(Long id);

    void save (Category category);

    void remove (Long id);
}
