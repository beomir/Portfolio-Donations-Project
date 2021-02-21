package pl.coderslab.charity.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;


    @ManyToMany
    List<Category> categories = new ArrayList<>();

    @NotNull
    @ManyToOne
    private Institution institution;

    private String street;
    private String city;
    private String zipCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime pickUpTime;
    private String pickUpComment;
    private String phone;

    @NotNull
    @ManyToOne
    private Users users;

    private String created;
    private String last_update;
    private String changeBy;
    private boolean active;
    private boolean expired;
    private String specNumber;

    public Donation(Long id, int quantity, Institution institution, String street, String city, String zipCode, LocalDate pickUpDate, LocalTime pickUpTime, String pickUpComment, String phone, Users users, String created, String last_update, String changeBy, boolean active, boolean expired,String specNumber) {
        this.id = id;
        this.quantity = quantity;
        this.institution = institution;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.pickUpDate = pickUpDate;
        this.pickUpTime = pickUpTime;
        this.pickUpComment = pickUpComment;
        this.phone = phone;
        this.users = users;
        this.created = created;
        this.last_update = last_update;
        this.changeBy = changeBy;
        this.active = active;
        this.expired = expired;
        this.specNumber = specNumber;
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", pickUpDate=" + pickUpDate +
                ", pickUpTime=" + pickUpTime +
                ", pickUpComment='" + pickUpComment + '\'' +
                ", phone='" + phone + '\'' +
                ", users=" + users +
                ", created='" + created + '\'' +
                ", last_update='" + last_update + '\'' +
                ", changeBy='" + changeBy + '\'' +
                ", active=" + active +
                ", expired=" + expired +
                ", specNumber=" + specNumber +
                '}';
    }
}
