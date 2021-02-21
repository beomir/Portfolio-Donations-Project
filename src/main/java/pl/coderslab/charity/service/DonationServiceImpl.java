package pl.coderslab.charity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.app.SecurityUtils;
import pl.coderslab.charity.entity.Donation;
import pl.coderslab.charity.repository.DonationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        donation.setSpecNumber(SecurityUtils.uuidToken());
        donationRepository.save(donation);
    }

    @Override
    public List<Donation> getDonation() {
        return donationRepository.getDonation();
    }

    @Override
    public List<Donation> getDonationByUserEmail(String email) {
        return donationRepository.getDonationByUserEmail(email);
    }

    @Override
    public Donation getDonationById(Long id) {
        return donationRepository.getDonationById(id);
    }

    @Override
    public Donation getDonationBySpecNumber(String specNumber) {
        return donationRepository.getDonationBySpecNumber(specNumber);
    }

    @Override
    public int SumOfDonation() {
        return donationRepository.SumOfDonation();
    }

    @Override
    public int QtyOfDonation() {
        return donationRepository.QtyOfDonation();
    }

    @Override
    public void deactivate(String specNumber) {
        Donation donation = donationRepository.getDonationBySpecNumber(specNumber);
        donation.setActive(true);
        donation.setLast_update(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        donation.setChangeBy(SecurityUtils.usernameForActivations());
        donationRepository.save(donation);
    }

    @Override
    public void activate(String specNumber) {
        Donation donation = donationRepository.getDonationBySpecNumber(specNumber);
        donation.setActive(false);
        donation.setLast_update(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        donation.setChangeBy(SecurityUtils.usernameForActivations());
        donationRepository.save(donation);
    }

}
