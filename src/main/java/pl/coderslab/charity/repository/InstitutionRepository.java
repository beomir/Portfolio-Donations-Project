package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entity.Institution;

import java.util.List;


@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    @Query("Select i from Institution i")
    List<Institution> getInstitution();

    @Query("Select i from Institution i where i.id % 2 = 0")
    List<Institution> getInstitutionEven();

    @Query("Select i from Institution i where i.id % 2 = 1")
    List<Institution> getInstitutionOdd();
}
