package libraryproject.model;

public class EBook extends Book {
    private double fileSize;

    public EBook() {
        super();
    }

    public EBook(int bookId, String title, String author, double fileSize) {
        super(bookId, title, author);
        this.fileSize = fileSize;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public void displayDetails() {
        System.out.println("---- EBook Details ----");
        System.out.println("Book ID: " + getBookId());
        System.out.println("Title: " + getTitle());
        System.out.println("Author: " + getAuthor());
        System.out.println("File Size: " + fileSize + " MB");
        System.out.println("Available: " + isAvailable());
    }
}