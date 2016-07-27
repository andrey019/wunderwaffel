package andrey019.model;

import javax.persistence.*;

@Entity
@Table(name = "user_confirmation")
public class UserConfirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String confirmationCode;
}
