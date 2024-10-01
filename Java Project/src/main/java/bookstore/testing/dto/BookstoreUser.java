package bookstore.testing.dto;

import java.util.ArrayList;
import java.util.List;

public class BookstoreUser {

    private String username = "";
    private String userID = "";

    private List<Books> books = new ArrayList();

    public List<Books> getBooks() {
        return books;
    }

    public void setBooks(List<Books> books) {
        this.books = books;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
