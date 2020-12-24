package pl.coderslab.charity.service;

import pl.coderslab.charity.entity.Institution;

import java.util.List;

public interface InstitutionService {

    void add(Institution institution);

    List<Institution> getInstitution();

    List<Institution> getInstitutionEven();

    List<Institution> getInstitutionOdd();

    Institution getInstitutionById(Long id);

    void deactivateInstitution(Long id);

    void activateInstitution(Long id);

    void deleteInstitution(Long id);

}
