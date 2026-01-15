package bcu.Group_F6.librarysystem.data;

import bcu.Group_F6.librarysystem.model.Book;
import bcu.Group_F6.librarysystem.model.Library;
import bcu.Group_F6.librarysystem.main.LibraryException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BookDataManager implements DataManager {
    
    private final String RESOURCE = "./resources/data/books.txt";
    
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        File file = new File(RESOURCE);
        if (!file.exists()) {
            System.out.println("BookDataManager: " + file.getAbsolutePath() + " not found. Checking alternative path...");
            // Try to find it in the nested directory structure if run from project root
            file = new File("InteractiveLibrarySystem_dist/resources/data/books.txt");
            if (!file.exists()) {
                 // Try one more common variation
                 file = new File("src/resources/data/books.txt");
            }
            
            if (!file.exists()) {
                 System.out.println("BookDataManager: Could not find books.txt. No books loaded.");
                 return;
            }
        }
        System.out.println("Loading books from: " + file.getAbsolutePath());
        try (Scanner sc = new Scanner(file)) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int id = Integer.parseInt(properties[0]);
                    String title = properties[1];
                    String author = properties[2];
                    String publicationYear = properties[3];
                    String publisher = properties.length > 4 ? properties[4] : "Unknown";
                    boolean isDeleted = properties.length > 5 ? Boolean.parseBoolean(properties[5]) : false;
                    
                    Book book = new Book(id, title, author, publicationYear, publisher);
                    book.setDeleted(isDeleted);
                    library.addBook(book);
                } catch (NumberFormatException ex) {
                    throw new LibraryException("Unable to parse book id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }
    
    @Override
    public void storeData(Library library) throws IOException {
        File file = new File(RESOURCE);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            for (Book book : library.getBooks()) {
                out.print(book.getId() + SEPARATOR);
                out.print(book.getTitle() + SEPARATOR);
                out.print(book.getAuthor() + SEPARATOR);
                out.print(book.getPublicationYear() + SEPARATOR);
                out.print(book.getPublisher() + SEPARATOR);
                out.print(book.isDeleted() + SEPARATOR);
                out.println();
            }
        }
    }
}
 