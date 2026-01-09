package bcu.Group_F6.librarysystem.gui;

import bcu.Group_F6.librarysystem.model.*;
import bcu.Group_F6.librarysystem.commands.DeletePatron;
import bcu.Group_F6.librarysystem.main.LibraryException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainWindow extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu adminMenu;
    private JMenu booksMenu;
    private JMenu membersMenu;

    private JMenuItem adminExit;

    private JMenuItem booksView;
    private JMenuItem booksAdd;
    private JMenuItem booksDel;	
    private JMenuItem booksIssue;
    private JMenuItem booksReturn;

    private JMenuItem memView;
    private JMenuItem memAdd;
    private JMenuItem memDel;

    private Library library;

    public MainWindow(Library library) {

        initialize();
        this.library = library;
    } 
    
    public Library getLibrary() {
        return library;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Library Management System");

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding booksMenu menu and menu items
        booksMenu = new JMenu("Books");
        menuBar.add(booksMenu);

        booksView = new JMenuItem("View");
        booksAdd = new JMenuItem("Add");
        booksDel = new JMenuItem("Delete");
        booksIssue = new JMenuItem("Issue");
        booksReturn = new JMenuItem("Return");
        booksMenu.add(booksView);
        booksMenu.add(booksAdd);
        booksMenu.add(booksDel);
        booksMenu.add(booksIssue);
        booksMenu.add(booksReturn);
        for (int i = 0; i < booksMenu.getItemCount(); i++) {
            booksMenu.getItem(i).addActionListener(this);
        }

        // adding membersMenu menu and menu items
        membersMenu = new JMenu("Patrons");
        menuBar.add(membersMenu);

        memView = new JMenuItem("View");
        memAdd = new JMenuItem("Add");
        memDel = new JMenuItem("Delete");

        membersMenu.add(memView);
        membersMenu.add(memAdd);
        membersMenu.add(memDel);

        memView.addActionListener(this);
        memAdd.addActionListener(this);
        memDel.addActionListener(this);

        setSize(800, 500);

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
/* Uncomment the following line to not terminate the console app when the window is closed */
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);        

    }	

/* Uncomment the following code to run the GUI version directly from the IDE */
//    public static void main(String[] args) throws IOException, LibraryException {
//        Library library = LibraryData.load();
//        new MainWindow(library);			
//    }



    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == adminExit) {
            System.exit(0);
        } else if (ae.getSource() == booksView) {
            displayBooks();
            
        } else if (ae.getSource() == booksAdd) {
            new AddBookWindow(this);
            
        } else if (ae.getSource() == booksDel) {
            
            
        } else if (ae.getSource() == booksIssue) {
            
            
        } else if (ae.getSource() == booksReturn) {
            
            
        } else if (ae.getSource() == memView) {
            
            
        } else if (ae.getSource() == memAdd) {
            
            
        } else if (ae.getSource() == memDel) {
            try {
                String s = javax.swing.JOptionPane.showInputDialog(this, "Enter patron ID to delete:");
                if (s != null && s.length() > 0) {
                    int id = Integer.parseInt(s);
                    int rc = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to delete patron #" + id + "?", "Confirm Delete", javax.swing.JOptionPane.YES_NO_OPTION);
                    if (rc == javax.swing.JOptionPane.YES_OPTION) {
                        new DeletePatron(id).execute(library, java.time.LocalDate.now());
                        displayPatrons();
                    }
                }
            } catch (NumberFormatException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Invalid id", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            } catch (LibraryException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }

    public void displayBooks() {
        List<Book> booksList = library.getBooks();
        // headers for the table
        String[] columns = new String[]{"Title", "Author", "Pub Date", "Status"};

        Object[][] data = new Object[booksList.size()][6];
        for (int i = 0; i < booksList.size(); i++) {
            Book book = booksList.get(i);
            data[i][0] = book.getTitle();
            data[i][1] = book.getAuthor();
            data[i][2] = book.getPublicationYear();
            data[i][3] = book.getStatus();
        }

        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }
    public void displayPatrons() {
        java.util.List<Patron> patronsList = library.getPatrons();
        String[] columns = new String[]{"ID", "Name", "Phone", "# Borrowed"};
        Object[][] data = new Object[patronsList.size()][4];
        for (int i = 0; i < patronsList.size(); i++) {
            Patron p = patronsList.get(i);
            data[i][0] = p.getId();
            data[i][1] = p.getName();
            data[i][2] = p.getPhone();
            data[i][3] = p.getBooks().size();
        }
        JTable table = new JTable(data, columns);
        this.getContentPane().removeAll();
        this.getContentPane().add(new JScrollPane(table));
        this.revalidate();
    }
}