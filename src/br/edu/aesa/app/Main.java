package br.edu.aesa.app;

import br.edu.aesa.model.Book;
import br.edu.aesa.service.BookCatalog;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        BookCatalog catalog = new BookCatalog();

        loadSampleBooks(catalog);

        int option;

        do {
            System.out.println("\n===== BOOK CATALOG =====");
            System.out.println("[1] Register Book");
            System.out.println("[2] List All Books");
            System.out.println("[3] Find Book");
            System.out.println("[4] Update Book");
            System.out.println("[5] Remove Book");
            System.out.println("[0] Exit");
            System.out.print("Choose an option: ");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {

                case 1:
                    registerBook(scanner, catalog);
                    break;

                case 2:
                    listBooks(catalog);
                    break;

                case 3:
                    findBook(scanner, catalog);
                    break;

                case 4:
                    updateBook(scanner, catalog);
                    break;

                case 5:
                    removeBook(scanner, catalog);
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

    // CREATE
    private static void registerBook(
            Scanner scanner,
            BookCatalog catalog) {

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
    }

    // READ - List all
    private static void listBooks(BookCatalog catalog) {

        if (catalog.isEmpty()) {
            System.out.println("No books registered.");
            return;
        }

        System.out.println("\n===== REGISTERED BOOKS =====");

        for (Book book : catalog.listAll()) {
            System.out.println(book);
            System.out.println("----------------------------");
        }
    }

    // READ - Find
    private static void findBook(
            Scanner scanner,
            BookCatalog catalog) {

        System.out.println("\n===== FIND BOOK =====");
        System.out.println("[1] Find by ISBN");
        System.out.println("[2] Find by Title");
        System.out.println("[0] Back");
        System.out.print("Choose an option: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {

            case 1:

                System.out.print("Enter the ISBN: ");
                String isbn = scanner.nextLine();

                Book bookByIsbn = catalog.findById(isbn);

                if (bookByIsbn != null) {
                    System.out.println(bookByIsbn);
                } else {
                    System.out.println(
                            "No book found with the provided ISBN."
                    );
                }

                break;

            case 2:

                System.out.print("Enter the title: ");
                String title = scanner.nextLine();

                List<Book> booksByTitle =
                        catalog.findByTitle(title);

                if (booksByTitle.isEmpty()) {
                    System.out.println(
                            "No books found with the provided title."
                    );
                } else {

                    System.out.println("\n===== SEARCH RESULTS =====");

                    for (Book book : booksByTitle) {
                        System.out.println(book);
                        System.out.println("----------------------------");
                    }
                }

                break;

            case 0:
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    // UPDATE
    private static void updateBook(
            Scanner scanner,
            BookCatalog catalog) {

        Book book = selectBook(scanner, catalog, "update");

        if (book == null) {
            return;
        }

        System.out.println("\n===== UPDATE BOOK =====");
        System.out.println("Current information:");
        System.out.println(book);

        System.out.println("\nWhat would you like to update?");
        System.out.println("[1] Title");
        System.out.println("[2] Author");
        System.out.println("[3] Publication Year");
        System.out.println("[0] Cancel");
        System.out.print("Choose an option: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {

            case 1:

                System.out.print("New title: ");
                String newTitle = scanner.nextLine();

                book.setTitle(newTitle);
                break;

            case 2:

                System.out.print("New author: ");
                String newAuthor = scanner.nextLine();

                book.setAuthor(newAuthor);
                break;

            case 3:

                System.out.print("New publication year: ");
                int newYear = scanner.nextInt();
                scanner.nextLine();

                book.setPublicationYear(newYear);
                break;

            case 0:
                System.out.println("Update cancelled.");
                return;

            default:
                System.out.println("Invalid option.");
                return;
        }

        System.out.println("\nBook updated successfully!");
        System.out.println("Updated information:");
        System.out.println(book);
    }

    // DELETE
    private static void removeBook(
            Scanner scanner,
            BookCatalog catalog) {

        Book book = selectBook(scanner, catalog, "remove");

        if (book == null) {
            return;
        }

        System.out.println("\nBook selected for removal:");
        System.out.println(book);

        System.out.print("\nAre you sure you want to remove this book? (Y/N): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("Y")) {

            if (catalog.remove(book.getIsbn())) {
                System.out.println("Book removed successfully!");
            } else {
                System.out.println("Failed to remove the book.");
            }

        } else {
            System.out.println("Removal cancelled.");
        }
    }

    // Select a book by ISBN or title
    private static Book selectBook(
            Scanner scanner,
            BookCatalog catalog,
            String operation) {

        System.out.println("\n===== SELECT BOOK TO " +
                operation.toUpperCase() + " =====");

        System.out.println("[1] Search by ISBN");
        System.out.println("[2] Search by Title");
        System.out.println("[0] Cancel");
        System.out.print("Choose an option: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {

            case 1:

                System.out.print("Enter the ISBN: ");
                String isbn = scanner.nextLine();

                Book bookByIsbn = catalog.findById(isbn);

                if (bookByIsbn == null) {
                    System.out.println(
                            "No book found with the provided ISBN."
                    );
                }

                return bookByIsbn;

            case 2:

                System.out.print("Enter the title: ");
                String title = scanner.nextLine();

                List<Book> booksByTitle =
                        catalog.findByTitle(title);

                if (booksByTitle.isEmpty()) {
                    System.out.println(
                            "No books found with the provided title."
                    );
                    return null;
                }

                if (booksByTitle.size() == 1) {
                    return booksByTitle.get(0);
                }

                System.out.println("\nMultiple books found:");
                System.out.println("----------------------------");

                for (int i = 0; i < booksByTitle.size(); i++) {
                    System.out.println(
                            "[" + (i + 1) + "]"
                    );
                    System.out.println(booksByTitle.get(i));
                    System.out.println("----------------------------");
                }

                System.out.print("Choose a book: ");
                int selected = scanner.nextInt();
                scanner.nextLine();

                if (selected < 1 || selected > booksByTitle.size()) {
                    System.out.println("Invalid selection.");
                    return null;
                }

                return booksByTitle.get(selected - 1);

            case 0:
                return null;

            default:
                System.out.println("Invalid option.");
                return null;
        }
    }

    // Sample books
    private static void loadSampleBooks(BookCatalog catalog) {

        catalog.register(new Book(
                "9798855413779",
                "Lord of Mysteries, Vol. 1: The Clown, Part I",
                "Cuttlefish That Loves Diving",
                2025
        ));

        catalog.register(new Book(
                "9781638494195",
                "Shadow Slave, Book 1: Child of Shadows",
                "Guiltythree",
                2026
        ));

        catalog.register(new Book(
                "9798400903526",
                "Omniscient Reader's Viewpoint, Vol. 1",
                "singNsong",
                2025
        ));

        catalog.register(new Book(
                "9780008537760",
                "The Lord of the Rings",
                "J. R. R. Tolkien",
                2022
        ));
    }
}