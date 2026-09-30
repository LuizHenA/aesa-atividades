package br.edu.aesa.service;

import br.edu.aesa.model.Book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class BookCatalog {

    private HashMap<String, Book> books;

    public BookCatalog() {
        books = new HashMap<>();
    }

    // CREATE
    public boolean register(Book book) {

        if (book == null) {
            return false;
        }

        if (book.getIsbn() == null
                || book.getIsbn().trim().isEmpty()) {
            return false;
        }

        if (book.getTitle() == null
                || book.getTitle().trim().isEmpty()) {
            return false;
        }

        if (book.getAuthor() == null
                || book.getAuthor().trim().isEmpty()) {
            return false;
        }

        if (book.getPublicationYear() <= 0) {
            return false;
        }

        if (books.containsKey(book.getIsbn())) {
            return false;
        }

        books.put(book.getIsbn(), book);
        return true;
    }

    // READ - Search by ISBN
    public Book findById(String isbn) {

        if (isbn == null || isbn.trim().isEmpty()) {
            return null;
        }

        return books.get(isbn);
    }

    // READ - Search by title
    public List<Book> findByTitle(String title) {

        List<Book> results = new ArrayList<>();

        if (title == null || title.trim().isEmpty()) {
            return results;
        }

        for (Book book : books.values()) {

            if (book.getTitle().equalsIgnoreCase(title.trim())) {
                results.add(book);
            }
        }

        return results;
    }

    // READ - List all books
    public Collection<Book> listAll() {
        return books.values();
    }

    // UPDATE
    public boolean update(String isbn, Book updatedBook) {

        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }

        if (updatedBook == null) {
            return false;
        }

        if (!books.containsKey(isbn)) {
            return false;
        }

        books.put(isbn, updatedBook);
        return true;
    }

    // DELETE
    public boolean remove(String isbn) {

        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }

        if (!books.containsKey(isbn)) {
            return false;
        }

        books.remove(isbn);
        return true;
    }

    // Check if the catalog is empty
    public boolean isEmpty() {
        return books.isEmpty();
    }
}