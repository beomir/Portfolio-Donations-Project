package pl.coderslab.charity.fixtures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.charity.app.TimeUtils;
import pl.coderslab.charity.entity.UsersRoles;
import pl.coderslab.charity.service.UsersRolesService;

import java.util.Arrays;
import java.util.List;


@Component
public class UsersRolesFixture {
    private UsersRolesService usersRolesService;


    private List<UsersRoles> usersRolesList = Arrays.asList(
            new UsersRoles(null, "ROLE_USER", TimeUtils.timeNowLong(),TimeUtils.timeNowLong(), true, "User with access only to content","system"),
            new UsersRoles(null, "ROLE_ADMIN", TimeUtils.timeNowLong(),TimeUtils.timeNowLong(), true, "User with access to content and configuration panel","system")
    );

    @Autowired
    public UsersRolesFixture(UsersRolesService usersRolesService) {
        this.usersRolesService = usersRolesService;
    }

    public void loadIntoDB() {
        for (UsersRoles usersRoles : usersRolesList) {
            usersRolesService.add(usersRoles);
        }
    }
}
