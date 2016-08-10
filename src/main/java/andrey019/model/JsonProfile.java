package andrey019.model;


import andrey019.model.dao.User;

public class JsonProfile {

    private String fName;
    private String lName;
    private String password;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFromUser(User user) {
        fName = user.getfName();
        lName = user.getlName();
        password = null;
    }
}
