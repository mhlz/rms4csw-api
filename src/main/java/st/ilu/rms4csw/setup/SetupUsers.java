package st.ilu.rms4csw.setup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import st.ilu.rms4csw.model.user.Role;
import st.ilu.rms4csw.model.user.User;
import st.ilu.rms4csw.repository.UserRepository;

import java.util.Optional;

/**
 * @author Mischa Holz
 */
@Component
public class SetupUsers implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SetupUsers.class);

    private UserRepository userRepository;

    @Override
    public void run(String... strings) throws Exception {
        if(userRepository.count() == 0) {
            logger.info("Creating default user since no users are present");

            User user = new User();

            user.setExpires(Optional.empty());
            user.setLoginName("admin");
            user.setRole(Role.ADMIN);
            user.setEmail("bp@ilu.st");
            user.setFirstName("Ilu");
            user.setLastName("St");
            user.setGender(Optional.empty());
            user.setMajor(null);
            user.setPassword("change me.");
            user.setStudentId(Optional.empty());
            user.setPhoneNumber("");

            userRepository.save(user);

            logger.info("done");
        }
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}