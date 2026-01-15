package bcu.Group_F6.librarysystem.commands;

import bcu.Group_F6.librarysystem.main.LibraryException;
import bcu.Group_F6.librarysystem.model.Library;
import java.time.LocalDate;

public interface Command {

    public static final String HELP_MESSAGE = "Commands:\n"
            + "\tlistbooks                       print all books*\n"
            + "\tlistpatrons                     print all patrons\n"
            + "\taddbook                         add a new book*\n"
            + "\taddpatron                       add a new patron\n"
            + "\tshowbook <id>                   show book details\n"
            + "\tshowpatron <id>                 show patron details\n"
            + "\tborrow <patronID> <bookID>      borrow a book\n"
            + "\trenew <patronID> <bookID>       renew a book\n"
            + "\treturn <patronID> <bookID>      return a book\n"
            + "\tdeletebook <id>                 soft delete a book\n"
            + "\tdeletepatron <id>               soft delete a patron\n"
            + "\trestorebook <id>                restore a deleted book\n"
            + "\trestorepatron <id>              restore a deleted patron\n"
            + "\tloadgui                         loads the GUI version of the app*\n"
            + "\thelp                            prints this help message*\n"
            + "\texit                            exits the program*";

    
    public void execute(Library library, LocalDate currentDate) throws LibraryException;
    
}
 