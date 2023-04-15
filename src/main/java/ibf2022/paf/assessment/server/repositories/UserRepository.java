package ibf2022.paf.assessment.server.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.User;
import static ibf2022.paf.assessment.server.repositories.DBQueries.*;

// TODO: Task 3
@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Optional<User> findUserByUsername(String username) {
        
        SqlRowSet rs = jdbcTemplate.queryForRowSet(GET_USER_BY_USERNAME, username);
        if (rs.first()) {
            return Optional.of(User.createFromRs(rs));
        }
        return Optional.empty();
    }

    public String insertUser(User user) {

        Optional<User> u = findUserByUsername(user.getUsername());

        if (u.isEmpty()) {
            String user_id = UUID.randomUUID().toString()
                            .replace("-", "")
                            .substring(0, 8);
            System.out.println("Created new user with User ID >>> " + user_id);
            user.setUserId(user_id);

            boolean isInserted = jdbcTemplate.update(INSERT_NEW_USER,
                                user_id,
                                user.getUsername(),
                                user.getName()) > 0;
            if (isInserted) {
                return user_id;
            }
            
        } else {
            user.setUserId(u.get().getUserId());
            System.out.println("User exists! Added task to " + user.getUsername() + "(" + user.getUserId() + ")");
        }
        return user.getUsername();
    }
}
