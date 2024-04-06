package logic;

public class Account {
    private String username;
    private String websiteName;
    private String password;

    public Account(String username, String websiteName, String password) {
        this.username = username;
        this.websiteName = websiteName;
        this.password = password;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getWebsiteName() {
        return websiteName;
    }
}
