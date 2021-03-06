package pl.coderslab.charity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.charity.app.SecurityUtils;
import pl.coderslab.charity.app.SendEmailService;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.UsersService;

import java.util.List;


@Controller
public class HomeController {

    private final InstitutionService institutionService;
    private final DonationService donationService;
    private final UsersService usersService;
    private final SendEmailService sendEmailService;

    @Autowired
    public HomeController(InstitutionService institutionService, DonationService donationService, UsersService usersService, SendEmailService sendEmailService) {
        this.institutionService = institutionService;
        this.donationService = donationService;
        this.usersService = usersService;
        this.sendEmailService = sendEmailService;
    }


    @RequestMapping("/index")
    public String homeAction(Model model){
        List<Institution> institutionsEven = institutionService.getInstitutionEven();
        List<Institution> institutionsOdd = institutionService.getInstitutionOdd();

        model.addAttribute("qtyOfDonation", donationService.QtyOfDonation());
        model.addAttribute("sumOfDonation", donationService.SumOfDonation());
        model.addAttribute("institutionsEven", institutionsEven);
        model.addAttribute("institutionsOdd", institutionsOdd);

        usersService.loggedUserData(model);
        return "index";
    }

    @RequestMapping("/contact")
    public String contact(Model model){
        usersService.loggedUserData(model);
        return "contact";
    }

    @RequestMapping("/contactForm-confirmation")
    public String contactFormConfirmation(Model model){
        usersService.loggedUserData(model);
        return "contactForm-confirmation";
    }

    @PostMapping("contactForm")
    public String contactForm( String message, String name, String surname, String email) {
        sendEmailService.sendEmailFromContactForm("<span style=\"font-size: 16px;\"><b>Name: </b><i>" + name + "</i><br/><b> Surname:</b><i> " + surname + "</i><br/><b>Email:</b> " + email + "<br/><br/><b>Message:</b><i></span> " + message + "</i>");
        sendEmailService.sendEmail(email,"Witaj " + name + " " + surname + ", otrzymaliśmy od Ciebię prośbę o kontakt. Skontaktujemy sie z Tobą tak szybko jak będzie to możliwe","Potwierdzenie prośby o kontakt");
        return "redirect:/contactForm-confirmation";
    }


}
