package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Institution;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("Select c from Category c")
    List<Category> getCategory();

    @Query("Select c from Category c where c.active = true")
    List<Category> getActiveCategory();

    @Query("Select c from Category c where c.id = ?1")
    Category category(Long id);
}
