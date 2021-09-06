package com.codewalla.books.dao;

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
public class UserRepositoryImpl implements  UserRepository{



    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Boolean saveUser(User user) {
       String query = "INSERT INTO users VALUES (?,?,?,?,?,?)";

        return jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setInt(1,user.getId());
                ps.setString(2,user.getFirstName());
                ps.setString(3,user.getLastName());
                ps.setString(4,user.getAddress());
                ps.setString(5,user.getEmail());
                ps.setString(6,user.getContact());

                return ps.execute();
            }
        });
    }

    @Override
    public Integer updateUser(User user) {
       String query = "UPDATE users SET firstname = ? , lastname  = ? ,address = ?, email = ? , contact = ? WHERE id = ?";
       Object[] params = {user.getFirstName(),user.getLastName(),user.getAddress(),user.getEmail(),user.getContact(),user.getId()};
       int[] types = {Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER};
       return jdbcTemplate.update(query,params,types);
    }

    @Override
    public User getById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try{
            return (User) this.jdbcTemplate.queryForObject(sql,new Object[] {id},new UserRowMapper());
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public User getByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try{
            User user = (User) this.jdbcTemplate.queryForObject(sql,User.class, new UserRowMapper());
            return user;
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    @Override
    public Integer deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id = ?",id);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcTemplate.query("SELECT id,firstname,lastname,address,email,contact FROM users",new UserRowMapper());
    }
}
