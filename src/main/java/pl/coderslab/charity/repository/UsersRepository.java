package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
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

}
