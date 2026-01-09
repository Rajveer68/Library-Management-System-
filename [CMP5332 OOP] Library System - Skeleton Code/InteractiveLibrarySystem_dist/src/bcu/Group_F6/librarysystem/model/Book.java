package bcu.Group_F6.librarysystem.model;

import bcu.Group_F6.librarysystem.main.LibraryException;
import java.time.LocalDate;

public class Book {

    private int id;
    private String title;
    private String author;
    private String publicationYear;

    private Loan loan;

    public Book(int id, String title, String author, String publicationYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
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

    public String getDetailsShort() {
        return "Book #" + id + " - " + title;
    }

    public String getDetailsLong() {
        StringBuilder sb = new StringBuilder();
        sb.append("Book #").append(id).append(" - ").append(title).append("\n");
        sb.append("Author: ").append(author).append("\n");
        sb.append("Publication Year: ").append(publicationYear).append("\n");
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
