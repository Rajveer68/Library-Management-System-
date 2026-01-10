package bcu.Group_F6.librarysystem.gui;

import bcu.Group_F6.librarysystem.commands.ReturnBook;
import bcu.Group_F6.librarysystem.commands.Command;
import bcu.Group_F6.librarysystem.main.LibraryException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class ReturnBookWindow extends JFrame implements ActionListener {

    private bcu.Group_F6.librarysystem.gui.MainWindow mw;
    private JTextField patronIdText = new JTextField();
    private JTextField bookIdText = new JTextField();

    private JButton returnBtn = new JButton("Return");
    private JButton cancelBtn = new JButton("Cancel");

    public ReturnBookWindow(bcu.Group_F6.librarysystem.gui.MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {

        }

        setTitle("Return Book from Patron");
        setSize(300, 150);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(3, 2));
        topPanel.add(new JLabel("Patron ID : "));
        topPanel.add(patronIdText);
        topPanel.add(new JLabel("Book ID : "));
        topPanel.add(bookIdText);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1, 3));
        bottomPanel.add(new JLabel("     "));
        bottomPanel.add(returnBtn);
        bottomPanel.add(cancelBtn);

        returnBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        this.getContentPane().add(topPanel, BorderLayout.CENTER);
        this.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(mw);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == returnBtn) {
            returnBook();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    private void returnBook() {
        try {
            String ps = patronIdText.getText().trim();
            String bs = bookIdText.getText().trim();
            if (ps.length() == 0 || bs.length() == 0) {
                JOptionPane.showMessageDialog(this, "Please enter both Patron ID and Book ID.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int patronId = Integer.parseInt(ps);
            int bookId = Integer.parseInt(bs);
            int rc = JOptionPane.showConfirmDialog(this, "Return book #" + bookId + " from patron #" + patronId + "?", "Confirm Return", JOptionPane.YES_NO_OPTION);
            if (rc != JOptionPane.YES_OPTION) return;
            Command cmd = new ReturnBook(patronId, bookId);
            cmd.execute(mw.getLibrary(), LocalDate.now());
            mw.saveData();
            mw.displayBooks();
            mw.displayPatrons();
            this.setVisible(false);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid id(s)", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
