package pl.coderslab.charity.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity

@Table(name="users")
@EqualsAndHashCode(of = "email")
@ToString(exclude = "password")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;

    private String created;
    private String last_update;
    private boolean active;

    @ManyToOne
    private UsersRoles usersRoles;

    @OneToMany(mappedBy="users")
    private List<Donation> donationList = new ArrayList<>();

    private String changeBy;

    public Users(Long id, String username, String password, String created, String last_update, UsersRoles usersRoles, String email, boolean active, String changeBy,String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.created = created;
        this.last_update = last_update;
        this.usersRoles = usersRoles;
        this.email = email;
        this.active = active;
        this.changeBy = changeBy;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", created='" + created + '\'' +
                ", last_update='" + last_update + '\'' +
                ", active=" + active +
                ", changeBy='" + changeBy + '\'' +
                '}';
    }
}
