package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.app.SecurityUtils;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.entity.Institution;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.InstitutionRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class InstitutionServiceImpl implements InstitutionService{

    private final InstitutionRepository institutionRepository;

    @Autowired
    public InstitutionServiceImpl(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }


    @Override
    public void add(Institution institution) {
        institutionRepository.save(institution);
    }

    @Override
    public List<Institution> getInstitution() {
        return institutionRepository.getInstitution();
    }

    @Override
    public List<Institution> getInstitutionEven() {
        return institutionRepository.getInstitutionEven();
    }

    @Override
    public List<Institution> getInstitutionOdd() {
        return institutionRepository.getInstitutionOdd();
    }

    @Override
    public void deactivateInstitution(Long id) {
        Institution institution = institutionRepository.getOne(id);
        institution.setActive(false);
        institution.setLast_update(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        institution.setChangeBy(SecurityUtils.usernameForActivations());
        institutionRepository.save(institution);
    }

    @Override
    public void activateInstitution(Long id) {
        Institution institution = institutionRepository.getOne(id);
        institution.setActive(true);
        institution.setLast_update(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        institution.setChangeBy(SecurityUtils.usernameForActivations());
        institutionRepository.save(institution);
    }

    @Override
    public Institution getInstitutionById(Long id) {
        return institutionRepository.getInstitutionById(id);
    }

    @Override
    public void deleteInstitution(Long id) {
        institutionRepository.deleteById(id);
    }

    @Override
    public List<Institution> getActiveInstitution() {
        return institutionRepository.getActiveInstitution();
    }

}
