package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;

import java.util.List;


@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {

    @Query("Select i from Institution i order by i.active desc")
    List<Institution> getInstitution();

    @Query("Select i from Institution i where i.active = true order by i.active desc")
    List<Institution> getActiveInstitution();

    @Query("Select i from Institution i where i.id % 2 = 0 and i.active = true")
    List<Institution> getInstitutionEven();

    @Query("Select i from Institution i where i.id % 2 = 1 and i.active = true")
    List<Institution> getInstitutionOdd();

    @Query("Select i from Institution i where i.id = ?1")
    Institution getInstitutionById(Long id);

}
