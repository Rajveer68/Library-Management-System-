package bcu.Group_F6.librarysystem.data;

import bcu.Group_F6.librarysystem.model.Library;
import bcu.Group_F6.librarysystem.main.LibraryException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LibraryData {
    
    private static final List<bcu.Group_F6.librarysystem.data.DataManager> dataManagers = new ArrayList<>();
    
        // runs only once when the object gets loaded to memory
    static {
        dataManagers.add(new BookDataManager());
        dataManagers.add(new bcu.Group_F6.librarysystem.data.PatronDataManager());
        dataManagers.add(new bcu.Group_F6.librarysystem.data.LoanDataManager());
        dataManagers.add(new bcu.Group_F6.librarysystem.data.HistoryDataManager());
    }
    
    public static Library load() throws LibraryException, IOException {

        Library library = new Library();
        for (bcu.Group_F6.librarysystem.data.DataManager dm : dataManagers) {
            dm.loadData(library);
        }
        return library;
    }

    public static void store(Library library) throws IOException {

        for (bcu.Group_F6.librarysystem.data.DataManager dm : dataManagers) {
            dm.storeData(library);
        }
    }
    
}
 