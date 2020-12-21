package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderslab.charity.entity.Users;
import pl.coderslab.charity.service.UsersRolesService;
import pl.coderslab.charity.service.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Controller
public class UsersController {

    private final UsersRolesService usersRolesService;
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService, UsersRolesService usersRolesService) {
        this.usersService = usersService;
        this.usersRolesService = usersRolesService;
    }

    @GetMapping("/register")
    public String form(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        model.addAttribute("users", new Users());
        return "register";
    }

    @PostMapping("register")
    public String add(Users users) {
        usersService.add(users);
        return "redirect:/index";
    }

    @GetMapping("/login")
    public String login() {
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
    public String adminPanel() {
        return "admin";
    }

}
