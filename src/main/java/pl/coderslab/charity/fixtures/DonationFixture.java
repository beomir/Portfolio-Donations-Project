package pl.coderslab.charity.fixtures;

import org.springframework.stereotype.Component;
import pl.coderslab.charity.app.TimeUtils;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.entity.Users;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.DonationService;
import pl.coderslab.charity.service.InstitutionService;
import pl.coderslab.charity.service.UsersService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DonationFixture {
    private DonationService donationService;
    private InstitutionService institutionService;
    private CategoryService categoryService;
    private UsersService usersService;



            private List<Donation> donationList = Arrays.asList(
                    new Donation(null,33,new ArrayList<>(),null,"Kołchozna","Kharkiv","33-333", LocalDate.now(), LocalTime.now(),"test","666-777-888",null, TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),"system",true),
                    new Donation(null,44,new ArrayList<>(),null,"Sovieckaja","Kiev","73-333", LocalDate.now(), LocalTime.now(),"test2","696-797-898",null, TimeUtils.timeNowLong(),TimeUtils.timeNowLong(),"system",true)
            );


    public DonationFixture(DonationService donationService, InstitutionService institutionService, CategoryService categoryService, UsersService usersService) {
        this.donationService = donationService;
        this.institutionService = institutionService;
        this.categoryService = categoryService;
        this.usersService = usersService;
    }

    public void loadIntoDB() {
        List<Institution> institutionList = institutionService.getInstitution();
        List<Category> categories = categoryService.getCategory();
        List<Users> usersList = usersService.getUsers();

        for (Donation donation : donationList){
            donationService.add(donation);
        }


        Donation donation1 = donationList.get(0);
        Donation donation2 = donationList.get(1);


        donation1.getCategories().add(categories.get(1));
        donation2.getCategories().add(categories.get(2));
        donation2.getCategories().add(categories.get(0));

        donation1.setInstitution(institutionList.get(0));
        donation2.setInstitution(institutionList.get(0));

        donation1.setUsers(usersList.get(0));
        donation2.setUsers(usersList.get(1));

        donationService.add(donation1);
        donationService.add(donation2);

    }
}
