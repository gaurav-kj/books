package com.codewalla.books.entity;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String name;
    private String writter;
    private Integer price;
    private Integer edition;


    public Book(){}

    public Book(int id, String name, String writter, Integer price, Integer edition) {
        this.id = id;
        this.name = name;
        this.writter = writter;
        this.price = price;
        this.edition = edition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWritter() {
        return writter;
    }

    public void setWritter(String writter) {
        this.writter = writter;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }
}
