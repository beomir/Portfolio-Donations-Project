package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.app.SecurityUtils;
import pl.coderslab.charity.app.SendEmailService;
import pl.coderslab.charity.entity.*;
import pl.coderslab.charity.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;


@Controller
public class UsersController {

    private final UsersService usersService;
    private final UsersRolesService usersRolesService;
    private final DonationService donationService;
    private final InstitutionService institutionService;
    private final CategoryService categoryService;
    private final SendEmailService sendEmailService;
    private boolean userExists;
    private boolean activateTokenActive;

    @Autowired
    public UsersController(UsersService usersService, UsersRolesService usersRolesService, DonationService donationService, InstitutionService institutionService, CategoryService categoryService, SendEmailService sendEmailService) {
        this.usersService = usersService;
        this.usersRolesService = usersRolesService;
        this.donationService = donationService;
        this.institutionService = institutionService;
        this.categoryService = categoryService;
        this.sendEmailService = sendEmailService;
    }



    @GetMapping("/register")
    public String form(Model model) {
        model.addAttribute("users", new Users());

        usersService.loggedUserData(model);
        return "register";
    }

    @PostMapping("register")
    public String add(Users users,String email) {
        usersService.registry(users);
        if(usersService.registrationStatus()){
            sendEmailService.sendEmail(email,"<p>Witaj w serwisie CharityNieboska, Potwierdz rejestracje klikając: <a href='http://localhost:8080/loginCheck/" + users.getActivateToken() + "'>Tutaj</a></p>","Potwierdzenie rejestracji");
        }
        return "redirect:/register-confirmation";
    }


    @GetMapping("/loginCheck/{activateToken}")
    public String registryValidation(@PathVariable String activateToken, Model model){
        Users user = usersService.getUserByActivateToken(activateToken);
        model.addAttribute("user", user);
        usersService.setActivateUserAfterEmailValidation(activateToken);
        return "loginCheck";
    }

    @RequestMapping("/register-confirmation")
    public String registerConfirmation(Model model){
        model.addAttribute("registrationStatus", usersService.registrationStatus());
        return "register-confirmation";
    }


    @GetMapping("/login")
    public String login(Model model) {
        usersService.loggedUserData(model);
        return "login";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        usersService.loggedUserData(model);
        return "admin";
    }

    @GetMapping("/myProfile/{token}")
    public String userPanel(@PathVariable String token, Model model) {
        Users user = usersService.getUserByActivateToken(token);
        model.addAttribute(user);

        usersService.loggedUserData(model);

        List<Donation> donationList = donationService.getDonationByUserEmail(SecurityUtils.username());
        model.addAttribute("donationList", donationList);


        model.addAttribute("localDateTime", LocalDateTime.now());
        return "myProfile";
    }

    @PostMapping("myProfile")
    public String usersDataChanges(Users users,String email, String password2) {
        usersService.checkData(users,password2);
        if(usersService.changedDataStatus())
        {
            sendEmailService.sendEmail(email,"<p>Twoje dane po dokonanych zmianach:<br/><b> Imię:</b>" + users.getUsername() + "<br/><br/> <b>Nazwisko:</b>" + users.getLastName() + "<br/><br/><b>Hasło:</b>" + users.getPassword() + "</p>","Zmiana danych");
            usersService.add(users);
            return "redirect:/data-changed";
        }
        else
            return "redirect:/data-changed";

    }

    @RequestMapping("/data-changed")
    public String dataChangeConfirmation(Model model){
        model.addAttribute("changedDataStatus", usersService.changedDataStatus());
        Users user = usersService.getByEmail(SecurityUtils.username());
        model.addAttribute("users", user);
        usersService.loggedUserData(model);
        return "data-changed";
    }


    //User Management
    @GetMapping("/admin/formUser")
    public String UserForm(Model model){
        model.addAttribute("user", new Users());

        List<UsersRoles> usersRoles = usersRolesService.getUsersRoles();
        model.addAttribute("usersRoles", usersRoles);

        usersService.loggedUserData(model);

        model.addAttribute("localDateTime", LocalDateTime.now());
        return "formUser";
    }

    @PostMapping("/admin/formUser")
    public String userAdd(Users users) {
        usersService.add(users);
        return "redirect:/admin/usersList";
    }


    @GetMapping("/admin/editUser/{id}")
    public String usersEdit(@PathVariable Long id, Model model) {

        usersService.loggedUserData(model);

        Users user = usersService.getUsersById(id);
        model.addAttribute("user", user);

        List<UsersRoles> usersRoles = usersRolesService.getUsersRoles();
        model.addAttribute("usersRoles", usersRoles);

        List<Users> users = usersService.getUsers();
        model.addAttribute("users", users);


        model.addAttribute("localDateTime", LocalDateTime.now());
        return "editUser";
    }

