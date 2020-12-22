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
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.entity.Users;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;

import pl.coderslab.charity.service.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UsersController {

    private final UsersService usersService;
    private final DonationService donationService;
    private final InstitutionService institutionService;

    @Autowired
    public UsersController(UsersService usersService, DonationService donationService, InstitutionService institutionService) {
        this.usersService = usersService;
        this.donationService = donationService;
        this.institutionService = institutionService;
    }

    @GetMapping("/register")
    public String form(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        model.addAttribute("users", new Users());

        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("id", userId);
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
        model.addAttribute("id", userId);
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
        model.addAttribute("id", userId);
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

    @GetMapping("/myFundraising/{id}")
    public String userFundraising(@PathVariable Long id, Model model) {

        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);

        Donation donation = donationService.getDonationById(id);
        model.addAttribute("donations", donation);

        List<Institution> institutions = institutionService.getInstitution();
        model.addAttribute("institutions", institutions);

        model.addAttribute("localDateTime", LocalDateTime.now());
        return "myFundraising";
    }

    @PostMapping("myFundraising")
    public String editFundraising(Donation donation) {
        donationService.add(donation);
        return "redirect:/donationList";
    }

    @GetMapping("/donationList")
    public String userFundraisingList(Model model) {
        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);

        List<Donation> donationList = donationService.getDonationByUserEmail(SecurityUtils.username());
        model.addAttribute("donationList", donationList);

        model.addAttribute("localDateTime", LocalDateTime.now());
        return "donationList";
    }

}
