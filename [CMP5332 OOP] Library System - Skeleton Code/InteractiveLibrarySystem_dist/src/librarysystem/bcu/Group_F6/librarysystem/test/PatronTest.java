package bcu.Group_F6.librarysystem.test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import bcu.Group_F6.librarysystem.model.Book;
import bcu.Group_F6.librarysystem.model.Patron;
import bcu.Group_F6.librarysystem.main.LibraryException;

import java.time.LocalDate;

public class PatronTest {

    private Patron patron;
    private Book book;

    @Before
    public void setUp() {
        patron = new Patron(1, "John Doe", "123456", "john@example.com");
        book = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", "1925", "Scribner");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(1, patron.getId());
        assertEquals("John Doe", patron.getName());
        assertEquals("123456", patron.getPhone());
        assertEquals("john@example.com", patron.getEmail());
        assertTrue(patron.getBooks().isEmpty());
        assertTrue(patron.getHistory().isEmpty());
    }

    @Test
    public void testSetters() {
        patron.setName("Jane Doe");
        assertEquals("Jane Doe", patron.getName());

        patron.setPhone("654321");
        assertEquals("654321", patron.getPhone());

        patron.setEmail("jane@example.com");
        assertEquals("jane@example.com", patron.getEmail());

        patron.setDeleted(true);
        assertTrue(patron.isDeleted());
    }

    @Test
    public void testBorrowBook() throws LibraryException {
        LocalDate now = LocalDate.now();
        patron.borrowBook(book, now, now.plusDays(7));
        
        assertEquals(1, patron.getBooks().size());
        assertTrue(patron.getBooks().contains(book));
        assertTrue(book.isOnLoan());
        assertEquals(patron, book.getLoan().getPatron());
    }

    @Test(expected = LibraryException.class)
    public void testBorrowBookAlreadyOnLoan() throws LibraryException {
        LocalDate now = LocalDate.now();
        patron.borrowBook(book, now, now.plusDays(7));
        
        Patron patron2 = new Patron(2, "Smith", "999", "smith@test.com");
        patron2.borrowBook(book, now, now.plusDays(7)); // Should throw exception
    }

    @Test
    public void testRenewBook() throws LibraryException {
        LocalDate now = LocalDate.now();
        patron.borrowBook(book, now, now.plusDays(7));
        
        LocalDate newDue = now.plusDays(14);
        patron.renewBook(book, newDue);
        
        assertEquals(newDue, book.getDueDate());
    }

    @Test(expected = LibraryException.class)
    public void testRenewBookNotBorrowed() throws LibraryException {
        patron.renewBook(book, LocalDate.now());
    }

    @Test
    public void testReturnBook() throws LibraryException {
        LocalDate now = LocalDate.now();
        patron.borrowBook(book, now, now.plusDays(7));
        
        patron.returnBook(book);
        
        assertTrue(patron.getBooks().isEmpty());
        assertFalse(book.isOnLoan());
        assertEquals(1, patron.getHistory().size());
    }

    @Test(expected = LibraryException.class)
    public void testReturnBookNotBorrowed() throws LibraryException {
        patron.returnBook(book);
    }
}
