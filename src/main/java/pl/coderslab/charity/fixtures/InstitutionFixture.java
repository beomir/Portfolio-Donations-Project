package pl.coderslab.charity.fixtures;

import org.springframework.stereotype.Component;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.CategoryService;
import pl.coderslab.charity.service.InstitutionService;

import java.util.Arrays;
import java.util.List;

@Component
public class InstitutionFixture {
    private InstitutionService institutionService;

            private List<Institution> institutionList = Arrays.asList(
                    new Institution(null,"Bez domu","Cel i misja: Pomoc dla osób nie posiadających miejsca zamieszkania")
            );


    public InstitutionFixture(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    public void loadIntoDB() {
        for (Institution institution : institutionList){
            institutionService.add(institution);
        }
    }
}
