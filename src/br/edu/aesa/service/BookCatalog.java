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

        if (book == null || book.getIsbn() == null
                || book.getIsbn().trim().isEmpty()) {
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

    String searchTerm = title.trim().toLowerCase();

    for (Book book : books.values()) {

        if (book.getTitle().toLowerCase().contains(searchTerm)) {
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

        if (isbn == null || isbn.trim().isEmpty()
                || updatedBook == null) {
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

    public boolean isEmpty() {
        return books.isEmpty();
    }
}