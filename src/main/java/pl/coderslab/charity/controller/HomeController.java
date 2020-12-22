package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.app.SecurityUtils;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.UsersService;

import java.util.List;


@Controller
public class HomeController {

    private final InstitutionService institutionService;
    private final DonationService donationService;
    private final CategoryService categoryService;
    private final UsersService usersService;

    @Autowired
    public HomeController(InstitutionService institutionService, DonationService donationService, CategoryService categoryService, UsersService usersService) {
        this.institutionService = institutionService;
        this.donationService = donationService;
        this.categoryService = categoryService;
        this.usersService = usersService;
    }

    @RequestMapping("/index")
    public String homeAction(Model model){
//        List<Institution> institutions = institutionService.getInstitution();
        List<Institution> institutionsEven = institutionService.getInstitutionEven();
        List<Institution> institutionsOdd = institutionService.getInstitutionOdd();

        model.addAttribute("qtyOfDonation", donationService.QtyOfDonation());
        model.addAttribute("sumOfDonation", donationService.SumOfDonation());
//        model.addAttribute("institutions", institutions);
        model.addAttribute("institutionsEven", institutionsEven);
        model.addAttribute("institutionsOdd", institutionsOdd);

        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("id", userId);
        return "index";
    }
}
