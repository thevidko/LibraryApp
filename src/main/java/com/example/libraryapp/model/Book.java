package com.example.libraryapp.model;


public class Book {
    private long id;
    private String title;
    private long authorId;
    private String isbn;

    public Book() {

    }

    public Book(long id, String title, long authorId, String isbn) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.isbn = isbn;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
