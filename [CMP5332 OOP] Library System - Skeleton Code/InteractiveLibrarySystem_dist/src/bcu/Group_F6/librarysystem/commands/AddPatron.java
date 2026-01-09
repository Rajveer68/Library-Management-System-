package bcu.Group_F6.librarysystem.commands;

import bcu.Group_F6.librarysystem.main.LibraryException;
import bcu.Group_F6.librarysystem.model.Library;
import bcu.Group_F6.librarysystem.model.Patron;

import java.time.LocalDate;

public class AddPatron implements Command {

    private final String name;
    private final String phone;
    private final String email;

    public AddPatron(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        int maxId = 0;
        if (library.getPatrons().size() > 0) {
            int lastIndex = library.getPatrons().size() - 1;
            maxId = library.getPatrons().get(lastIndex).getId();
        }
        Patron patron = new Patron(++maxId, name, phone, email);
        library.addPatron(patron);
        System.out.println("Patron #" + patron.getId() + " added.");
    }
}
