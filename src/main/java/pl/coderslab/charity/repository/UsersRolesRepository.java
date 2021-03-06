package pl.coderslab.charity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.charity.entity.UsersRoles;

import java.util.List;

@Repository
public interface UsersRolesRepository extends JpaRepository<UsersRoles, Long> {

    @Query("Select u from UsersRoles u where u.active = true")
    List<UsersRoles> getUsersRoles();

    @Query("Select u.id from UsersRoles u where u.active = true")
    List<Long> getUsersRolesId();

    @Query("Select u from UsersRoles u where u.active = false")
    List<UsersRoles> getDeactivatedUsersRoles();

    @Query(value="select ur.role from users u inner join users_roles ur on u.users_roles_id = ur.id where email = ?1",nativeQuery = true)
    String getUsersRolesByEmail(String email);
}
