package pl.coderslab.charity.fixtures;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LoadFixtures {
    private CategoryFixture categoryFixture;
    private InstitutionFixture institutionFixture;
    private DonationFixture donationFixture;
    private UsersFixture usersFixture;
    private UsersRolesFixture usersRolesFixture;


    public LoadFixtures(CategoryFixture categoryFixture, InstitutionFixture institutionFixture, DonationFixture donationFixture, UsersFixture usersFixture, UsersRolesFixture usersRolesFixture) {
        this.categoryFixture = categoryFixture;
        this.institutionFixture = institutionFixture;
        this.donationFixture = donationFixture;
        this.usersFixture = usersFixture;
        this.usersRolesFixture = usersRolesFixture;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        categoryFixture.loadIntoDB();
        institutionFixture.loadIntoDB();
        donationFixture.loadIntoDB();
        usersRolesFixture.loadIntoDB();
        usersFixture.loadIntoDB();
    }
}
