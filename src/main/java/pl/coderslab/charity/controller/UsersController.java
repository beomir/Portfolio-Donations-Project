package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.app.SecurityUtils;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Users;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UsersController {

    private final UsersService usersService;
    private final DonationService donationService;

    @Autowired
    public UsersController(UsersService usersService, DonationService donationService) {
        this.usersService = usersService;
        this.donationService = donationService;

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
    public String add(Users users) {
        usersService.add(users);
        return "redirect:/index";
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

        List<Users> users = usersService.getUsers();
        model.addAttribute("users", users);

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
        return "redirect:/admin/userList";
    }


    @GetMapping("/admin/editUser/{id}")
    public String usersEdit(@PathVariable Long id, Model model) {

        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);

        Users user = usersService.getUsersById(id);
        model.addAttribute("user", user);

        List<Users> users = usersService.getUsers();
        model.addAttribute("users", users);


        model.addAttribute("localDateTime", LocalDateTime.now());
        return "editUser";
    }

    @PostMapping("/admin/editUser")
    public String institutionEditPost(Users users) {
        usersService.add(users);
        return "redirect:/admin/userList";
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

    //deactivate Institution
    @GetMapping("/admin/usersList/deactivate/{id}")
    public String deactivateUser(@PathVariable Long id) {
        usersService.deactivateUsers(id);
        return "redirect:/admin/usersList";
    }

    //activate Institution
    @GetMapping("/admin/usersList/activate/{id}")
    public String activateUser(@PathVariable Long id) {
        usersService.activateUsers(id);
        return "redirect:/admin/usersList";
    }

    //delete Institution
    @GetMapping("/admin/usersList/delete/{id}")
    public String removeUser(@PathVariable Long id) {
        usersService.deleteUsers(id);
        return "redirect:/admin/usersList";
    }

}
