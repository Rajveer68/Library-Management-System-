package bcu.Group_F6.librarysystem.commands;

import bcu.Group_F6.librarysystem.main.LibraryException;
import bcu.Group_F6.librarysystem.model.Library;
import bcu.Group_F6.librarysystem.model.Patron;
import bcu.Group_F6.librarysystem.model.Book;
import java.time.LocalDate;

public class RenewBook implements Command {

    private final int patronId;
    private final int bookId;

    public RenewBook(int patronId, int bookId) {
        this.patronId = patronId;
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Patron patron = library.getPatronByID(patronId);
        Book book = library.getBookByID(bookId);
        LocalDate newDue = currentDate.plusDays(library.getLoanPeriod());
        
        if (!patron.getBooks().contains(book) || !book.isOnLoan()) {
            throw new LibraryException("Book is not on loan to this patron.");
        }
        book.setDueDate(newDue);
        System.out.println("Book #" + book.getId() + " renewed until " + newDue);
    }
}