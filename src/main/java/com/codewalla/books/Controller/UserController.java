package com.codewalla.books.Controller;

import com.codewalla.books.dao.UserRepository;
import com.codewalla.books.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/health")
    public String health(){
        return "Api is working";
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getUser(@PathVariable("id") Integer id){
        User user = userRepository.getById(id);
        if(user == null){
            return new ResponseEntity<String>("No user found with this "+ id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }

    @GetMapping(value = "/?email={email}")
    public ResponseEntity getUser(@PathParam("email") String email){
        User user = userRepository.getByEmail(email);
        if(user == null){
           return new ResponseEntity<String>("No user found with this "+ email, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) throws SQLIntegrityConstraintViolationException{
        if(userRepository.getById(user.getId()) != null){
            return new ResponseEntity<String>("User Already exist "+ user.getId(), HttpStatus.IM_USED);
        }

        userRepository.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        if(userRepository.getById(user.getId()) == null){
            return new ResponseEntity<String>("Unable to update "+ user.getId()+" user not found", HttpStatus.NOT_FOUND);
        }

        userRepository.updateUser(user);
        return new ResponseEntity<User>(user,HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id) {
        if(userRepository.getById(id) == null){
            return new ResponseEntity<String>(" user not found" + id, HttpStatus.NOT_FOUND);
        }

        userRepository.deleteById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }


}
