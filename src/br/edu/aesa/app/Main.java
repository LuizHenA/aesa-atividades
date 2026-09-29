package br.edu.aesa.app;

import br.edu.aesa.model.Book;
import br.edu.aesa.service.BookCatalog;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BookCatalog catalog = new BookCatalog();

        int option;

        do {
            System.out.println("\n===== BOOK CATALOG =====");
            System.out.println("[1] Register Book");
            System.out.println("[2] List All Books");
            System.out.println("[3] Find Book by ISBN");
            System.out.println("[4] Find Book by Title");
            System.out.println("[5] Update Book");
            System.out.println("[6] Remove Book");
            System.out.println("[0] Exit");
            System.out.print("Choose an option: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1:

                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine();

                    System.out.print("Title: ");
                    String title = scanner.nextLine();

                    System.out.print("Author: ");
                    String author = scanner.nextLine();

                    System.out.print("Publication Year: ");
                    int publicationYear = scanner.nextInt();
                    scanner.nextLine();

                    Book newBook = new Book(
                            isbn,
                            title,
                            author,
                            publicationYear
                    );

                    if (catalog.register(newBook)) {
                        System.out.println("Book registered successfully!");
                    } else {
                        System.out.println(
                                "Error: invalid ISBN or ISBN already registered."
                        );
                    }

                    break;

                case 2:

                    if (catalog.isEmpty()) {
                        System.out.println("No books registered.");
                    } else {

                        System.out.println("\n===== REGISTERED BOOKS =====");

                        for (Book book : catalog.listAll()) {
                            System.out.println(book);
                            System.out.println("----------------------------");
                        }
                    }

                    break;

                case 3:

                    System.out.print("Enter the ISBN: ");
                    String isbnToFind = scanner.nextLine();

                    Book foundBook = catalog.findById(isbnToFind);

                    if (foundBook != null) {
                        System.out.println(foundBook);
                    } else {
                        System.out.println(
                                "No book found with the provided ISBN."
                        );
                    }

                    break;

                case 4:

                    System.out.print("Enter the title: ");
                    String titleToFind = scanner.nextLine();

                    List<Book> foundBooks =
                            catalog.findByTitle(titleToFind);

                    if (foundBooks.isEmpty()) {
                        System.out.println(
                                "No books found with the provided title."
                        );
                    } else {

                        System.out.println("\n===== SEARCH RESULTS =====");

                        for (Book book : foundBooks) {
                            System.out.println(book);
                            System.out.println("----------------------------");
                        }
                    }

                    break;

                case 5:

                    System.out.print("Enter the ISBN of the book to update: ");
                    String isbnToUpdate = scanner.nextLine();

                    Book existingBook =
                            catalog.findById(isbnToUpdate);

                    if (existingBook == null) {
                        System.out.println("Book not found.");
                        break;
                    }

                    System.out.print("New title: ");
                    String newTitle = scanner.nextLine();

                    System.out.print("New author: ");
                    String newAuthor = scanner.nextLine();

                    System.out.print("New publication year: ");
                    int newPublicationYear = scanner.nextInt();
                    scanner.nextLine();

                    Book updatedBook = new Book(
                            isbnToUpdate,
                            newTitle,
                            newAuthor,
                            newPublicationYear
                    );

                    if (catalog.update(isbnToUpdate, updatedBook)) {
                        System.out.println(
                                "Book updated successfully!"
                        );
                    } else {
                        System.out.println(
                                "Failed to update the book."
                        );
                    }

                    break;

                case 6:

                    System.out.print("Enter the ISBN of the book to remove: ");
                    String isbnToRemove = scanner.nextLine();

                    if (catalog.remove(isbnToRemove)) {
                        System.out.println(
                                "Book removed successfully!"
                        );
                    } else {
                        System.out.println(
                                "No book found with the provided ISBN."
                        );
                    }

                    break;

                case 0:

                    System.out.println("Program terminated.");
                    break;

                default:

                    System.out.println("Invalid option.");
            }

        } while (option != 0);

        scanner.close();
    }
}