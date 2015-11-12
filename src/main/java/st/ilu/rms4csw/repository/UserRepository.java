package st.ilu.rms4csw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import st.ilu.rms4csw.model.user.User;

/**
 * @author Mischa Holz
 */
public interface UserRepository extends JpaRepository<User, String> {
}
