package pl.coderslab.charity.fixtures;

import org.springframework.stereotype.Component;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DonationFixture {
    private DonationService donationService;
    private InstitutionService institutionService;
    private CategoryService categoryService;



            private List<Donation> donationList = Arrays.asList(
                    new Donation(null,33,null,null,"Kołchozna","Kharkiv","33-333", LocalDate.now(), LocalTime.now(),"test"),
                    new Donation(null,44,null,null,"Sovieckaja","Kiev","73-333", LocalDate.now(), LocalTime.now(),"test2")
            );


    public DonationFixture(DonationService donationService, InstitutionService institutionService, CategoryService categoryService) {
        this.donationService = donationService;
        this.institutionService = institutionService;
        this.categoryService = categoryService;
    }

    public void loadIntoDB() {
        for (Donation donation : donationList){
            donationService.add(donation);
        }
        List<Institution> institutionList = institutionService.getInstitution();
        List<Category> categoryList = categoryService.getCategory();

        Donation donation1 = donationList.get(0);
        Donation donation2 = donationList.get(1);

        donation1.setCategory(categoryList.get(0));
        donation2.setCategory(categoryList.get(1));

        donation1.setInstitution(institutionList.get(0));
        donation2.setInstitution(institutionList.get(0));

        donationService.add(donation1);
        donationService.add(donation2);

    }
}
