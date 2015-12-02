package st.ilu.rms4csw.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import st.ilu.rms4csw.controller.base.CrudController;
import st.ilu.rms4csw.model.user.Role;
import st.ilu.rms4csw.model.user.User;
import st.ilu.rms4csw.repository.user.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Mischa Holz
 */
@RestController
@RequestMapping("/api/v1/" + UserController.USER_API_BASE)
public class UserController extends CrudController<User> {

    public final static String USER_API_BASE = "users";

    @Override
    public String getApiBase() {
        return USER_API_BASE;
    }

    @RequestMapping
    @PreAuthorize("hasAuthority('OP_TEST')")
    public List<User> findAll() {
        return super.findAll();
    }

    @RequestMapping("/{id}")
    public User findOne(@PathVariable String id) {
        return super.findOne(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> postUser(@RequestBody User user, HttpServletResponse response) {
        if(user.getRole() == Role.STUDENT || user.getRole() == Role.LECTURER) {
            Date expires = User.calculateNextSemesterEnd(new Date());
            user.setExpires(Optional.of(expires));
        }

        return super.post(user, response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public User putUser(@PathVariable String id, @RequestBody User user) {
        return super.put(id, user);
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity deleteUser(@PathVariable String id) {
        return super.delete(id);
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public User patchUser(@PathVariable String id, @RequestBody User user) {
        return super.patch(id, user);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.repository = userRepository;
    }
}