package pl.coderslab.charity.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String description;

    private String created;
    private String last_update;
    private String changeBy;
    private boolean active;

    public Institution(Long id, String name,String description,String created,String last_update,String changeBy,boolean active) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.description = description;
        this.last_update = last_update;
        this.changeBy = changeBy;
        this.active = active;
    }

    @OneToMany(mappedBy="institution")
    private List<Donation> donationList = new ArrayList<>();

    @Override
    public String toString() {
        return "Institution{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", created='" + created + '\'' +
                ", last_update='" + last_update + '\'' +
                ", changeBy='" + changeBy + '\'' +
                ", active=" + active +
                '}';
    }
}
