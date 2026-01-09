package bcu.Group_F6.librarysystem.commands;

import bcu.Group_F6.librarysystem.commands.Command;
import bcu.Group_F6.librarysystem.main.LibraryException;
import bcu.Group_F6.librarysystem.model.Library;
import bcu.Group_F6.librarysystem.model.Patron;
import bcu.Group_F6.librarysystem.model.Book;
import java.time.LocalDate;
import java.util.List;

public class ShowPatron implements Command {

    private final int patronId;

    public ShowPatron(int patronId) {
        this.patronId = patronId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Patron patron = library.getPatronByID(patronId);
        System.out.println("Patron #" + patron.getId() + " - " + patron.getName());
        System.out.println("Phone: " + patron.getPhone());
        System.out.println("Email: " + patron.getEmail());
        List<Book> books = patron.getBooks();
        if (books.isEmpty()) {
            System.out.println("No books borrowed.");
        } else {
            System.out.println("Borrowed books:");
            for (Book book : books) {
                System.out.println("  " + book.getDetailsShort());
            }
            System.out.println(books.size() + " book(s) borrowed");
        }
    }
}
