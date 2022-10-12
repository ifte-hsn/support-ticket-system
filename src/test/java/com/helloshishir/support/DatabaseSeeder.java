package com.helloshishir.support;

import com.github.javafaker.Faker;
import com.helloshishir.support.users.User;
import com.helloshishir.support.users.UserRepository;
import com.helloshishir.support.users.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class DatabaseSeeder {

    @Autowired
    UserRepository userRepository;


    @Test
    void seedUsersTable() {
        Faker faker = new Faker();


        for(int i = 0; i < 500; i++) {
            User user = new User();
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            String emailAddress = faker.internet().emailAddress();

            while (userRepository.findByEmail(emailAddress) != null) {
                emailAddress = faker.internet().emailAddress();
            }

            user.setEmail(emailAddress);
            user.setUsername(faker.name().firstName().toLowerCase()+"."+faker.name().lastName().toLowerCase());
            user.setPassword("secret");
            user.setPhone(faker.phoneNumber().cellPhone());
            user.setAddress(faker.address().fullAddress());
            user.setActive(true);

            userRepository.save(user);

        }

    }
}
