package pl.coderslab.charity.fixtures;

import org.springframework.stereotype.Component;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.service.InstitutionService;

import java.util.Arrays;
import java.util.List;

@Component
public class InstitutionFixture {
    private InstitutionService institutionService;

            private List<Institution> institutionList = Arrays.asList(
                    new Institution(null,"Bez domu","Cel i misja: Pomoc dla osób nie posiadających miejsca zamieszkania"),
                    new Institution(null,"Dbam o Zdrowie","Cel i misja: Pomoc dzieciom z ubogich rodzin."),
                    new Institution(null,"Fundacja \"A kogo\"","Cel i misja: Pomoc wybudzaniu dzieci ze śpiączki."),
                    new Institution(null,"Fundacja “Dla dzieci\"","Cel i misja: Pomoc osobom znajdującym się w trudnej sytuacji życiowej.")
//                    new Institution(null,"Poszol WON!","Cel i misja: Egzorcyzmy na opętanych.")
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
