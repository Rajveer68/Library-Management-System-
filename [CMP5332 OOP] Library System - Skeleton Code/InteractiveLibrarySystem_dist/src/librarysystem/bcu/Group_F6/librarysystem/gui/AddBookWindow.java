package bcu.Group_F6.librarysystem.gui;

import bcu.Group_F6.librarysystem.commands.AddBook;
import bcu.Group_F6.librarysystem.commands.Command;
import bcu.Group_F6.librarysystem.main.LibraryException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AddBookWindow extends JFrame implements ActionListener {

    private bcu.Group_F6.librarysystem.gui.MainWindow mw;
    private JTextField titleText = new JTextField();
    private JTextField authText = new JTextField();
    private JTextField pubDateText = new JTextField();
    private JTextField publisherText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    public AddBookWindow(bcu.Group_F6.librarysystem.gui.MainWindow mw) {
        this.mw = mw;
        initialize();
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

        setTitle("Add a New Book");
        setSize(400, 300);
        setLocationRelativeTo(mw);

        // Main Layout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 15));
        
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(labelFont);
        formPanel.add(titleLabel);
        
        titleText.setFont(textFont);
        formPanel.add(titleText);
        
        JLabel authLabel = new JLabel("Author:");
        authLabel.setFont(labelFont);
        formPanel.add(authLabel);
        
        authText.setFont(textFont);
        formPanel.add(authText);
        
        JLabel pubDateLabel = new JLabel("Publishing Date:");
        pubDateLabel.setFont(labelFont);
        formPanel.add(pubDateLabel);
        
        pubDateText.setFont(textFont);
        formPanel.add(pubDateText);
        
        JLabel pubLabel = new JLabel("Publisher:");
        pubLabel.setFont(labelFont);
        formPanel.add(pubLabel);
        
        publisherText.setFont(textFont);
        formPanel.add(publisherText);
        
        contentPanel.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        addBtn.setFont(labelFont);
        cancelBtn.setFont(labelFont);
        
        buttonPanel.add(addBtn);
        buttonPanel.add(cancelBtn);

        addBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        this.getContentPane().add(contentPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBtn) {
            addBook();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }

    }

    private void addBook() {
        try {
            String title = titleText.getText();
            String author = authText.getText();
            String publicationYear = pubDateText.getText();
            String publisher = publisherText.getText();
            // create and execute the AddBook Command
            Command addBook = new AddBook(title, author, publicationYear, publisher);
            addBook.execute(mw.getLibrary(), LocalDate.now());
            // save data
            mw.saveData();
            // refresh the view with the list of books
            mw.displayBooks();
            // hide (close) the AddBookWindow
            this.setVisible(false);
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
