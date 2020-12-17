package pl.coderslab.charity.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
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

    @OneToMany(mappedBy="category")
    private List<Donation> donationList = new ArrayList<>();
}
