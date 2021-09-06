package com.codewalla.books.dao;

import com.codewalla.books.entity.Book;
import com.codewalla.books.entity.BookRowMapper;
import com.codewalla.books.entity.User;
import com.codewalla.books.entity.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        return jdbcTemplate.query("SELECT * FROM books",new BookRowMapper());
    }

    @Override
    public Book getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try{
            return (Book) this.jdbcTemplate.queryForObject(sql,new Object[] {id},new BookRowMapper());
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public List<Book> getBookByWritter(String writter) {
        return jdbcTemplate.query("Select * from books WHERE writter  = ? ",new BookRowMapper(),writter);
    }

    @Override
    public Integer updateBook(Book book) {
        String query = "UPDATE books SET name = ? , writter  = ? ,price = ?, edition = ? WHERE id = ?";
        Object[] params = {book.getName(),book.getWritter(),book.getPrice(),book.getEdition(),book.getId()};
        int[] types = {Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.INTEGER,Types.INTEGER};
        return jdbcTemplate.update(query,params,types);
    }

    @Override
    public Integer deleteBook(Integer id) {
        return jdbcTemplate.update("DELETE FROM books WHERE id = ?",id);
    }
}
