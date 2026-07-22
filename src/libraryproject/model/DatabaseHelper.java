package libraryproject.model;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHelper {

    private static final String URL = "jdbc:h2:F:/libraryDB";
    private Connection conn;

    public DatabaseHelper() {
        try {
            conn = DriverManager.getConnection(URL, "sa", "");
            createTables();
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private void createTables() throws SQLException {
        Statement stmt = conn.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS books (" +
                "bookId INT PRIMARY KEY," +
                "title VARCHAR(100)," +
                "author VARCHAR(100)," +
                "category VARCHAR(50)," +
                "pages INT," +
                "available BOOLEAN)");

        stmt.execute("CREATE TABLE IF NOT EXISTS members (" +
                "memberId INT PRIMARY KEY," +
                "memberName VARCHAR(100)," +
                "contactNumber VARCHAR(20))");

        stmt.close();
        System.out.println("Tables created successfully!");
    }

    public void addBook(int bookId, String title, String author,
                        String category, int pages, boolean available) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO books VALUES (?,?,?,?,?,?)");
            ps.setInt(1, bookId);
            ps.setString(2, title);
            ps.setString(3, author);
            ps.setString(4, category);
            ps.setInt(5, pages);
            ps.setBoolean(6, available);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    public void deleteBook(int bookId) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM books WHERE bookId=?");
            ps.setInt(1, bookId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }

    public void updateBookAvailability(int bookId, boolean available) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE books SET available=? WHERE bookId=?");
            ps.setBoolean(1, available);
            ps.setInt(2, bookId);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error updating book: " + e.getMessage());
        }
    }

    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> list = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            while (rs.next()) {
                PrintedBook b = new PrintedBook(
                    rs.getInt("bookId"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("category"),
                    rs.getInt("pages")
                );
                b.setAvailable(rs.getBoolean("available"));
                list.add(b);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error getting books: " + e.getMessage());
        }
        return list;
    }

    public void addMember(int memberId, String memberName, String contactNumber) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO members VALUES (?,?,?)");
            ps.setInt(1, memberId);
            ps.setString(2, memberName);
            ps.setString(3, contactNumber);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error adding member: " + e.getMessage());
        }
    }

    public ArrayList<Member> getAllMembers() {
        ArrayList<Member> list = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM members");
            while (rs.next()) {
                Member m = new Member(
                    rs.getInt("memberId"),
                    rs.getString("memberName"),
                    rs.getString("contactNumber")
                );
                list.add(m);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error getting members: " + e.getMessage());
        }
        return list;
    }

    public void closeConnection() {
        try {
            if (conn != null) conn.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
