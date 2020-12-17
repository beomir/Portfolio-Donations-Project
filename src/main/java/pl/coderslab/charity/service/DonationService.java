package pl.coderslab.charity.service;

import pl.coderslab.charity.entity.Donation;

import java.util.List;

public interface DonationService {

    void add(Donation donation);

    List<Donation> getDonation();

    int SumOfDonation();

    int QtyOfDonation();

}
