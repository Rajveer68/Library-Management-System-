package bcu.Group_F6.librarysystem.commands;

import bcu.Group_F6.librarysystem.model.Book;
import bcu.Group_F6.librarysystem.model.Library;
import bcu.Group_F6.librarysystem.main.LibraryException;

import java.time.LocalDate;
import java.util.List;

public class ListBooks implements Command {

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        List<Book> books = library.getBooks();
        int count = 0;
        for (Book book : books) {
            if (!book.isDeleted()) {
                System.out.println(book.getDetailsShort());
                count++;
            }
        }
        System.out.println(count + " book(s)");
    }
}
 