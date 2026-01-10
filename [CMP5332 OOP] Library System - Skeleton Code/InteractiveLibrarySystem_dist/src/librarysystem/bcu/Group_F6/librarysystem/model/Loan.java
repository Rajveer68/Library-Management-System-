package bcu.Group_F6.librarysystem.model;

import java.time.LocalDate;

public class Loan {

    private bcu.Group_F6.librarysystem.model.Patron patron;
    private Book book;
    private LocalDate startDate;
    private LocalDate dueDate;

    public Loan(bcu.Group_F6.librarysystem.model.Patron patron, Book book, LocalDate startDate, LocalDate dueDate) {
        this.patron = patron;
        this.book = book;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public bcu.Group_F6.librarysystem.model.Patron getPatron() {
        return patron;
    }
    public void setPatron(bcu.Group_F6.librarysystem.model.Patron patron){
        this.patron = patron;
    }

    public Book getBook() {
        return book;
    }
    public void setBook(Book book){
        this.book = book;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
 