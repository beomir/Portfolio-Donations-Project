package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.app.SecurityUtils;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.UsersService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/logged")
public class DonationController {

    private final DonationService donationService;
    private final CategoryService categoryService;
    private final InstitutionService institutionService;
    private final UsersService usersService;

    @Autowired
    public DonationController(DonationService donationService, CategoryService categoryService, InstitutionService institutionService, UsersService usersService) {
        this.donationService = donationService;
        this.categoryService = categoryService;
        this.institutionService = institutionService;
        this.usersService = usersService;
    }

    @GetMapping("form")
    public String donationForm(Model model){
        model.addAttribute("donation", new Donation());

        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        List<Category> categories = categoryService.getCategory();
        model.addAttribute("categories", categories);

        List<Institution> institutions = institutionService.getInstitution();
        model.addAttribute("institutions", institutions);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);

        model.addAttribute("localDateTime", LocalDateTime.now());
        return "form";
    }

    @PostMapping("form")
    public String donationAdd(Donation donation) {
        donationService.add(donation);
        return "redirect:/logged/form-confirmation";
    }

    @GetMapping("form-confirmation")
    public String donationConfirmationForm(Model model){
        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);
        return "form-confirmation";
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
        return "redirect:/logged/donationList";
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

//deactivate Donation - make them receipt
    @GetMapping("myFundraising/deactivate/{id}")
    public String deactivateDonation(@PathVariable Long id) {
        donationService.deactivate(id);
        return "redirect:/logged/donationList";
    }

    //activate Donation - make them  to receipt
    @GetMapping("/myFundraising/activate/{id}")
    public String activateDonation(@PathVariable Long id) {
        donationService.activate(id);
        return "redirect:/logged/donationList";
    }
}
