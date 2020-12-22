package pl.coderslab.charity.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    private String created;
    private String last_update;
    private String changeBy;
    private boolean active;

    public Category(Long id, String name,String created,String last_update,String changeBy,boolean active) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.last_update = last_update;
        this.changeBy = changeBy;
        this.active = active;
    }

    @ManyToMany(mappedBy="categories")
    List<Donation> donations = new ArrayList<>();


    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
