package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;

import java.util.List;

@Controller
public class DonationController {

    private final DonationService donationService;
    private final CategoryService categoryService;
    private final InstitutionService institutionService;

    @Autowired
    public DonationController(DonationService donationService, CategoryService categoryService, InstitutionService institutionService) {
        this.donationService = donationService;
        this.categoryService = categoryService;
        this.institutionService = institutionService;
    }

    @GetMapping("form")
    public String donationForm(Model model){
//        List<Donation> donation = donationService.getDonation();
        List<Category> categories = categoryService.getCategory();
        List<Institution> institutions = institutionService.getInstitution();

        model.addAttribute("donation", new Donation());
        model.addAttribute("categories", categories);
        model.addAttribute("institutions", institutions);
        return "form";
    }

    @PostMapping("form")
    public String donationAdd(Donation donation) {
        donationService.add(donation);
        return "redirect:/form-confirmation";
    }

    @GetMapping("form-confirmation")
    public String donationConfirmationForm(){
        return "form-confirmation";
    }
}
