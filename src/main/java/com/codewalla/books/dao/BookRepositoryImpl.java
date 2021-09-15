package com.codewalla.books.dao;

import com.codewalla.books.entity.Book;
import com.codewalla.books.entity.BookRowMapper;
import com.codewalla.books.entity.User;
import com.codewalla.books.entity.UserRowMapper;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository{

    private static final Logger LOG =  LoggerFactory.getLogger(BookRepositoryImpl.class);


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Boolean addBook(Book book) {
       String sql = "INSERT INTO books VALUES (?,?,?,?,?)";
        return jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {
           @Override
           public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
              ps.setInt(1,book.getId());
              ps.setString(2,book.getName());
               ps.setString(3,book.getWritter());
               ps.setInt(4,book.getPrice());
               ps.setInt(5,book.getEdition());

               return ps.execute();
           }
       });

    }

    @Override
    public List<Book> getAllBooks() {
        LOG.info("DataBase is called for the all book ");
        return jdbcTemplate.query("SELECT * FROM books",new BookRowMapper());
    }

    @Override
    @Cacheable(value = "book", key = "#id")
    public Book getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try{
            return (Book) this.jdbcTemplate.queryForObject(sql,new Object[] {id},new BookRowMapper());
        }catch (EmptyResultDataAccessException ex){
            return null;
        }finally {
            LOG.info("DataBase is called for the book"+id);
        }
    }

    @Override
    public List<Book> getBookByWritter(String writter) {
        LOG.info("DataBase is called for the book "+writter);
        return jdbcTemplate.query("Select * from books WHERE writter  = ? ",new BookRowMapper(),writter);
    }

    @Override
    @CachePut(value = "books", key = "#book.id")
    public Integer updateBook(Book book) {
        String query = "UPDATE books SET name = ? , writter  = ? ,price = ?, edition = ? WHERE id = ?";
        Object[] params = {book.getName(),book.getWritter(),book.getPrice(),book.getEdition(),book.getId()};
        int[] types = {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER,Types.INTEGER};
        LOG.info("DataBase is called for the book "+book.getId());
        return jdbcTemplate.update(query,params,types);
    }

    @Override
    @CacheEvict(value="book",key = "#id")
    public Integer deleteBook(Integer id) {
        LOG.info("DataBase is called for the deleting book "+ id);
        return jdbcTemplate.update("DELETE FROM books WHERE id = ?",id);
    }
}
