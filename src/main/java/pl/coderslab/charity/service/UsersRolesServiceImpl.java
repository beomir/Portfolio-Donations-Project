package pl.coderslab.charity.service;


import org.springframework.stereotype.Service;
import pl.coderslab.charity.app.SecurityUtils;
import pl.coderslab.charity.entity.UsersRoles;
import pl.coderslab.charity.repository.UsersRolesRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class UsersRolesServiceImpl implements UsersRolesService{
    private final UsersRolesRepository usersRolesRepository;

    public UsersRolesServiceImpl(UsersRolesRepository usersRolesRepository) {
        this.usersRolesRepository = usersRolesRepository;
    }


    @Override
    public void add(UsersRoles usersroles) {
        usersRolesRepository.save(usersroles);
    }

    @Override
    public List<UsersRoles> getUsersRoles() {
        return usersRolesRepository.getUsersRoles();
    }

    @Override
    public List<UsersRoles> getDeactivatedUsersRoles() {
        return usersRolesRepository.getDeactivatedUsersRoles();
    }

    @Override
    public UsersRoles findById(Long id) {
        return usersRolesRepository.getOne(id);
    }

    @Override
    public void delete(Long id) {
        UsersRoles usersRoles = usersRolesRepository.getOne(id);
        usersRoles.setActive(false);
        usersRoles.setLast_update(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        usersRoles.setChangeBy(SecurityUtils.usernameForActivations());
        usersRolesRepository.save(usersRoles);
    }

    @Override
    public void activate(Long id) {
        UsersRoles usersRoles = usersRolesRepository.getOne(id);
        usersRoles.setActive(true);
        usersRoles.setLast_update(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        usersRoles.setChangeBy(SecurityUtils.usernameForActivations());
        usersRolesRepository.save(usersRoles);
    }

    @Override
    public List<Long> getUsersRolesId() {
        return usersRolesRepository.getUsersRolesId();
    }

    @Override
    public String getUsersRolesByEmail(String email) {
        return usersRolesRepository.getUsersRolesByEmail(email);
    }
}
