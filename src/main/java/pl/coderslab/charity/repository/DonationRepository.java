package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;

import java.util.List;


@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("Select d from Donation d join fetch d.categories c")
    List<Donation> getDonation();

    @Query(value = "Select count(*) from donations",nativeQuery = true)
    int QtyOfDonation();

    @Query(value = "Select sum(quantity) from donations",nativeQuery = true)
    int SumOfDonation();

    @Query("Select distinct d from Donation d join fetch d.users u join fetch d.categories c where u.email = ?1")
    List<Donation> getDonationByUserEmail(String email);

    @Query("Select d from Donation d where d.id = ?1")
    Donation getDonationById(Long id);

}
