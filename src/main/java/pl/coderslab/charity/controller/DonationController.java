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
import pl.coderslab.charity.service.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/logged")
public class DonationController {

    private final DonationService donationService;
    private final CategoryService categoryService;
    private final InstitutionService institutionService;
    private final UsersService usersService;
    private final UsersRolesService usersRolesService;

    @Autowired
    public DonationController(DonationService donationService, CategoryService categoryService, InstitutionService institutionService, UsersService usersService, UsersRolesService usersRolesService) {
        this.donationService = donationService;
        this.categoryService = categoryService;
        this.institutionService = institutionService;
        this.usersService = usersService;
        this.usersRolesService = usersRolesService;
    }

    @GetMapping("form")
    public String donationForm(Model model){
        model.addAttribute("donation", new Donation());

        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        List<Category> categories = categoryService.getCategory();
        model.addAttribute("categories", categories);

        List<Institution> institutions = institutionService.getActiveInstitution();
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

    @GetMapping("/myFundraising/{specNumber}")
    public String userFundraising(@PathVariable String specNumber, Model model) {

        usersService.loggedUserData(model);

        Donation donation = donationService.getDonationBySpecNumber(specNumber);
        model.addAttribute("donations", donation);

        List<Institution> institutions = institutionService.getActiveInstitution();
        model.addAttribute("institutions", institutions);

        List<Category> categories = categoryService.getCategory();
        model.addAttribute("categories", categories);

        return "myFundraising";
    }

    @PostMapping("myFundraising")
    public String editFundraising(Donation donation) {
        donationService.add(donation);
        return "redirect:/logged/donationList";
    }

    @GetMapping("/donationList")
    public String userFundraisingList(Model model) {
        usersService.loggedUserData(model);

        List<Donation> donationList = donationService.getDonationByUserEmail(SecurityUtils.username());
        model.addAttribute("donationList", donationList);

        return "donationList";
    }


//deactivate Donation - make them receipt
    @GetMapping("myFundraising/deactivate/{specNumber}")
    public String deactivateDonation(@PathVariable String specNumber) {
        donationService.deactivate(specNumber);
        if(usersRolesService.getUsersRolesByEmail(SecurityUtils.username()).equals("ROLE_ADMIN"))
        {
            return "redirect:/admin/donationListAll";
        }
        else {
            return "redirect:/logged/donationList";
        }
    }

    //activate Donation - make them  to receipt
    @GetMapping("/myFundraising/activate/{specNumber}")
    public String activateDonation(@PathVariable String specNumber) {
        donationService.activate(specNumber);
        if(usersRolesService.getUsersRolesByEmail(SecurityUtils.username()).equals("ROLE_ADMIN"))
        {
            return "redirect:/admin/donationListAll";
        }
        else {
            return "redirect:/logged/donationList";
        }
    }

}
