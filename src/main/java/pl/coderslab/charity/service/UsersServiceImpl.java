package pl.coderslab.charity.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.app.SecurityUtils;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.entity.Users;
import pl.coderslab.charity.repository.UsersRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UsersServiceImpl implements pl.coderslab.charity.service.UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public void add(Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepository.save(users);
    }

    @Override
    public void addWithoutCodePass(Users users) {
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
    public void deleteUsers(Long id) {
        if (!usersRepository.readyToDelete(id)) {
            usersRepository.deleteById(id);
        }

    }
}
