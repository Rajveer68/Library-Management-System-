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
        int count = 0;
        for (Patron patron : patrons) {
            if (!patron.isDeleted()) {
                System.out.println("Patron #" + patron.getId() + " - " + patron.getName());
                count++;
            }
        }
        System.out.println(count + " patron(s)");
    }
}
