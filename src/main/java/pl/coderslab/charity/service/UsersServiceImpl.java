package pl.coderslab.charity.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.app.SecurityUtils;
import pl.coderslab.charity.app.TimeUtils;
import pl.coderslab.charity.entity.Users;
import pl.coderslab.charity.repository.UsersRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UsersServiceImpl implements pl.coderslab.charity.service.UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private boolean registrationValidation;
    private boolean resetPassword;



    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registry(Users users) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[%&@#$^*?_~])(?=\\S+$).{8,}"; // for Test ! is not added
        if(!users.getUsername().contains("admin") && users.getUsername() != null && !users.getUsername().isEmpty() && users.getLastName() != null &&
                !users.getLastName().isEmpty() && users.getEmail() != null && !users.getEmail().isEmpty() && users.getPassword() != null &&
                users.getPassword().matches(pattern)) {
            users.setPassword(passwordEncoder.encode(users.getPassword()));
            users.setActivateToken(SecurityUtils.uuidToken());
            users.setActive(false);
            registrationValidation = true;
            registrationStatus();
            usersRepository.save(users);
        }
        else
        {
            registrationValidation = false;
            registrationStatus();
        }
    }

    @Override
    public void resetPassword(Users users) {
        if(usersRepository.getByEmail(users.getEmail()) != null) {
            String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[%&@#$^*?_~])(?=\\S+$).{8,}"; // for Test ! is not added
            if (users.getPassword().matches(pattern)) {
                users.setId(usersRepository.getByEmail(users.getEmail()).getId());
                users.setChangeBy("Reset Password by: " + SecurityUtils.usernameForActivations());
                users.setLast_update(TimeUtils.timeNowLong());
                users.setUsersRoles(usersRepository.getByEmail(users.getEmail()).getUsersRoles());
                users.setUsername(usersRepository.getByEmail(users.getEmail()).getUsername());
                users.setLastName(usersRepository.getByEmail(users.getEmail()).getLastName());
                users.setCreated(usersRepository.getByEmail(users.getEmail()).getCreated());
                users.setPassword(passwordEncoder.encode(users.getPassword()));
                users.setActivateToken(SecurityUtils.uuidToken());
                users.setActive(true);
                resetPassword = true;
                resetPasswordStatus();
                usersRepository.save(users);
            } else {
                resetPassword = false;
                resetPasswordStatus();
            }
        }
    }

    @Override
    public boolean registrationStatus() {
        return registrationValidation;
    }
    @Override
    public boolean resetPasswordStatus() {
        return resetPassword;
    }

    @Override
    public Users getByEmail(String email) {
        return usersRepository.getByEmail(email);
    }


    @Override
    public void add(Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setActivateToken(SecurityUtils.uuidToken());
        users.setActive(true);
        usersRepository.save(users);
    }

    @Override
    public void addWithoutCodePass(Users users) {
        users.setActivateToken(SecurityUtils.uuidToken());
        users.setActive(true);
        usersRepository.save(users);
    }


    @Override
    public List<Users> getUser(Long id) {
        return usersRepository.getUser(id);
    }


    @Override
    public List<Users> getUsers() {
        return usersRepository.getUsers();
    }


    @Override
    public Users findById(Long id) {
        return usersRepository.getOne(id);
    }



    @Override
    public String FindUsernameByEmail(String email) {
        return usersRepository.FindUsernameByEmail(email);
    }

    @Override
    public Long FindUserIdByEmail(String email) {
        return usersRepository.FindUserIdByEmail(email);
    }

    @Override
    public Users getUsersById(Long id) {
        return usersRepository.getUsersById(id);
    }

    @Override
    public Users getUserByActivateToken(String activateToken) {
        return usersRepository.getUserByActivateToken(activateToken);
    }


    @Override
    public void deactivateUsers(Long id) {
        Users users = usersRepository.getOne(id);
        users.setActive(false);
        users.setLast_update(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        users.setChangeBy(SecurityUtils.usernameForActivations());
        usersRepository.save(users);
    }

    @Override
    public void activateUsers(Long id) {
        Users users = usersRepository.getOne(id);
        users.setActive(true);
        users.setLast_update(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        users.setChangeBy(SecurityUtils.usernameForActivations());
        usersRepository.save(users);
    }

    @Override
    public void setActivateUserAfterEmailValidation(String activateToken){
        Users user = usersRepository.getUserByActivateToken(activateToken);
        user.setActive(true);
        user.setLast_update(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        user.setChangeBy("Activation");
        user.setActivateToken(SecurityUtils.uuidToken());
        usersRepository.save(user);
    }


    @Override
    public void deleteUsers(Long id) {
        Long userId = usersRepository.FindUserIdByEmail(SecurityUtils.usernameForActivations());

        if(usersRepository.donationsQtyForSelectedUser(id) == 0) {

            if (!usersRepository.readyToDelete(id) && !id.equals(userId)) {
                usersRepository.deleteById(id);
            }

        }
        else{
            if (!usersRepository.readyToDelete(id) && !id.equals(userId)) {
                usersRepository.deleteDonationCategoriesForDeletingUser(id);
                usersRepository.deleteDonationsForDeletingUser(id);
                usersRepository.deleteById(id);
            }
        }
    }


}