    @PostMapping("/admin/editUser")
    public String institutionEditPost(Users users) {
        usersService.add(users);
        return "redirect:/admin/usersList";
    }

    @GetMapping("/admin/usersList")
    public String usersList(Model model) {
        usersService.loggedUserData(model);

        List<Users> users = usersService.getUsers();
        model.addAttribute("users", users);

        model.addAttribute("localDateTime", LocalDateTime.now());
        return "usersList";
    }

    //deactivate User
    @GetMapping("/admin/users/deactivate/{id}")
    public String deactivateUser(@PathVariable Long id) {
        usersService.deactivateUsers(id);
        return "redirect:/admin/usersList";
    }

    //activate User
    @GetMapping("/admin/users/activate/{id}")
    public String activateUser(@PathVariable Long id) {
        usersService.activateUsers(id);
        return "redirect:/admin/usersList";
    }

    //delete User
    @GetMapping("/admin/users/delete/{id}")
    public String removeUser(@PathVariable Long id) {
        usersService.deleteUsers(id);
        return "redirect:/admin/usersList";
    }

    //donation Managment
    @GetMapping("/admin/donationListAll")
    public String userFundraisingListAll(Model model) {
        usersService.loggedUserData(model);

        List<Donation> donationListAll = donationService.getDonation();
        model.addAttribute("donationList", donationListAll);

        model.addAttribute("localDateTime", LocalDateTime.now());
        return "donationListAll";
    }

    @GetMapping("/admin/donationsCreation")
    public String userFundraisingList(Model model) {
        model.addAttribute("donation", new Donation());

        usersService.loggedUserData(model);

        List<Category> categories = categoryService.getActiveCategory();
        model.addAttribute("categories", categories);

        List<Institution> institutions = institutionService.getActiveInstitution();
        model.addAttribute("institutions", institutions);


        List<Users> users = usersService.getUsers();
        model.addAttribute("usersList", users);

        model.addAttribute("localDateTime", LocalDateTime.now());
        return "donationsCreation";


    }

    @PostMapping("/admin/donationsCreation")
    public String creatDonation(Donation donation) {
        donationService.add(donation);
        return "redirect:/admin/donationListAll";
    }


    //reset Password
    @GetMapping("/resetPassword")
    public String resetPassword(Model model) {

        usersService.loggedUserData(model);

        List<UsersRoles> usersRoles = usersRolesService.getUsersRoles();
        model.addAttribute("usersRoles", usersRoles);

        return "resetPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPasswordPost(String email) {
        try {
            sendEmailService.sendEmail(email, "<p>W celu zresetowania hasla kliknij: <a href='http://localhost:8080/passwordRestarted/" + usersService.getByEmail(email).getActivateToken() + "'>Tutaj</a></p>", "Resetowanie hasła");
            userExists = true;
            return "redirect:/resetPassword-confirmation";
        }
            catch(NullPointerException e) {
                userExists = false;
                return "redirect:/resetPassword-confirmation";
            }
    }


    @RequestMapping("/resetPassword-confirmation")
    public String resetPasswordConfirmation(Model model){
        model.addAttribute("userExists", userExists);
        return "resetPassword-confirmation";
    }


    @GetMapping("/passwordRestarted/{activateToken}")
    public String passwordRestarted(@PathVariable String activateToken, Model model){
        try {
            Users user = usersService.getUserByActivateToken(activateToken);
            model.addAttribute("user", user);
            usersService.setActivateUserAfterEmailValidation(activateToken);
            activateTokenActive = true;
            model.addAttribute("activateTokenActive", activateTokenActive);
            return "passwordRestarted";
        }
        catch(NullPointerException e) {
            activateTokenActive = false;
            model.addAttribute("activateTokenActive", activateTokenActive);
            return "passwordRestarted";
        }
    }

    @PostMapping("/passwordRestarted")
    public String passwordRestartedPost(Users users,String password2) {
        usersService.resetPassword(users,password2);
        if(usersService.resetPasswordStatus()) {
            sendEmailService.sendEmail(users.getEmail(), "<p>Twoje nowe hasło to: <b>" + password2 + "</b>.<br/><br/>Jeżeli nie resetowałeś hasła kliknij: <a href='http://localhost:8080/blockMyAccount/" + usersService.getByEmail(users.getEmail()).getActivateToken() + "'>Tutaj</a></p>", "Nowe Hasło");
        }
        return "redirect:/passwordRestartedStep2";
    }

    @GetMapping("/passwordRestartedStep2")
    public String passwordRestartedFinish(Model model){
        model.addAttribute("resetPassword", usersService.resetPasswordStatus());
        return "passwordRestartedStep2";
    }
}
