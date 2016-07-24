package andrey019.model;

public enum Role {
    USER("USER"),
    DBA("DBA"),
    ADMIN("ADMIN");

    String role;

    private Role(String userProfileType){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}