package bcu.Group_F6.librarysystem.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import bcu.Group_F6.librarysystem.model.Book;
import bcu.Group_F6.librarysystem.model.Loan;
import bcu.Group_F6.librarysystem.model.Patron;

import java.time.LocalDate;

public class LoanTest {

    private Loan loan;
    private Patron patron;
    private Book book;
    private LocalDate startDate;
    private LocalDate dueDate;

    @Before
    public void setUp() {
        patron = new Patron(1, "John Doe", "123456", "john@example.com");
        book = new Book(1, "Test Book", "Author", "2020", "Publisher");
        startDate = LocalDate.of(2023, 1, 1);
        dueDate = LocalDate.of(2023, 1, 8);
        loan = new Loan(patron, book, startDate, dueDate);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(patron, loan.getPatron());
        assertEquals(book, loan.getBook());
        assertEquals(startDate, loan.getStartDate());
        assertEquals(dueDate, loan.getDueDate());
    }

    @Test
    public void testSetters() {
        Patron newPatron = new Patron(2, "Jane", "987", "jane@test.com");
        loan.setPatron(newPatron);
        assertEquals(newPatron, loan.getPatron());

        Book newBook = new Book(2, "New Book", "New Auth", "2021", "Pub");
        loan.setBook(newBook);
        assertEquals(newBook, loan.getBook());

        LocalDate newStart = startDate.plusDays(1);
        loan.setStartDate(newStart);
        assertEquals(newStart, loan.getStartDate());

        LocalDate newDue = dueDate.plusDays(7);
        loan.setDueDate(newDue);
        assertEquals(newDue, loan.getDueDate());
    }
}
