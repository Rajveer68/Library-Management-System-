package bcu.Group_F6.librarysystem.commands;

import bcu.Group_F6.librarysystem.main.LibraryException;
import bcu.Group_F6.librarysystem.model.Book;
import bcu.Group_F6.librarysystem.model.Library;
import java.time.LocalDate;

/**
 * Command to soft-delete a book from the library.
 * <p>
 * This command sets the deleted flag of a book to true, effectively hiding it
 * from the system views without removing the record from the database/file.
 * A book can only be deleted if it is not currently on loan.
 * </p>
 */
public class DeleteBook implements Command {

    private final int bookId;

    public DeleteBook(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Book book = library.getBookByID(bookId);
        if (book.isOnLoan()) {
            throw new LibraryException("Cannot delete book that is on loan.");
        }
        book.setDeleted(true);
        System.out.println("Book #" + book.getId() + " marked as deleted.");
    }
}
