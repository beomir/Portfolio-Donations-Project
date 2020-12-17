package pl.coderslab.charity.service;

import pl.coderslab.charity.entity.Institution;

import java.util.List;

public interface InstitutionService {

    void add(Institution institution);

    List<Institution> getInstitution();

    List<Institution> getInstitutionEven();

    List<Institution> getInstitutionOdd();

}
