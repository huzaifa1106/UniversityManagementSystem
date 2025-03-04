package Backend;

public class Admin extends User {

    public Admin(String u, String p, String r) {
        super(u, p, r);
    }

    public void changeStudentPassword(String p, Student s) {
        s.setPassword(p);
    }

    public void changeStudentRole(String r, Student s) {
        s.setRole(r);
    }
}
