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

            option = readInt(scanner, "Choose an option: ");

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

    // =========================
    // CREATE
    // =========================

    private static void registerBook(
            Scanner scanner,
            BookCatalog catalog) {

        System.out.println("\n===== REGISTER BOOK =====");

        String isbn = readNonEmptyString(
                scanner,
                "ISBN: "
        );

        String title = readNonEmptyString(
                scanner,
                "Title: "
        );

        String author = readNonEmptyString(
                scanner,
                "Author: "
        );

        int publicationYear =
                readPublicationYear(scanner);

        Book newBook = new Book(
                isbn,
                title,
                author,
                publicationYear
        );

        if (catalog.register(newBook)) {

            System.out.println(
                    "Book registered successfully!"
            );

        } else {

            System.out.println(
                    "Error: ISBN already registered or invalid data."
            );
        }
    }

    // =========================
    // READ - LIST
    // =========================

    private static void listBooks(
            BookCatalog catalog) {

        System.out.println("\n===== REGISTERED BOOKS =====");

        if (catalog.isEmpty()) {

            System.out.println(
                    "No books registered."
            );

            return;
        }

        for (Book book : catalog.listAll()) {

            System.out.println(book);
            System.out.println(
                    "----------------------------"
            );
        }
    }

    // =========================
    // READ - FIND
    // =========================

    private static void findBook(
            Scanner scanner,
            BookCatalog catalog) {

        if (catalog.isEmpty()) {

            System.out.println(
                    "No books registered."
            );

            return;
        }

        System.out.println("\n===== FIND BOOK =====");
        System.out.println("[1] Find by ISBN");
        System.out.println("[2] Find by Title");
        System.out.println("[0] Back");

        int option = readInt(
                scanner,
                "Choose an option: "
        );

        switch (option) {

            case 1:

                String isbn = readNonEmptyString(
                        scanner,
                        "Enter the ISBN: "
                );

                Book bookByIsbn =
                        catalog.findById(isbn);

                if (bookByIsbn != null) {

                    System.out.println("\nBook found:");
                    System.out.println(bookByIsbn);

                } else {

                    System.out.println(
                            "No book found with the provided ISBN."
                    );
                }

                break;

            case 2:

                String title = readNonEmptyString(
                        scanner,
                        "Enter the title: "
                );

                List<Book> booksByTitle =
                        catalog.findByTitle(title);

                if (booksByTitle.isEmpty()) {

                    System.out.println(
                            "No books found with the provided title."
                    );

                } else {

                    System.out.println(
                            "\n===== SEARCH RESULTS ====="
                    );

                    for (Book book : booksByTitle) {

                        System.out.println(book);
                        System.out.println(
                                "----------------------------"
                        );
                    }
                }

                break;

            case 0:
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    // =========================
    // UPDATE
    // =========================

    private static void updateBook(
            Scanner scanner,
            BookCatalog catalog) {

        if (catalog.isEmpty()) {

            System.out.println(
                    "No books registered."
            );

            return;
        }

        Book book = selectBook(
                scanner,
                catalog,
                "update"
        );

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

        int option = readInt(
                scanner,
                "Choose an option: "
        );

        switch (option) {

            case 1:

                String newTitle = readNonEmptyString(
                        scanner,
                        "New title: "
                );

                book.setTitle(newTitle);
                break;

            case 2:

                String newAuthor = readNonEmptyString(
                        scanner,
                        "New author: "
                );

                book.setAuthor(newAuthor);
                break;

            case 3:

                int newYear =
                        readPublicationYear(scanner);

                book.setPublicationYear(newYear);
                break;

            case 0:

                System.out.println(
                        "Update cancelled."
                );

                return;

            default:

                System.out.println(
                        "Invalid option."
                );

                return;
        }

        System.out.println(
                "\nBook updated successfully!"
        );

        System.out.println(
                "Updated information:"
        );

        System.out.println(book);
    }

    // =========================
    // DELETE
    // =========================

    private static void removeBook(
            Scanner scanner,
            BookCatalog catalog) {

        if (catalog.isEmpty()) {

            System.out.println(
                    "No books registered."
            );

            return;
        }

        Book book = selectBook(
                scanner,
                catalog,
                "remove"
        );

        if (book == null) {
            return;
        }

        System.out.println(
                "\nBook selected for removal:"
        );

        System.out.println(book);

        String confirmation = readNonEmptyString(
                scanner,
                "\nAre you sure you want to remove this book? (Y/N): "
        );

        if (confirmation.equalsIgnoreCase("Y")) {

            if (catalog.remove(book.getIsbn())) {

                System.out.println(
                        "Book removed successfully!"
                );

            } else {

                System.out.println(
                        "Failed to remove the book."
                );
            }

        } else if (confirmation.equalsIgnoreCase("N")) {

            System.out.println(
                    "Removal cancelled."
            );

        } else {

            System.out.println(
                    "Invalid confirmation. Removal cancelled."
            );
        }
    }

    // =========================
    // SELECT BOOK
    // =========================

    private static Book selectBook(
            Scanner scanner,
            BookCatalog catalog,
            String operation) {

        System.out.println(
                "\n===== SELECT BOOK TO "
                        + operation.toUpperCase()
                        + " ====="
        );

        System.out.println("[1] Search by ISBN");
        System.out.println("[2] Search by Title");
        System.out.println("[0] Cancel");

        int option = readInt(
                scanner,
                "Choose an option: "
        );

        switch (option) {

            case 1:

                String isbn = readNonEmptyString(
                        scanner,
                        "Enter the ISBN: "
                );

                Book bookByIsbn =
                        catalog.findById(isbn);

                if (bookByIsbn == null) {

                    System.out.println(
                            "No book found with the provided ISBN."
                    );
                }

                return bookByIsbn;

            case 2:

                String title = readNonEmptyString(
                        scanner,
                        "Enter the title: "
                );

                List<Book> booksByTitle =
                        catalog.findByTitle(title);

                if (booksByTitle.isEmpty()) {

                    System.out.println(
                            "No books found with the provided title."
                    );

                    return null;
                }

                // Only one book found
                if (booksByTitle.size() == 1) {
                    return booksByTitle.get(0);
                }

                // Multiple books found
                System.out.println(
                        "\nMultiple books found:"
                );

                System.out.println(
                        "----------------------------"
                );

                for (int i = 0;
                     i < booksByTitle.size();
                     i++) {

                    System.out.println(
                            "[" + (i + 1) + "]"
                    );

                    System.out.println(
                            booksByTitle.get(i)
                    );

                    System.out.println(
                            "----------------------------"
                    );
                }

                int selected = readInt(
                        scanner,
                        "Choose a book: "
                );

                if (selected < 1
                        || selected > booksByTitle.size()) {

                    System.out.println(
                            "Invalid selection."
                    );

                    return null;
                }

                return booksByTitle.get(selected - 1);

            case 0:

                return null;

            default:

                System.out.println(
                        "Invalid option."
                );

                return null;
        }
    }

    // =========================
    // INPUT VALIDATION
    // =========================

    private static int readInt(
            Scanner scanner,
            String message) {

        while (true) {

            System.out.print(message);

            if (scanner.hasNextInt()) {

                int value = scanner.nextInt();
                scanner.nextLine();

                return value;
            }

            System.out.println(
                    "Invalid input. Please enter a number."
            );

            scanner.nextLine();
        }
    }

    private static String readNonEmptyString(
            Scanner scanner,
            String message) {

        while (true) {

            System.out.print(message);

            String value =
                    scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println(
                    "Input cannot be empty. Please try again."
            );
        }
    }

    private static int readPublicationYear(
            Scanner scanner) {

        while (true) {

            int year = readInt(
                    scanner,
                    "Publication Year: "
            );

            if (year > 0 && year <= 2026) {
                return year;
            }

            System.out.println(
                    "Invalid publication year. "
                    + "Please enter a valid year."
            );
        }
    }

    // =========================
    // SAMPLE BOOKS
    // =========================

    private static void loadSampleBooks(
            BookCatalog catalog) {

        catalog.register(
                new Book(
                        "9798855413779",
                        "Lord of the Mysteries, Vol. 1: The Clown, Part I",
                        "Cuttlefish That Loves Diving",
                        2020
                )
        );

        catalog.register(
                new Book(
                        "9781638494195",
                        "Shadow Slave, Book 1: Child of Shadows",
                        "Guiltythree",
                        2026
                )
        );

        catalog.register(
                new Book(
                        "9798400903526",
                        "Omniscient Reader's Viewpoint, Vol. 1",
                        "singNsong",
                        2018
                )
        );

        catalog.register(
                new Book(
                        "9780008537760",
                        "The Lord of the Rings",
                        "J. R. R. Tolkien",
                        1954
                )
        );
    }
}