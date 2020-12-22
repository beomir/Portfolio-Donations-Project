package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entity.Users;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("Select u from Users u where u.active = true")
    List<Users> getUsers();

    @Query("Select u from Users u where u.active = false")
    List<Users> getDeactivatedUsers();


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

}
