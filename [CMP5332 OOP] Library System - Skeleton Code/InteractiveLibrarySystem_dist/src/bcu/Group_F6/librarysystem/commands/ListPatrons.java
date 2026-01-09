package bcu.Group_F6.librarysystem.commands;

import bcu.Group_F6.librarysystem.model.Library;
import bcu.Group_F6.librarysystem.model.Patron;
import bcu.Group_F6.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.List;

public class ListPatrons implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Patron> patrons = library.getPatrons();
        for (Patron patron : patrons) {
            System.out.println("Patron #" + patron.getId() + " - " + patron.getName());
        }
        System.out.println(patrons.size() + " patron(s)");
    }
}
