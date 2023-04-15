package ibf2022.paf.assessment.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.repositories.TaskRepository;
import ibf2022.paf.assessment.server.repositories.UserRepository;

// TODO: Task 7
@Service
public class TodoService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private TaskRepository taskRepo;

    @Transactional(rollbackFor = Exception.class)
    public void upsertTask(User user, Task task) {

        userRepo.insertUser(user);
        taskRepo.insertTask(task, user);
        
    }
}
