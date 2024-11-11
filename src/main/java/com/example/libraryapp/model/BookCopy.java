package com.example.libraryapp.model;

public class BookCopy {
    private long id;
    private Book book;
    private boolean isLoaned;
    public BookCopy() {

    }
    public BookCopy(long id, Book book, boolean isLoaned) {
        this.id = id;
        this.book = book;
        this.isLoaned = isLoaned;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isLoaned() {
        return isLoaned;
    }

    public void setLoaned(boolean loaned) {
        isLoaned = loaned;
    }
}
