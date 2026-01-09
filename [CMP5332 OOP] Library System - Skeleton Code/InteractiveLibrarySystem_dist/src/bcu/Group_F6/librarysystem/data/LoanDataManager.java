package bcu.Group_F6.librarysystem.data;

import bcu.Group_F6.librarysystem.main.LibraryException;
import bcu.Group_F6.librarysystem.model.*;


import java.io.*;
import java.util.*;
import java.io.IOException;
import java.time.LocalDate;

public class LoanDataManager implements DataManager {
    
    public final String RESOURCE = "./resources/data/loans.txt";

    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        File f = new File(RESOURCE);
        if (!f.exists()) return;
        try (Scanner sc = new Scanner(f)) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);

                // Validate line format
                if (properties.length < 4) {
                    System.err.println("Warning: Invalid loan record on line " + line_idx + "; expected 4 fields, got " + properties.length + ". Skipping.");
                    line_idx++;
                    continue;
                }

                int patronId;
                int bookId;
                LocalDate startDate;
                LocalDate dueDate;
                try {
                    patronId = Integer.parseInt(properties[0]);
                    bookId = Integer.parseInt(properties[1]);
                    startDate = LocalDate.parse(properties[2]);
                    dueDate = LocalDate.parse(properties[3]);
                } catch (NumberFormatException | java.time.format.DateTimeParseException ex) {
                    System.err.println("Warning: Unable to parse loan data on line " + line_idx + ". Error: " + ex.getMessage() + ". Skipping.");
                    line_idx++;
                    continue;
                }

                // Resolve patron and book; if missing, skip the loan with a warning
                Patron patron = null;
                Book book = null;
                try {
                    patron = library.getPatronByID(patronId);
                } catch (LibraryException ex) {
                    System.err.println("Warning: Unknown patron id " + patronId + " on loans file line " + line_idx + ". Skipping loan.");
                    line_idx++;
                    continue;
                }
                try {
                    book = library.getBookByID(bookId);
                } catch (LibraryException ex) {
                    System.err.println("Warning: Unknown book id " + bookId + " on loans file line " + line_idx + ". Skipping loan.");
                    line_idx++;
                    continue;
                }

                // Create loan
                Loan loan = new Loan(patron, book, startDate, dueDate);
                book.setLoan(loan);
                patron.addBook(book);

                line_idx++;
            }
        }
    }

    @Override
    public void storeData(Library library) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Patron patron : library.getPatrons()) {
                for (Book book : patron.getBooks()) {
                    Loan loan = book.getLoan();
                    if (loan == null) continue; // safety
                    out.print(patron.getId() + SEPARATOR);
                    out.print(book.getId() + SEPARATOR);
                    out.print(loan.getStartDate() + SEPARATOR);
                    out.print(loan.getDueDate() + SEPARATOR);
                    out.println();
                }
            }
        }
    }
    
}
 