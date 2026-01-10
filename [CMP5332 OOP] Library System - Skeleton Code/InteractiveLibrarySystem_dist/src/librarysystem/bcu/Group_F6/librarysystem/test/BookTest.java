package bcu.Group_F6.librarysystem.test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Before;
import org.junit.Test;

import bcu.Group_F6.librarysystem.model.Book;
import bcu.Group_F6.librarysystem.model.Loan;
import bcu.Group_F6.librarysystem.model.Patron;
import bcu.Group_F6.librarysystem.main.LibraryException;

import java.time.LocalDate;

public class BookTest {

    private Book book;

    @Before
    public void setUp() {
        book = new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", "1925", "Scribner");
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(1, book.getId());
        assertEquals("The Great Gatsby", book.getTitle());
        assertEquals("F. Scott Fitzgerald", book.getAuthor());
        assertEquals("1925", book.getPublicationYear());
        assertEquals("Scribner", book.getPublisher());
        assertFalse(book.isDeleted());
    }

    @Test
    public void testSetters() {
        book.setTitle("New Title");
        assertEquals("New Title", book.getTitle());

        book.setAuthor("New Author");
        assertEquals("New Author", book.getAuthor());

        book.setPublicationYear("2020");
        assertEquals("2020", book.getPublicationYear());

        book.setPublisher("New Publisher");
        assertEquals("New Publisher", book.getPublisher());

        book.setDeleted(true);
        assertTrue(book.isDeleted());
    }

    @Test
    public void testLoanStatus() {
        assertFalse(book.isOnLoan());
        assertEquals("Available", book.getStatus());
        assertNull(book.getDueDate());
    }

    @Test
    public void testSetLoan() throws LibraryException {
        Patron patron = new Patron(1, "John Doe", "123456", "john@example.com");
        LocalDate now = LocalDate.now();
        Loan loan = new Loan(patron, book, now, now.plusDays(7));
        
        book.setLoan(loan);
        assertTrue(book.isOnLoan());
        assertNotNull(book.getLoan());
        assertEquals(now.plusDays(7), book.getDueDate());
        assertTrue(book.getStatus().startsWith("On loan until"));
    }

    @Test
    public void testReturnToLibrary() {
        Patron patron = new Patron(1, "John Doe", "123456", "john@example.com");
        Loan loan = new Loan(patron, book, LocalDate.now(), LocalDate.now().plusDays(7));
        book.setLoan(loan);
        
        book.returnToLibrary();
        assertFalse(book.isOnLoan());
        assertNull(book.getLoan());
    }
    
    @Test(expected = LibraryException.class)
    public void testSetDueDateThrowsExceptionWhenNotOnLoan() throws LibraryException {
        book.setDueDate(LocalDate.now());
    }
}
