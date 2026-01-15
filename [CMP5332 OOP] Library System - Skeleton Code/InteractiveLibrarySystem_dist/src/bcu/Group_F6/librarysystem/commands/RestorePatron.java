package bcu.Group_F6.librarysystem.commands;

import bcu.Group_F6.librarysystem.main.LibraryException;
import bcu.Group_F6.librarysystem.model.Library;
import bcu.Group_F6.librarysystem.model.Patron;
import java.time.LocalDate;

/**
 * Command to restore a soft-deleted patron.
 * <p>
 * This command sets the deleted flag of a patron to false, making them
 * visible again in the system views.
 * </p>
 */
public class RestorePatron implements Command {

    private final int patronId;

    public RestorePatron(int patronId) {
        this.patronId = patronId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Patron patron = library.getPatronByID(patronId);
        if (!patron.isDeleted()) {
            throw new LibraryException("Patron #" + patronId + " is not deleted.");
        }
        patron.setDeleted(false);
        System.out.println("Patron #" + patron.getId() + " has been restored.");
    }
}
