package libraryproject.model;

import libraryproject.exception.BookNotAvailableException;
import java.util.ArrayList;

public class Library {
    private ArrayList<Book> books;
    private ArrayList<Member> members;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public Book findBookById(int bookId) {
        for (Book b : books) {
            if (b.getBookId() == bookId) {
                return b;
            }
        }
        return null;
    }

    public void issueBook(int bookId) throws BookNotAvailableException {
        Book book = findBookById(bookId);

        if (book == null) {
            throw new BookNotAvailableException("Book with ID " + bookId + " does not exist.");
        }

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book \"" + book.getTitle() + "\" is already issued.");
        }

        book.setAvailable(false);
    }

    public void returnBook(int bookId) throws BookNotAvailableException {
        Book book = findBookById(bookId);

        if (book == null) {
            throw new BookNotAvailableException("Book with ID " + bookId + " does not exist.");
        }

        book.setAvailable(true);
    }
}