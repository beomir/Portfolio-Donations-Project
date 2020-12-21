package pl.coderslab.charity.fixtures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.charity.app.TimeUtils;
import pl.coderslab.charity.entity.Users;
import pl.coderslab.charity.entity.UsersRoles;
import pl.coderslab.charity.service.UsersRolesService;
import pl.coderslab.charity.service.UsersService;

import java.util.Arrays;
import java.util.List;

@Component
public class UsersFixture {
    private UsersService usersService;
    private UsersRolesService usersRolesService;

    private List<Users> usersList = Arrays.asList(
             new Users(null, "Magda", "123", TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),null,"StrojemMagdaM@meta.ua",true,"system","Mróz"),
             new Users(null, "Piotr", "123", TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),null,"buDUJEmPiotrP@wp.pl",true,"system","Peterka"),
             new Users(null, "Gaweł", "123", TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),null,"buDUJEmGawelG@wp.pl",true,"system","Graź"),
             new Users(null, "Ruslan", "123", TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),null,"RuslanR@BuldMate.com",true,"system","Rybik"),
             new Users(null, "Fernandos", "123", TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),null,"FernandosF@FNdMdAL.pt",true,"system","Alonsos"),
             new Users(null, "Benito", "123", TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),null,"BenitoB@Eni.it",true,"system","Bussosiliis"),
             new Users(null, "Zladko", "123", TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),null,"ZladkoZ@HS.sb",true,"system","zacharisz"),
             new Users(null, "admin", "admin", TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),null,"adminCharity@gmail.com",true,"system",""),
             new Users(null, "Andrzej", "admin", TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),null,"beomir89@gmail.com",true,"system","Leszka")
    );

    @Autowired
    public UsersFixture(UsersService usersService, UsersRolesService usersRolesService) {
        this.usersService = usersService;
        this.usersRolesService = usersRolesService;
    }

    public void loadIntoDB() {


        for (Users users : usersList) {

            usersService.add(users);
        }
        List<UsersRoles> usersRolesList = usersRolesService.getUsersRoles();
        Users users1 = usersList.get(0);
        Users users2 = usersList.get(1);
        Users users3 = usersList.get(2);
        Users users4 = usersList.get(3);
        Users users5 = usersList.get(4);
        Users users6 = usersList.get(5);
        Users users7 = usersList.get(6);
        Users users8 = usersList.get(7);
        Users users9 = usersList.get(8);
//
        users1.setUsersRoles(usersRolesList.get(0));
        users2.setUsersRoles(usersRolesList.get(0));
        users3.setUsersRoles(usersRolesList.get(0));
        users4.setUsersRoles(usersRolesList.get(0));
        users5.setUsersRoles(usersRolesList.get(0));
        users6.setUsersRoles(usersRolesList.get(0));
        users7.setUsersRoles(usersRolesList.get(0));
        users8.setUsersRoles(usersRolesList.get(1));
        users9.setUsersRoles(usersRolesList.get(1));
//
        usersService.addWithoutCodePass(users1);
        usersService.addWithoutCodePass(users2);
        usersService.addWithoutCodePass(users3);
        usersService.addWithoutCodePass(users4);
        usersService.addWithoutCodePass(users5);
        usersService.addWithoutCodePass(users6);
        usersService.addWithoutCodePass(users7);
        usersService.addWithoutCodePass(users8);
        usersService.addWithoutCodePass(users9);
    }
}
