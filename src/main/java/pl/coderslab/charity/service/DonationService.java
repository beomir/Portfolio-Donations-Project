package pl.coderslab.charity.service;

import pl.coderslab.charity.entity.Donation;

import java.util.List;

public interface DonationService {

    void add(Donation donation);

    List<Donation> getDonation();

    List<Donation> getDonationByUserEmail(String email);

    Donation getDonationById(Long id);

    int SumOfDonation();

    int QtyOfDonation();

}
