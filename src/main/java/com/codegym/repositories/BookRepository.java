package com.codegym.repositories;

import com.codegym.models.Book;
import com.codegym.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Iterable<Book> findAllByCategory(Category category);

    Page<Book> findAllByNameContaining(String name, Pageable pageable);
}
