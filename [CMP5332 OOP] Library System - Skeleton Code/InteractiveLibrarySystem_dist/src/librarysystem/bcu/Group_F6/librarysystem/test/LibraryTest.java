package bcu.Group_F6.librarysystem.test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import bcu.Group_F6.librarysystem.model.Book;
import bcu.Group_F6.librarysystem.model.Library;
import bcu.Group_F6.librarysystem.model.Patron;
import bcu.Group_F6.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.List;

public class LibraryTest {

    private Library library;
    private Book book;
    private Patron patron;

    @Before
    public void setUp() {
        library = new Library();
        book = new Book(1, "Test Book", "Author", "2020", "Publisher");
        patron = new Patron(1, "John Doe", "123456", "john@example.com");
    }

    @Test
    public void testAddAndGetBook() throws LibraryException {
        library.addBook(book);
        assertEquals(1, library.getBooks().size());
        assertEquals(book, library.getBookByID(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDuplicateBook() {
        library.addBook(book);
        library.addBook(book);
    }

    @Test(expected = LibraryException.class)
    public void testGetNonExistentBook() throws LibraryException {
        library.getBookByID(999);
    }

    @Test
    public void testAddAndGetPatron() throws LibraryException {
        library.addPatron(patron);
        assertEquals(1, library.getPatrons().size());
        assertEquals(patron, library.getPatronByID(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDuplicatePatron() {
        library.addPatron(patron);
        library.addPatron(patron);
    }

    @Test(expected = LibraryException.class)
    public void testGetNonExistentPatron() throws LibraryException {
        library.getPatronByID(999);
    }

    @Test
    public void testRemoveBook() throws LibraryException {
        library.addBook(book);
        library.removeBook(book);
        assertEquals(0, library.getBooks().size());
    }

    @Test(expected = LibraryException.class)
    public void testRemoveBookOnLoan() throws LibraryException {
        library.addBook(book);
        patron.borrowBook(book, LocalDate.now(), LocalDate.now().plusDays(7));
        library.removeBook(book);
    }

    @Test
    public void testRemovePatron() throws LibraryException {
        library.addPatron(patron);
        library.removePatron(patron);
        assertEquals(0, library.getPatrons().size());
    }

    @Test(expected = LibraryException.class)
    public void testRemovePatronWithLoans() throws LibraryException {
        library.addPatron(patron);
        patron.borrowBook(book, LocalDate.now(), LocalDate.now().plusDays(7));
        library.removePatron(patron);
    }

    @Test
    public void testGetLoanPeriod() {
        assertEquals(7, library.getLoanPeriod());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetBooksUnmodifiable() {
        library.addBook(book);
        List<Book> books = library.getBooks();
        books.add(new Book(2, "Test", "Test", "2020", "Test")); // Should fail
    }
}
