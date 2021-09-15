package com.codewalla.books.Controller;


import com.codewalla.books.dao.BookRepository;
import com.codewalla.books.dao.UserRepository;
import com.codewalla.books.entity.Book;
import com.codewalla.books.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/health")
    public String health(){
        return "Api is working";
    }



    @GetMapping(value = "/{id}")
    public ResponseEntity getUser(@PathVariable("id") Integer id){
        Book book = bookRepository.getBookById(id);
        if(book == null){
            return new ResponseEntity<String>("No book found with this "+ id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Book>(book,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getBook(@RequestParam(required = false) String writter){
        if(writter != null) {
            List<Book> books = bookRepository.getBookByWritter(writter);
            if (books.isEmpty()) {
                return new ResponseEntity<String>("No books found with this " + writter, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<List<Book>>(books, HttpStatus.OK);
        }
        return new ResponseEntity<List<Book>>(bookRepository.getAllBooks(),HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) throws SQLIntegrityConstraintViolationException {
        if(bookRepository.getBookById(book.getId()) != null){
            return new ResponseEntity<String>("book Already exist "+ book.getId(), HttpStatus.IM_USED);
        }

        bookRepository.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping
    public ResponseEntity<?> updateBook(@RequestBody Book book) {
        if(bookRepository.getBookById(book.getId()) == null){
            return new ResponseEntity<String>("Unable to update "+ book.getId()+" user not found", HttpStatus.NOT_FOUND);
        }

        bookRepository.updateBook(book);
        return new ResponseEntity<Book>(book,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Integer id) {
        if(bookRepository.getBookById(id) == null){
            return new ResponseEntity<String>(" book not found" + id, HttpStatus.NOT_FOUND);
        }

        bookRepository.deleteBook(id);
        return new ResponseEntity<Book>(HttpStatus.NO_CONTENT);
    }



}
