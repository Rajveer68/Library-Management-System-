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
        for (Book book : books) {
            System.out.println(book.getDetailsShort());
        }
        System.out.println(books.size() + " book(s)");
    }
}
 