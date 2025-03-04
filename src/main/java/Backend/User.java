package Backend;

public class User {
    String username = "";
    private String password = "";
    private String role = "";

    public User(String u, String p, String r) {
        this.username = u;
        this.password = p;
        this.role = r;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRole() {
        return this.role;
    }

    protected void setPassword(String p) {
        this.password = p;
    }

    protected void setRole(String r) {
        this.role = r;
    }

    public String getUsername() {
        return username;
    }
}
