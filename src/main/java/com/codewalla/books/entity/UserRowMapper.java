package com.codewalla.books.entity;

import org.springframework.jdbc.core.RowMapper;
import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper {


    public Object mapRow(ResultSet rs, int rowNum) throws SQLException{
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("firstname"));
        user.setLastName(rs.getString("lastname"));
        user.setAddress(rs.getString("address"));
        user.setEmail(rs.getString("email"));
        user.setContact(rs.getString("contact"));

        return user;
    }
}
