package bcu.Group_F6.librarysystem.commands;

import bcu.Group_F6.librarysystem.main.LibraryException;
import bcu.Group_F6.librarysystem.model.Library;
import java.time.LocalDate;

/**
 * Command to soft-delete a patron from the library.
 * <p>
 * This command sets the deleted flag of a patron to true, effectively hiding them
 * from the system views without removing the record from the database/file.
 * A patron can only be deleted if they have no active loans.
 * </p>
 */
public class DeletePatron implements Command {

    private final int patronId;

    public DeletePatron(int patronId) {
        this.patronId = patronId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        bcu.Group_F6.librarysystem.model.Patron patron = library.getPatronByID(patronId);
        if (!patron.getBooks().isEmpty()) {
            throw new LibraryException("Cannot delete patron who has books on loan.");
        }
        patron.setDeleted(true);
        System.out.println("Patron #" + patron.getId() + " marked as deleted.");
    }
}
