package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.UUID;

@Controller
public class UsersController {

    private final UsersService usersService;
    private final UsersRolesService usersRolesService;
    private final DonationService donationService;
    private final InstitutionService institutionService;
    private final CategoryService categoryService;
    private final SendEmailService sendEmailService;

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
        model.addAttribute("localDateTime", LocalDateTime.now());
        model.addAttribute("users", new Users());

        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);
        return "register";
    }

    @PostMapping("register")
    public String add(Users users,String email) {
        usersService.registry(users);
        if(usersService.registrationStatus())
        {
        sendEmailService.sendEmail(email,"<p>Witaj w serwisie CharityNieboska, Potwierdz rejestracje klikając: <a href='http://localhost:8080/loginCheck/" + users.getActivateToken() + "'>Tutaj</a></p>","Potwierdzenie rejestracji");
        return "redirect:/register-confirmation";
        }
        else
            return "redirect:/register-confirmation-unsuccess";
    }


    @GetMapping("/loginCheck/{activateToken}")
    public String registryValidation(@PathVariable String activateToken, Model model){
        Users user = usersService.getUserByActivateToken(activateToken);
        model.addAttribute("user", user);
        usersService.setActivateUserAfterEmailValidation(activateToken);
        return "loginCheck";
    }

    @RequestMapping("/register-confirmation")
    public String registerConfirmation(){
        return "register-confirmation";
    }

    @RequestMapping("/register-confirmation-unsuccess")
    public String registerConfirmationUnsuccess(){
        return "register-confirmation-unsuccess";
    }


    @GetMapping("/login")
    public String login(Model model) {
        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);
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
        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);
        return "admin";
    }

    @GetMapping("/myProfile/{id}")
    public String userPanel(@PathVariable Long id, Model model) {
        Users user = usersService.findById(id);
        model.addAttribute(user);

        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);

        List<Donation> donationList = donationService.getDonationByUserEmail(SecurityUtils.username());
        model.addAttribute("donationList", donationList);


        model.addAttribute("localDateTime", LocalDateTime.now());
        return "myProfile";
    }



    //User Management
    @GetMapping("/admin/formUser")
    public String UserForm(Model model){
        model.addAttribute("user", new Users());

        List<UsersRoles> usersRoles = usersRolesService.getUsersRoles();
        model.addAttribute("usersRoles", usersRoles);

        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);


        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);

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

        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);

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
        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);

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
        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);

        List<Donation> donationListAll = donationService.getDonation();
        model.addAttribute("donationList", donationListAll);

        model.addAttribute("localDateTime", LocalDateTime.now());
        return "donationListAll";
    }

    @GetMapping("/admin/donationsCreation")
    public String userFundraisingList(Model model) {
        model.addAttribute("donation", new Donation());

        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        List<Category> categories = categoryService.getActiveCategory();
        model.addAttribute("categories", categories);

        List<Institution> institutions = institutionService.getActiveInstitution();
        model.addAttribute("institutions", institutions);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);

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

}
