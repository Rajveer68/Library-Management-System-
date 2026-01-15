package bcu.Group_F6.librarysystem.model;

import bcu.Group_F6.librarysystem.main.LibraryException;
import java.time.LocalDate;

public class Book {

    private int id;
    private String title;
    private String author;
    private String publicationYear;
    private String publisher;
    private boolean isDeleted = false;

    private bcu.Group_F6.librarysystem.model.Loan loan;

    public Book(int id, String title, String author, String publicationYear, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Set the deleted status of the book.
     * <p>
     * A deleted book remains in the system but is hidden from standard views.
     * This implements the "soft delete" functionality.
     * </p>
     * @param isDeleted true if the book is deleted, false otherwise
     */
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public String getDetailsShort() {
        return "Book #" + id + " - " + title;
    }

    public String getDetailsLong() {
        StringBuilder sb = new StringBuilder();
        sb.append("Book #").append(id).append(" - ").append(title).append("\n");
        sb.append("Author: ").append(author).append("\n");
        sb.append("Publication Year: ").append(publicationYear).append("\n");
        sb.append("Publisher: ").append(publisher).append("\n");
        if (isOnLoan()) {
            sb.append("Status: On loan until ").append(getDueDate());
        } else {
            sb.append("Status: Available");
        }
        return sb.toString();
    }

    public boolean isOnLoan() {
        return (loan != null);
    }

    public String getStatus() {
        if (!isOnLoan()) {
            return "Available";
        }
        LocalDate due = getDueDate();
        return "On loan until " + (due != null ? due.toString() : "(unknown)");
    }

    public LocalDate getDueDate() {
        if (loan == null) {
            return null;
        }
        return loan.getDueDate();
    }

    public void setDueDate(LocalDate dueDate) throws LibraryException {
        if (loan == null) {
            throw new LibraryException("Book is not on loan.");
        }
        loan.setDueDate(dueDate);
    }

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public void returnToLibrary() {
        loan = null;
    }
}
