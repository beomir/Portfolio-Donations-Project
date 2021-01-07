package pl.coderslab.charity.service;

import org.springframework.ui.Model;
import pl.coderslab.charity.entity.Users;

import java.util.List;

public interface UsersService {

    void add(Users users);

    void registry(Users users);

    void resetPassword(Users users, String password2);

    void checkData(Users users, String password2);

    void addWithoutCodePass(Users users);

    List<Users> getUsers();

    Users findById(Long id);

    List<Users> getUser(Long id);

    void deactivateUsers(Long id);

    void activateUsers(Long id);

    void deleteUsers(Long id);

    String FindUsernameByEmail(String email);

    Long FindUserIdByEmail(String email);

    String FindUsernameByToken(String email);

    Users getUsersById(Long id);

    Users getUserByActivateToken(String activateToken);

    void setActivateUserAfterEmailValidation(String activateToken);

    boolean registrationStatus();

    boolean resetPasswordStatus();

    boolean changedDataStatus();

    Users getByEmail(String email);

    void loggedUserData(Model model);


}
