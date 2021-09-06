package com.codewalla.books.entity;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper {

    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setName(rs.getString("name"));
        book.setWritter(rs.getString("writter"));
        book.setPrice(rs.getInt("price"));
        book.setEdition(rs.getInt("edition"));

        return book;
    }

}
