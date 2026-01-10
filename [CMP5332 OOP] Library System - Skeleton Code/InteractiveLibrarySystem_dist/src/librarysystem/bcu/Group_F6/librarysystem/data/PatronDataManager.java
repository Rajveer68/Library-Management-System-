package bcu.Group_F6.librarysystem.data;

import bcu.Group_F6.librarysystem.model.Library;
import bcu.Group_F6.librarysystem.model.Patron;
import bcu.Group_F6.librarysystem.main.LibraryException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class PatronDataManager implements bcu.Group_F6.librarysystem.data.DataManager {

    private final String RESOURCE = "./resources/data/patrons.txt";

    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        File f = new File(RESOURCE);
        if (!f.exists()) {
             System.out.println("PatronDataManager: " + f.getAbsolutePath() + " not found. Checking alternative path...");
             f = new File("InteractiveLibrarySystem_dist/resources/data/patrons.txt");
             if (!f.exists()) {
                  f = new File("src/resources/data/patrons.txt");
             }
             
             if (!f.exists()) {
                  System.out.println("PatronDataManager: Could not find patrons.txt. No patrons loaded.");
                  return;
             }
        }
        System.out.println("Loading patrons from: " + f.getAbsolutePath());
        try (Scanner sc = new Scanner(f)) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int id = Integer.parseInt(properties[0]);
                    String name = properties[1];
                    String phone = properties[2];
                    String email = properties[3];
                    boolean isDeleted = properties.length > 4 ? Boolean.parseBoolean(properties[4]) : false;
                    Patron patron = new Patron(id, name, phone, email);
                    patron.setDeleted(isDeleted);
                    library.addPatron(patron);
                } catch (NumberFormatException ex) {
                    throw new LibraryException("Unable to parse patron id " + properties[0] + " on line " + line_idx
                            + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }

    @Override
    public void storeData(Library library) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Patron patron : library.getPatrons()) {
                out.print(patron.getId() + SEPARATOR);
                out.print(patron.getName() + SEPARATOR);
                out.print(patron.getPhone() + SEPARATOR);
                out.print(patron.getEmail() + SEPARATOR);
                out.print(patron.isDeleted() + SEPARATOR);
                out.println();
            }
        }
    }
}
 