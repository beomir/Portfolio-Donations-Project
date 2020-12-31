package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.entity.Users;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("Select u from Users u order by u.usersRoles.role desc,u.active desc")
    List<Users> getUsers();


    boolean existsByUsername(String username);
    Users getByUsername(String username);

    boolean existsByEmail(String email);

    @Query("Select u from Users u where u.email = ?1")
    Users getByEmail(String email);

    @Query("Select distinct u from Users u where u.id =?1")
    List<Users> getUser(Long id);

    @Query(value="Select username from users where email = ?1",nativeQuery = true)
    String FindUsernameByEmail(String email);

    @Query(value="Select id from users where email = ?1",nativeQuery = true)
    Long FindUserIdByEmail(String email);

    @Query("Select u from Users u where u.id = ?1")
    Users getUsersById(Long id);

    @Query(value="Select active from users where id = ?1",nativeQuery = true)
    boolean readyToDelete(Long id);

    @Query("Select u from Users u where u.activateToken = ?1")
    Users getUserByActivateToken(String activateToken);

    @Query(value="Select count(d.id) from donations d inner join users u on d.users_id = u.id where u.id = ?1",nativeQuery = true)
    int donationsQtyForSelectedUser(Long id);

    @Modifying
    @Transactional
    @Query(value = "delete dc.* from donations d inner join users on d.users_id = users.id inner join donations_categories dc on d.id = dc.donations_id where users_id = ?1",nativeQuery = true)
    void deleteDonationCategoriesForDeletingUser(Long id);

    @Modifying
    @Transactional
    @Query(value = "delete d.* from donations d inner join users on d.users_id = users.id where users_id = ?1",nativeQuery = true)
    void deleteDonationsForDeletingUser(Long id);


}
