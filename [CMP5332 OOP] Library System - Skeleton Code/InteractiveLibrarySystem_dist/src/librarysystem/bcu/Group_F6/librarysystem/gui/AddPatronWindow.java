package bcu.Group_F6.librarysystem.gui;

import bcu.Group_F6.librarysystem.commands.AddPatron;
import bcu.Group_F6.librarysystem.commands.Command;
import bcu.Group_F6.librarysystem.main.LibraryException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AddPatronWindow extends JFrame implements ActionListener {

    private bcu.Group_F6.librarysystem.gui.MainWindow mw;
    private JTextField nameText = new JTextField();
    private JTextField phoneText = new JTextField();
    private JTextField emailText = new JTextField();

    private JButton addBtn = new JButton("Add");
    private JButton cancelBtn = new JButton("Cancel");

    public AddPatronWindow(bcu.Group_F6.librarysystem.gui.MainWindow mw) {
        this.mw = mw;
        initialize();
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // ignore
        }

        setTitle("Add a New Patron");
        setSize(400, 300);
        setLocationRelativeTo(mw);

        // Main Layout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 2, 10, 15));
        
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font textFont = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        formPanel.add(nameLabel);
        
        nameText.setFont(textFont);
        formPanel.add(nameText);
        
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(labelFont);
        formPanel.add(phoneLabel);
        
        phoneText.setFont(textFont);
        formPanel.add(phoneText);
        
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        formPanel.add(emailLabel);
        
        emailText.setFont(textFont);
        formPanel.add(emailText);
        
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
            addPatron();
        } else if (ae.getSource() == cancelBtn) {
            this.setVisible(false);
        }
    }

    private void addPatron() {
        try {
            String name = nameText.getText().trim();
            String phone = phoneText.getText().trim();
            String email = emailText.getText().trim();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (phone.length() == 0) {
                JOptionPane.showMessageDialog(this, "Phone cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (email.length() == 0) {
                JOptionPane.showMessageDialog(this, "Email cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int rc = JOptionPane.showConfirmDialog(this, "Add patron '" + name + "' with phone '" + phone + "'?", "Confirm Add", JOptionPane.YES_NO_OPTION);
            if (rc != JOptionPane.YES_OPTION) return;
            Command addPatron = new AddPatron(name, phone, email);
            addPatron.execute(mw.getLibrary(), LocalDate.now());
            mw.saveData();
            mw.displayPatrons();
            this.setVisible(false);
        } catch (LibraryException ex) {
            JOptionPane.showMessageDialog(this, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
