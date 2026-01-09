package bcu.Group_F6.librarysystem.commands;

import bcu.Group_F6.librarysystem.model.Book;
import bcu.Group_F6.librarysystem.model.Library;
import bcu.Group_F6.librarysystem.main.LibraryException;
import java.time.LocalDate;

public class ShowBook implements Command{

    private final int bookId;

    public ShowBook(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public void execute(Library library, LocalDate currentDate) throws LibraryException {
        Book book = library.getBookByID(bookId);
        System.out.println(book.getDetailsLong());
    }
}
