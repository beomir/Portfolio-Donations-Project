package pl.coderslab.charity.service;

import pl.coderslab.charity.entity.UsersRoles;

import java.util.List;

public interface UsersRolesService {

    void add(UsersRoles usersroles);

    List<UsersRoles> getUsersRoles();

    List<UsersRoles> getDeactivatedUsersRoles();

    UsersRoles findById(Long id);

    void delete(Long id);

    void activate(Long id);

    List<Long> getUsersRolesId();

    String getUsersRolesByEmail(String email);

}
