package com.codegym.controllers;

import com.codegym.models.Book;
import com.codegym.models.Category;
import com.codegym.services.BookService;
import com.codegym.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Page<Category> categories(Pageable pageable){
        return categoryService.findALl(pageable);
    }

    @GetMapping("/books")
    public ModelAndView listBooks(@RequestParam("key_word") Optional<String> key_word, @PageableDefault(size = 5, sort = "name") Pageable pageable){
        Page<Book> books;
        if (key_word.isPresent()){
            books = bookService.findAllByNameContaining(key_word.get(),pageable);
        } else {
            books = bookService.findAll(pageable);
        }

        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping("/create-book")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book", new Book());
        return modelAndView;
    }

    @PostMapping("/create-book")
    public ModelAndView saveBook(@ModelAttribute("book") Book book){
        bookService.save(book);
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book", new Book());
        modelAndView.addObject("message", "New book added successfully");
        return modelAndView;
    }

    @GetMapping("/edit-book/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Book book = bookService.findById(id);
        if (book != null) {
            ModelAndView modelAndView = new ModelAndView("/book/edit");
            modelAndView.addObject("book", book);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-book")
    public ModelAndView updateBook(@ModelAttribute("book") Book book) {
        bookService.save(book);
        ModelAndView modelAndView = new ModelAndView("/book/edit");
        modelAndView.addObject("book", book);
        modelAndView.addObject("message", "Book information updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-book/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Book book = bookService.findById(id);
        if (book != null) {
            ModelAndView modelAndView = new ModelAndView("/book/delete");
            modelAndView.addObject("book", book);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-book")
    public String deleteCustomer(@ModelAttribute("book") Book book) {
        bookService.remove(book.getId());
        return "redirect:books";
    }
}
