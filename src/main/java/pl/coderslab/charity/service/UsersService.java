package pl.coderslab.charity.service;

import pl.coderslab.charity.entity.Users;

import java.util.List;

public interface UsersService {

    void add(Users users);

    void addWithoutCodePass(Users users);

    List<Users> getUsers();

    Users findById(Long id);

    List<Users> getUser(Long id);

    void deactivateUsers(Long id);

    void activateUsers(Long id);

    void deleteUsers(Long id);

    String FindUsernameByEmail(String email);

    Long FindUserIdByEmail(String email);

    Users getUsersById(Long id);

}
