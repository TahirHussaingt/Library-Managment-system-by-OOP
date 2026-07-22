package libraryproject.model;

public class PrintedBook extends Book {
    private int pages;

    public PrintedBook() {
        super();
    }

   public PrintedBook(int bookId, String title, String author, String category, int pages) {
    super(bookId, title, author);
    setCategory(category);
    this.pages = pages;
}

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public void displayDetails() {
        System.out.println("---- Printed Book Details ----");
        System.out.println("Book ID: " + getBookId());
        System.out.println("Title: " + getTitle());
        System.out.println("Author: " + getAuthor());
        System.out.println("Pages: " + pages);
        System.out.println("Available: " + isAvailable());
    }
}