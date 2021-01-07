package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;

import java.util.List;


@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("Select distinct d from Donation d join fetch d.categories c order by d.active desc,d.pickUpDate,d.pickUpTime,d.created")
    List<Donation> getDonation();

    @Query(value = "Select count(*) from donations",nativeQuery = true)
    int QtyOfDonation();

    @Query(value = "Select sum(quantity) from donations",nativeQuery = true)
    int SumOfDonation();

    @Query("Select distinct d from Donation d join fetch d.users u join fetch d.categories c where u.email = ?1 order by d.active desc,d.pickUpDate,d.pickUpTime,d.created")
    List<Donation> getDonationByUserEmail(String email);

    @Query("Select distinct d from Donation d where d.id = ?1 order by d.active desc,d.pickUpDate,d.pickUpTime,d.created")
    Donation getDonationById(Long id);

    @Query("Select distinct d from Donation d where d.specNumber = ?1 order by d.active desc,d.pickUpDate,d.pickUpTime,d.created")
    Donation getDonationBySpecNumber(String specNumber);

}
