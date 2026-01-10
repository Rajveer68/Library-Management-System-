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
    private final List<String> history = new ArrayList<>();
    private boolean isDeleted = false;
    private static final int MAX_BORROW_LIMIT = 2;

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
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set the deleted status of the patron.
     * <p>
     * A deleted patron remains in the system but is hidden from standard views.
     * This implements the "soft delete" functionality.
     * </p>
     * @param isDeleted true if the patron is deleted, false otherwise
     */
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public List<String> getHistory() {
        return Collections.unmodifiableList(history);
    }

    public void addHistoryEntry(String entry) {
        history.add(entry);
    }

    public void borrowBook(Book book, LocalDate startDate, LocalDate dueDate) throws LibraryException {
        if (book.isOnLoan()) {
            throw new LibraryException("Book is already on loan.");
        }
        if (books.size() >= MAX_BORROW_LIMIT) {
            throw new LibraryException("Patron has reached the maximum borrowing limit of " + MAX_BORROW_LIMIT + " books.");
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
        // Add to history before returning
        Loan loan = book.getLoan();
        if (loan != null) {
            String entry = "Book: " + book.getTitle() + " (ID: " + book.getId() + ") - Borrowed: " + loan.getStartDate() + " - Returned: " + LocalDate.now();
            history.add(entry);
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
 