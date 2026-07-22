package libraryproject.gui;

import libraryproject.model.*;
import libraryproject.exception.BookNotAvailableException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LibraryFrame extends JFrame {

    private DatabaseHelper db = new DatabaseHelper();
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Member> members = new ArrayList<>();

    private JTabbedPane tabs;

    // Book tab components
    private JTextField tfBookId, tfTitle, tfAuthor, tfPages;
    private JComboBox<String> cbCategory;
    private JTextArea taRecords;
    private JButton btnAddBook, btnDeleteBook, btnClearForm;

    // Issue/Return tab components
    private JTextField tfIssueBookId, tfMemberName;
    private JTextField tfReturnBookId;
    private JTextArea taIssueLog;
    private JButton btnIssue, btnReturn;

    // Member tab components
    private JTextField tfMemberId, tfMemberName2, tfContact;
    private JTextArea taMemberRecords;
    private JButton btnRegister, btnClearMember;

    public LibraryFrame() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        // Header
        JLabel header = new JLabel("  Library Management System", JLabel.LEFT);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(30, 80, 160));
        header.setOpaque(true);
        header.setPreferredSize(new Dimension(900, 50));

        // Create tabs
        tabs = new JTabbedPane();
        tabs.addTab("Books", buildBookPanel());
        tabs.addTab("Issue/Return", buildIssuePanel());
        tabs.addTab("Members", buildMemberPanel());

        add(header, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);

        // Add some demo data
        seedData();
        refreshBookRecords();
        refreshMemberRecords();
    }

    // ═══════════════════════════════════════════════════════════════
    // TAB 1: BOOKS
    // ═══════════════════════════════════════════════════════════════

    private JPanel buildBookPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left side - form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Add Book"));
        form.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        tfBookId = new JTextField(15);
        tfTitle = new JTextField(15);
        tfAuthor = new JTextField(15);
        tfPages = new JTextField(15);
        cbCategory = new JComboBox<>(new String[]{"Science", "Computing", "Literature", "History"});

        // Add labels and fields
        g.gridx = 0; g.gridy = 0;
        form.add(new JLabel("Book ID:"), g);
        g.gridx = 1;
        form.add(tfBookId, g);

        g.gridx = 0; g.gridy = 1;
        form.add(new JLabel("Title:"), g);
        g.gridx = 1;
        form.add(tfTitle, g);

        g.gridx = 0; g.gridy = 2;
        form.add(new JLabel("Author:"), g);
        g.gridx = 1;
        form.add(tfAuthor, g);

        g.gridx = 0; g.gridy = 3;
        form.add(new JLabel("Category:"), g);
        g.gridx = 1;
        form.add(cbCategory, g);

        g.gridx = 0; g.gridy = 4;
        form.add(new JLabel("Pages:"), g);
        g.gridx = 1;
        form.add(tfPages, g);

        // Buttons
        btnAddBook = new JButton("Add Book");
        btnDeleteBook = new JButton("Delete Book");
        btnClearForm = new JButton("Clear");

        btnAddBook.addActionListener(e -> addBook());
        btnDeleteBook.addActionListener(e -> deleteBook());
        btnClearForm.addActionListener(e -> clearBookForm());

        g.gridx = 0; g.gridy = 5; g.gridwidth = 2;
        g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.CENTER;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        btnPanel.add(btnAddBook);
        btnPanel.add(btnDeleteBook);
        btnPanel.add(btnClearForm);
        form.add(btnPanel, g);

        // Right side - records
        taRecords = new JTextArea();
        taRecords.setEditable(false);
        taRecords.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scroll = new JScrollPane(taRecords);
        scroll.setBorder(BorderFactory.createTitledBorder("Book Records"));

        panel.add(form, BorderLayout.WEST);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════════════════════
    // TAB 2: ISSUE/RETURN
    // ═══════════════════════════════════════════════════════════════

    private JPanel buildIssuePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Top - Issue & Return forms
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        // Issue form
        JPanel issueForm = new JPanel(new GridBagLayout());
        issueForm.setBorder(BorderFactory.createTitledBorder("Issue Book"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.fill = GridBagConstraints.HORIZONTAL;

        tfIssueBookId = new JTextField(15);
        tfMemberName = new JTextField(15);

        g.gridx = 0; g.gridy = 0;
        issueForm.add(new JLabel("Book ID:"), g);
        g.gridx = 1;
        issueForm.add(tfIssueBookId, g);

        g.gridx = 0; g.gridy = 1;
        issueForm.add(new JLabel("Member Name:"), g);
        g.gridx = 1;
        issueForm.add(tfMemberName, g);

        btnIssue = new JButton("Issue Book");
        btnIssue.addActionListener(e -> issueBook());
        g.gridx = 0; g.gridy = 2; g.gridwidth = 2;
        g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.CENTER;
        issueForm.add(btnIssue, g);

        // Return form
        JPanel returnForm = new JPanel(new GridBagLayout());
        returnForm.setBorder(BorderFactory.createTitledBorder("Return Book"));
        GridBagConstraints g2 = new GridBagConstraints();
        g2.insets = new Insets(5, 5, 5, 5);
        g2.fill = GridBagConstraints.HORIZONTAL;

        tfReturnBookId = new JTextField(15);

        g2.gridx = 0; g2.gridy = 0;
        returnForm.add(new JLabel("Book ID:"), g2);
        g2.gridx = 1;
        returnForm.add(tfReturnBookId, g2);

        btnReturn = new JButton("Return Book");
        btnReturn.addActionListener(e -> returnBook());
        g2.gridx = 0; g2.gridy = 1; g2.gridwidth = 2;
        g2.fill = GridBagConstraints.NONE;
        g2.anchor = GridBagConstraints.CENTER;
        returnForm.add(btnReturn, g2);

        topPanel.add(issueForm);
        topPanel.add(returnForm);

        // Bottom - Log
        taIssueLog = new JTextArea();
        taIssueLog.setEditable(false);
        taIssueLog.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane logScroll = new JScrollPane(taIssueLog);
        logScroll.setBorder(BorderFactory.createTitledBorder("Transaction Log"));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(logScroll, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════════════════════
    // TAB 3: MEMBERS
    // ═══════════════════════════════════════════════════════════════

    private JPanel buildMemberPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left - form
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Register Member"));
        form.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        tfMemberId = new JTextField(15);
        tfMemberName2 = new JTextField(15);
        tfContact = new JTextField(15);

        g.gridx = 0; g.gridy = 0;
        form.add(new JLabel("Member ID:"), g);
        g.gridx = 1;
        form.add(tfMemberId, g);

        g.gridx = 0; g.gridy = 1;
        form.add(new JLabel("Name:"), g);
        g.gridx = 1;
        form.add(tfMemberName2, g);

        g.gridx = 0; g.gridy = 2;
        form.add(new JLabel("Contact:"), g);
        g.gridx = 1;
        form.add(tfContact, g);

        btnRegister = new JButton("Register");
        btnClearMember = new JButton("Clear");
        btnRegister.addActionListener(e -> registerMember());
        btnClearMember.addActionListener(e -> clearMemberForm());

        g.gridx = 0; g.gridy = 3; g.gridwidth = 2;
        g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.CENTER;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        btnPanel.add(btnRegister);
        btnPanel.add(btnClearMember);
        form.add(btnPanel, g);

        // Right - records
        taMemberRecords = new JTextArea();
        taMemberRecords.setEditable(false);
        taMemberRecords.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane scroll = new JScrollPane(taMemberRecords);
        scroll.setBorder(BorderFactory.createTitledBorder("Member Records"));

        panel.add(form, BorderLayout.WEST);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    // ═══════════════════════════════════════════════════════════════
    // BUTTON ACTIONS
    // ═══════════════════════════════════════════════════════════════

    private void addBook() {
        try {
            int id = Integer.parseInt(tfBookId.getText());
            String title = tfTitle.getText();
            String author = tfAuthor.getText();
            String category = (String) cbCategory.getSelectedItem();
            int pages = Integer.parseInt(tfPages.getText());

            Book b = new PrintedBook(id, title, author, category, pages);
            books.add(b);
            db.addBook(id, title, author, category, pages, true);
            refreshBookRecords();
            clearBookForm();

            JOptionPane.showMessageDialog(this, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Book ID and Pages must be numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBook() {
        try {
            int id = Integer.parseInt(tfBookId.getText());
            Book toDelete = findBook(id);
            if (toDelete == null) {
                JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            books.remove(toDelete);
            db.deleteBook(id);
            refreshBookRecords();
            clearBookForm();
            JOptionPane.showMessageDialog(this, "Book deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid Book ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearBookForm() {
        tfBookId.setText("");
        tfTitle.setText("");
        tfAuthor.setText("");
        tfPages.setText("");
    }

    private void issueBook() {
        try {
            int bookId = Integer.parseInt(tfIssueBookId.getText());
            String member = tfMemberName.getText();

            Book book = findBook(bookId);
            if (book == null) {
                JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!book.isAvailable()) {
                throw new BookNotAvailableException("Book is already issued.");
            }

            book.setAvailable(false);
            db.updateBookAvailability(bookId, false);
            refreshBookRecords();
            taIssueLog.append("✓ Book " + bookId + " issued to " + member + "\n");
            tfIssueBookId.setText("");
            tfMemberName.setText("");
            JOptionPane.showMessageDialog(this, "Book issued successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid Book ID.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (BookNotAvailableException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnBook() {
        try {
            int bookId = Integer.parseInt(tfReturnBookId.getText());
            Book book = findBook(bookId);

            if (book == null) {
                JOptionPane.showMessageDialog(this, "Book not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            book.setAvailable(true);
            db.updateBookAvailability(bookId, true);
            refreshBookRecords();
            taIssueLog.append("✓ Book " + bookId + " returned\n");
            tfReturnBookId.setText("");
            JOptionPane.showMessageDialog(this, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter valid Book ID.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerMember() {
        try {
            int id = Integer.parseInt(tfMemberId.getText());
            String name = tfMemberName2.getText();
            String contact = tfContact.getText();

            Member m = new Member(id, name, contact);
            members.add(m);
            db.addMember(id, name, contact);
            refreshMemberRecords();
            clearMemberForm();

            JOptionPane.showMessageDialog(this, "Member registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Member ID must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearMemberForm() {
        tfMemberId.setText("");
        tfMemberName2.setText("");
        tfContact.setText("");
    }

    // ═══════════════════════════════════════════════════════════════
    // HELPERS
    // ═══════════════════════════════════════════════════════════════

    private Book findBook(int id)
    {
        for (Book b : books) {
            if (b.getBookId() == id) return b;
        }
        return null;
    }

    private void refreshBookRecords() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Total Books: ").append(books.size()).append("\n");
        for (Book b : books) {
            sb.append(b.getBookId()).append(" | ").append(b.getTitle()).append(" | ")
              .append(b.getAuthor()).append(" | ").append(b.isAvailable() ? "Available" : "Issued").append("\n");
        }
        taRecords.setText(sb.toString());
    }

    private void refreshMemberRecords() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Total Members: ").append(members.size()).append("\n");
        for (Member m : members) {
            sb.append(m.toString()).append("\n");
        }
        taMemberRecords.setText(sb.toString());
    }

    private void seedData() 
    {
    books = db.getAllBooks();
    members = db.getAllMembers();
   }
}