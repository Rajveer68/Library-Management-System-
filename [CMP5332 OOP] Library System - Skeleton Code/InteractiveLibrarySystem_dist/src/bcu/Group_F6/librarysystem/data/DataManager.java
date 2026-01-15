package bcu.Group_F6.librarysystem.data;

import bcu.Group_F6.librarysystem.model.Library;
import bcu.Group_F6.librarysystem.main.LibraryException;
import java.io.IOException;

public interface DataManager {
    
    public static final String SEPARATOR = "::";
    
    public void loadData(Library library) throws IOException, LibraryException;
    public void storeData(Library library) throws IOException;
}
 