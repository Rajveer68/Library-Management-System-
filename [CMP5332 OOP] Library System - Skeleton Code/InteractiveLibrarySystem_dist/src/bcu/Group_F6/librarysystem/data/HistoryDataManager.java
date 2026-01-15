package bcu.Group_F6.librarysystem.data;

import bcu.Group_F6.librarysystem.model.Library;
import bcu.Group_F6.librarysystem.model.Patron;
import bcu.Group_F6.librarysystem.main.LibraryException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HistoryDataManager implements DataManager {
    
    private final String RESOURCE = "./resources/data/history.txt";
    
    @Override
    public void loadData(Library library) throws IOException, LibraryException {
        File f = new File(RESOURCE);
        if (!f.exists()) {
             System.out.println("HistoryDataManager: " + f.getAbsolutePath() + " not found. Checking alternative path...");
             f = new File("InteractiveLibrarySystem_dist/resources/data/history.txt");
             if (!f.exists()) {
                  f = new File("src/resources/data/history.txt");
             }
             
             if (!f.exists()) {
                  // Create empty file if it doesn't exist, to avoid errors later
                  try {
                      File parent = f.getParentFile();
                      if (parent != null) parent.mkdirs();
                      f.createNewFile();
                  } catch (IOException e) {
                      System.out.println("HistoryDataManager: Could not find or create history.txt. History not loaded.");
                      return;
                  }
             }
        }
        
        if (!f.exists() || f.length() == 0) return;

        System.out.println("Loading history from: " + f.getAbsolutePath());
        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(SEPARATOR, 2); // Split only on first separator
                if (parts.length < 2) continue;
                
                try {
                    int patronId = Integer.parseInt(parts[0]);
                    String entry = parts[1];
                    Patron patron = library.getPatronByID(patronId);
                    patron.addHistoryEntry(entry);
                } catch (NumberFormatException | LibraryException ex) {
                    // Ignore malformed lines or unknown patrons
                }
            }
        }
    }
    
    @Override
    public void storeData(Library library) throws IOException {
        File f = new File(RESOURCE);
        File parentDir = f.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        try (PrintWriter out = new PrintWriter(new FileWriter(f))) {
            for (Patron patron : library.getPatrons()) {
                for (String entry : patron.getHistory()) {
                    out.print(patron.getId() + SEPARATOR);
                    out.print(entry); // Entry already contains details
                    out.println();
                }
            }
        }
    }
}
