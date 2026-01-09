package bcu.Group_F6.librarysystem.commands;

import bcu.Group_F6.librarysystem.main.LibraryException;
import bcu.Group_F6.librarysystem.model.Library;
import bcu.Group_F6.librarysystem.model.Patron;
import bcu.Group_F6.librarysystem.model.Book;
import java.time.LocalDate;

public class BorrowBook implements Command {

    private final int patronId;
    private final int bookId;

    public BorrowBook(int patronId, int bookId) {
        this.patronId = patronId;
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Patron patron = library.getPatronByID(patronId);
        Book book = library.getBookByID(bookId);
        LocalDate dueDate = currentDate.plusDays(library.getLoanPeriod());
        patron.borrowBook(book, currentDate, dueDate);
        System.out.println("Book #" + book.getId() + " loaned to Patron #" + patron.getId() + " until " + dueDate);
    }
}
