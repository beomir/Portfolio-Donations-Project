package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.entity.Category;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.repository.CategoryRepository;
import pl.coderslab.charity.repository.DonationRepository;

import java.util.List;

@Service
public class DonationServiceImpl implements DonationService{

    private final DonationRepository donationRepository;

    @Autowired
    public DonationServiceImpl(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }


    @Override
    public void add(Donation donation) {
        donationRepository.save(donation);
    }

    @Override
    public List<Donation> getDonation() {
        return donationRepository.getDonation();
    }
}
