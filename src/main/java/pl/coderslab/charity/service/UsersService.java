package pl.coderslab.charity.service;

import pl.coderslab.charity.entity.Users;

import java.util.List;

public interface UsersService {

    void add(Users users);

    void addWithoutCodePass(Users users);

//    void edit(Users users);

    List<Users> getUsers();

    List<Users> getDeactivatedUsers();

    Users findById(Long id);

    List<Users> getUser(Long id);

    void delete(Long id);

    void remove(Long id);

    void activate(Long id);

    String FindUsernameByEmail(String email);

    Long FindUserIdByEmail(String email);

    //To think about solution for this
//    void updateRole(Users users);

}
