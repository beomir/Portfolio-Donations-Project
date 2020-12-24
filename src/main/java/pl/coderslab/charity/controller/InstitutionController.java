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
public class InstitutionController {

    private final DonationService donationService;
    private final InstitutionService institutionService;
    private final UsersService usersService;

    @Autowired
    public InstitutionController(DonationService donationService, InstitutionService institutionService, UsersService usersService) {
        this.donationService = donationService;
        this.institutionService = institutionService;
        this.usersService = usersService;
    }

    @GetMapping("/admin/formInstitution")
    public String InstitutionForm(Model model){
        model.addAttribute("institution", new Institution());

        List<Institution> institution = institutionService.getInstitution();
        model.addAttribute("institutions", institution);

        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);


        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);

        model.addAttribute("localDateTime", LocalDateTime.now());
        return "formInstitution";
    }

    @PostMapping("/admin/formInstitution")
    public String institutionAdd(Institution institution) {
        institutionService.add(institution);
        return "redirect:/institution";
    }


    @GetMapping("/admin/editInstitution/{id}")
    public String institutionEdit(@PathVariable Long id, Model model) {

        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);

        Institution institution = institutionService.getInstitutionById(id);
        model.addAttribute("institution", institution);

        List<Institution> institutions = institutionService.getInstitution();
        model.addAttribute("institutions", institutions);


        model.addAttribute("localDateTime", LocalDateTime.now());
        return "editInstitution";
    }

    @PostMapping("/admin/editInstitution")
    public String institutionEditPost(Institution institution) {
        institutionService.add(institution);
        return "redirect:/institution";
    }

    @GetMapping("/institution")
    public String institutionList(Model model) {
        String username = usersService.FindUsernameByEmail(SecurityUtils.username());
        model.addAttribute("username", username);

        Long userId = usersService.FindUserIdByEmail(SecurityUtils.username());
        model.addAttribute("userId", userId);

        List<Institution> institutions = institutionService.getInstitution();
        model.addAttribute("institutions", institutions);

        model.addAttribute("localDateTime", LocalDateTime.now());
        return "institution";
    }

//deactivate Institution
    @GetMapping("/admin/institution/deactivate/{id}")
    public String deactivateInstitution(@PathVariable Long id) {
        institutionService.deactivateInstitution(id);
        return "redirect:/institution";
    }

    //activate Institution
    @GetMapping("/admin/institution/activate/{id}")
    public String activateInstitution(@PathVariable Long id) {
        institutionService.activateInstitution(id);
        return "redirect:/institution";
    }

    //delete Institution
    @GetMapping("/admin/institution/delete/{id}")
    public String removeUser(@PathVariable Long id) {
        institutionService.deleteInstitution(id);
        return "redirect:/institution";
    }
}
