package com.codewalla.books.dao;

import com.codewalla.books.entity.Book;

import java.util.List;

public interface BookRepository {
    Boolean addBook(Book book);
    List<Book> getAllBooks();
    Book getBookById(int id);
    List<Book> getBookByWritter(String writter);
    Integer updateBook(Book book);
    Integer deleteBook(Integer id);
}
