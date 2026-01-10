package bcu.Group_F6.librarysystem.gui;

import bcu.Group_F6.librarysystem.commands.BorrowBook;
import bcu.Group_F6.librarysystem.commands.DeleteBook;
import bcu.Group_F6.librarysystem.commands.DeletePatron;
import bcu.Group_F6.librarysystem.commands.RenewBook;
import bcu.Group_F6.librarysystem.commands.ReturnBook;
import bcu.Group_F6.librarysystem.main.LibraryException;
import bcu.Group_F6.librarysystem.model.*;
import bcu.Group_F6.librarysystem.data.LibraryData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    private JMenuItem booksRenew;
    
    private JMenu loansMenu;
    private JMenuItem loansView;

    private JMenuItem memView;
    private JMenuItem memAdd;
    private JMenuItem memDel;
    private JMenuItem memHistory;

    private Library library;

    public MainWindow(Library library) {
        initialize();
        this.library = library;
    }

    public Library getLibrary() {
        return library;
    }

    public void saveData() {
        try {
            LibraryData.store(library);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Failed to save data: " + ex.getMessage() + "\nRolling back changes...", "Save Error", JOptionPane.ERROR_MESSAGE);
            try {
                // Reload library from last saved state to rollback in-memory changes
                this.library = LibraryData.load();
                // Refresh the view to reflect the rollback
                // We clear the content to force the user to refresh/navigate, ensuring no stale data is shown
                updateContent(new JPanel()); 
                JOptionPane.showMessageDialog(this, "System state has been rolled back to the last successful save.", "Rollback Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception reloadEx) {
                 JOptionPane.showMessageDialog(this, "CRITICAL: Failed to rollback data: " + reloadEx.getMessage(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // ignore
        }

        setTitle("Library Management System");

        // Custom Font
        Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);
        UIManager.put("Label.font", mainFont);
        UIManager.put("Button.font", mainFont);
        UIManager.put("Table.font", mainFont);
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 14));

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //adding adminMenu menu and menu items
        adminMenu = new JMenu("Admin");
        adminMenu.setFont(mainFont);
        menuBar.add(adminMenu);

        adminExit = new JMenuItem("Exit");
        adminExit.setFont(mainFont);
        adminMenu.add(adminExit);
        adminExit.addActionListener(this);

        // adding booksMenu menu and menu items
        booksMenu = new JMenu("Books");
        booksMenu.setFont(mainFont);
        menuBar.add(booksMenu);

        booksView = new JMenuItem("View");
        booksAdd = new JMenuItem("Add");
        booksDel = new JMenuItem("Delete");
        booksIssue = new JMenuItem("Issue");
        booksReturn = new JMenuItem("Return");
        booksRenew = new JMenuItem("Renew");
        
        for (JMenuItem item : new JMenuItem[]{booksView, booksAdd, booksDel, booksIssue, booksReturn, booksRenew}) {
            item.setFont(mainFont);
            booksMenu.add(item);
            item.addActionListener(this);
        }
        
        // adding loansMenu menu and menu items
        loansMenu = new JMenu("Loans");
        loansMenu.setFont(mainFont);
        menuBar.add(loansMenu);
        
        loansView = new JMenuItem("View");
        loansView.setFont(mainFont);
        loansMenu.add(loansView);
        loansView.addActionListener(this);

        // adding membersMenu menu and menu items
        membersMenu = new JMenu("Patrons");
        membersMenu.setFont(mainFont);
        menuBar.add(membersMenu);

        memView = new JMenuItem("View");
        memAdd = new JMenuItem("Add");
        memDel = new JMenuItem("Delete");
        memHistory = new JMenuItem("History");

        for (JMenuItem item : new JMenuItem[]{memView, memAdd, memDel, memHistory}) {
            item.setFont(mainFont);
            membersMenu.add(item);
            item.addActionListener(this);
        }

        setSize(900, 600);
        setLocationRelativeTo(null); // Center on screen

        setVisible(true);
        setAutoRequestFocus(true);
        toFront();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Initial welcome screen
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(Color.WHITE);
        JLabel welcomeLabel = new JLabel("Welcome to Library Management System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(50, 50, 50));
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        
        this.getContentPane().add(welcomePanel);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == adminExit) {
            saveData();
            System.exit(0);
        } else if (ae.getSource() == booksView) {
            displayBooks();

        } else if (ae.getSource() == booksAdd) {
            new AddBookWindow(this);

        } else if (ae.getSource() == booksDel) {
            try {
                String s = javax.swing.JOptionPane.showInputDialog(this, "Enter book ID to delete:");
                if (s != null && s.length() > 0) {
                    int id = Integer.parseInt(s);
                    int rc = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to delete book #" + id + "?", "Confirm Delete", javax.swing.JOptionPane.YES_NO_OPTION);
                    if (rc == javax.swing.JOptionPane.YES_OPTION) {
                        new DeleteBook(id).execute(library, java.time.LocalDate.now());
                        saveData();
                        displayBooks();
                    }
                }
            } catch (NumberFormatException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Invalid id", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            } catch (LibraryException ex) {
                javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }

        } else if (ae.getSource() == booksIssue) {
            new IssueBookWindow(this);

        } else if (ae.getSource() == booksReturn) {
            new bcu.Group_F6.librarysystem.gui.ReturnBookWindow(this);

        } else if (ae.getSource() == booksRenew) {
            new bcu.Group_F6.librarysystem.gui.RenewBookWindow(this);

        } else if (ae.getSource() == loansView) {
             displayLoans();
             
        } else if (ae.getSource() == memView) {
            displayPatrons();
            
        } else if (ae.getSource() == memHistory) {
            try {
                 String patronStr = javax.swing.JOptionPane.showInputDialog(this, "Enter patron ID:");
                 if (patronStr != null && patronStr.length() > 0) {
                     int patronId = Integer.parseInt(patronStr);
                     Patron patron = library.getPatronByID(patronId);
                     displayPatronHistory(patron);
                 }
            } catch (NumberFormatException ex) {
                 javax.swing.JOptionPane.showMessageDialog(this, "Invalid id", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            } catch (LibraryException ex) {
                 javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }

        } else if (ae.getSource() == memAdd) {
            new AddPatronWindow(this);

        } else if (ae.getSource() == memDel) {
            try {
                String s = javax.swing.JOptionPane.showInputDialog(this, "Enter patron ID to delete:");
                if (s != null && s.length() > 0) {
                    int id = Integer.parseInt(s);
                    int rc = javax.swing.JOptionPane.showConfirmDialog(this, "Are you sure you want to delete patron #" + id + "?", "Confirm Delete", javax.swing.JOptionPane.YES_NO_OPTION);
                    if (rc == javax.swing.JOptionPane.YES_OPTION) {
                        new DeletePatron(id).execute(library, java.time.LocalDate.now());
                        saveData();
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
        // Filter out deleted books
        List<Book> activeBooks = new java.util.ArrayList<>();
        for (Book book : booksList) {
            if (!book.isDeleted()) {
                activeBooks.add(book);
            }
        }
        
        System.out.println("Displaying books. Count: " + activeBooks.size());
        // headers for the table
        String[] columns = new String[]{"Title", "Author", "Pub Date", "Publisher", "Status"};

        Object[][] data = new Object[activeBooks.size()][5];
        for (int i = 0; i < activeBooks.size(); i++) {
            Book book = activeBooks.get(i);
            data[i][0] = book.getTitle();
            data[i][1] = book.getAuthor();
            data[i][2] = book.getPublicationYear();
            data[i][3] = book.getPublisher();
            data[i][4] = book.getStatus();
        }

        JTable table = createStyledTable(data, columns);
        // Add mouse listener for popup
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    Book book = activeBooks.get(row);
                    if (book.isOnLoan()) {
                        Patron patron = book.getLoan().getPatron();
                        JOptionPane.showMessageDialog(MainWindow.this, 
                            "Patron: " + patron.getName() + "\nID: " + patron.getId(), 
                            "Loan Details", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        
        updateContent(new JScrollPane(table));
    }

    public void displayPatrons() {
        java.util.List<Patron> patronsList = library.getPatrons();
        // Filter out deleted patrons
        List<Patron> activePatrons = new java.util.ArrayList<>();
        for (Patron p : patronsList) {
            if (!p.isDeleted()) {
                activePatrons.add(p);
            }
        }

        System.out.println("Displaying patrons. Count: " + activePatrons.size());
        String[] columns = new String[]{"ID", "Name", "Phone", "Email", "# Borrowed"};
        Object[][] data = new Object[activePatrons.size()][5];
        for (int i = 0; i < activePatrons.size(); i++) {
            Patron p = activePatrons.get(i);
            data[i][0] = p.getId();
            data[i][1] = p.getName();
            data[i][2] = p.getPhone();
            data[i][3] = p.getEmail();
            data[i][4] = p.getBooks().size();
        }
        
        JTable table = createStyledTable(data, columns);
        // Add mouse listener for popup
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                if (row >= 0) {
                    Patron patron = activePatrons.get(row);
                    if (!patron.getBooks().isEmpty()) {
                        StringBuilder sb = new StringBuilder("Books on Loan:\n");
                        for (Book b : patron.getBooks()) {
                            sb.append("- ").append(b.getTitle()).append(" (Due: ").append(b.getDueDate()).append(")\n");
                        }
                        JOptionPane.showMessageDialog(MainWindow.this, sb.toString(), 
                            "Borrowed Books", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        updateContent(new JScrollPane(table));
    }

    public void displayLoans() {
        java.util.List<Book> booksList = library.getBooks();
        java.util.List<Loan> loansList = new java.util.ArrayList<>();
        for (Book book : booksList) {
            if (book.isOnLoan()) {
                loansList.add(book.getLoan());
            }
        }
        System.out.println("Displaying loans. Count: " + loansList.size());
        
        String[] columns = new String[]{"Book ID", "Book Title", "Patron ID", "Patron Name", "Start Date", "Due Date"};
        Object[][] data = new Object[loansList.size()][6];
        
        for (int i = 0; i < loansList.size(); i++) {
            Loan loan = loansList.get(i);
            data[i][0] = loan.getBook().getId();
            data[i][1] = loan.getBook().getTitle();
            data[i][2] = loan.getPatron().getId();
            data[i][3] = loan.getPatron().getName();
            data[i][4] = loan.getStartDate();
            data[i][5] = loan.getDueDate();
        }
        
        JTable table = createStyledTable(data, columns);
        updateContent(new JScrollPane(table));
    }

    public void displayPatronHistory(Patron patron) {
        java.util.List<String> history = patron.getHistory();
        java.util.List<Book> currentBooks = patron.getBooks();
        
        java.util.List<String> allHistory = new java.util.ArrayList<>();
        
        // Add current loans
        for (Book book : currentBooks) {
             Loan loan = book.getLoan();
             String entry = "CURRENT: Book: " + book.getTitle() + " (ID: " + book.getId() + ") - Borrowed: " + (loan != null ? loan.getStartDate() : "Unknown") + " - Due: " + (loan != null ? loan.getDueDate() : "Unknown");
             allHistory.add(entry);
        }
        
        // Add past history
        allHistory.addAll(history);
        
        String[] columns = new String[]{"History"};
        Object[][] data = new Object[allHistory.size()][1];
        for (int i = 0; i < allHistory.size(); i++) {
            data[i][0] = allHistory.get(i);
        }
        JTable table = createStyledTable(data, columns);
        
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("History for " + patron.getName() + " (ID: " + patron.getId() + ")");
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(label, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        
        updateContent(panel);
    }
    
    private JTable createStyledTable(Object[][] data, String[] columns) {
        JTable table = new JTable(data, columns);
        table.setRowHeight(30);
        table.setFillsViewportHeight(true);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(Color.BLACK);
        
        // Alternating row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
                }
                if (column == 3 || column == 4) { // Status or Counts
                     setHorizontalAlignment(SwingConstants.CENTER);
                } else {
                     setHorizontalAlignment(SwingConstants.LEFT);
                }
                // Add some padding
                if (c instanceof JLabel) {
                    ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                }
                return c;
            }
        });
        
        return table;
    }
    
    private void updateContent(JComponent component) {
        this.getContentPane().removeAll();
        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.add(component, BorderLayout.CENTER);
        this.getContentPane().add(container, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}
