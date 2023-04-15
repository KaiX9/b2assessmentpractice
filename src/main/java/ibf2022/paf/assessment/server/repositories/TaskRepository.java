package ibf2022.paf.assessment.server.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;

import static ibf2022.paf.assessment.server.repositories.DBQueries.*;

// TODO: Task 6
@Repository
public class TaskRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean insertTask(Task task, User user) {

        return jdbcTemplate.update(INSERT_TASK,
                task.getDescription(),
                task.getPriority(),
                task.getDue_date(),
                user.getUserId()) > 0;
    }

}
