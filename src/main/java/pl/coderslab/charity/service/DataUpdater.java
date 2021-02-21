package pl.coderslab.charity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.coderslab.charity.repository.DonationRepository;

@Service
@Slf4j
public class DataUpdater {

    private final DonationRepository donationRepository;

    @Autowired
    public DataUpdater(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Scheduled(fixedDelay = 60000)
    public void updateData(){
        if(donationRepository.qtyOfExpiredDonations()>0){
            log.debug("Expired Donation" +  " Updated");
            donationRepository.updateExpiredDonations();
        }
    }
}
