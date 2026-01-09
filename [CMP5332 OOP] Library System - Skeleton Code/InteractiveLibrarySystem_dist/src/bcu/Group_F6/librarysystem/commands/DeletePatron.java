package bcu.Group_F6.librarysystem.commands;

import bcu.Group_F6.librarysystem.main.LibraryException;
import bcu.Group_F6.librarysystem.model.Library;
import java.time.LocalDate;

public class DeletePatron implements Command {

    private final int patronId;

    public DeletePatron(int patronId) {
        this.patronId = patronId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        // TODO: implementation
        throw new LibraryException("Delete Patron command is not implemented.");
    }
}
