package bcu.Group_F6.librarysystem.commands;

import bcu.Group_F6.librarysystem.main.LibraryException;
import bcu.Group_F6.librarysystem.model.Book;
import bcu.Group_F6.librarysystem.model.Library;
import java.time.LocalDate;

/**
 * Command to restore a soft-deleted book.
 * <p>
 * This command sets the deleted flag of a book to false, making it
 * visible again in the system views.
 * </p>
 */
public class RestoreBook implements Command {

    private final int bookId;

    public RestoreBook(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Book book = library.getBookByID(bookId);
        if (!book.isDeleted()) {
            throw new LibraryException("Book #" + bookId + " is not deleted.");
        }
        book.setDeleted(false);
        System.out.println("Book #" + book.getId() + " has been restored.");
    }
}
