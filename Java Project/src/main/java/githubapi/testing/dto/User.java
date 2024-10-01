package githubapi.testing.dto;

public class User {

    private String login = "";
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public static final String LOGIN = "Vladimir-Lutic";
    public static final int ID = 183031966;

}
