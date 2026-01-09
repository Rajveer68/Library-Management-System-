package bcu.Group_F6.librarysystem.model;

import bcu.Group_F6.librarysystem.main.LibraryException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Patron {

    private int id;
    private String name;
    private String phone;
    private String email;
    private final List<Book> books = new ArrayList<>();

    // constructor
    public Patron(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public void borrowBook(Book book, LocalDate startDate, LocalDate dueDate) throws LibraryException {
        if (book.isOnLoan()) {
            throw new LibraryException("Book is already on loan.");
        }
        // create loan and associate
        Loan loan = new Loan(this, book, startDate, dueDate);
        book.setLoan(loan);
        addBook(book);
    }

    public void renewBook(Book book, LocalDate dueDate) throws LibraryException {
        if (!books.contains(book) || !book.isOnLoan()) {
            throw new LibraryException("Book is not on loan to this patron.");
        }
        book.setDueDate(dueDate);
    }

    public void returnBook(Book book) throws LibraryException {
        if (!books.contains(book)) {
            throw new LibraryException("This patron did not borrow this book.");
        }
        book.returnToLibrary();
        books.remove(book);
    }

    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
        }
    }
}
 