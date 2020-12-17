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

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @ManyToMany(mappedBy="categories")
    List<Donation> donations = new ArrayList<>();
}
