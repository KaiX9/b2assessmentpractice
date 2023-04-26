package ibf2022.paf.assessment.server.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import ibf2022.paf.assessment.server.models.Task;
import ibf2022.paf.assessment.server.models.User;
import ibf2022.paf.assessment.server.services.TodoService;

// TODO: Task 4, Task 8
@RestController
@RequestMapping
public class TasksController {

    @Autowired
    private TodoService todoSvc;

    @PostMapping(path="/task")
    public ModelAndView saveTask(@RequestBody MultiValueMap<String, String> form) {

        ModelAndView mav = new ModelAndView();
        try {
            User user = new User();
            Task task = new Task();
            System.out.printf(">>> POST: %s\n", form);
            String username = form.getFirst("username");
            if (username.matches("[a-zA-Z0-9]+")) {
                user.setUsername(username);
            } else {
                mav = new ModelAndView("error");
                mav.addObject("message", true);
                return mav;
            }
            
            System.out.println("Username >>> " + username);

            List<Task> tasks = new ArrayList<Task>();
            Integer taskCount = (form.size() - 1) / 3;
            for (int i = 0; i < taskCount; i++) {
                task = new Task();
                String description = form.getFirst("description-" + i);
                task.setDescription(description);
                String due_date = form.getFirst("dueDate-" + i);
                
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dueDate = LocalDate.parse(due_date, formatter);
                task.setDue_date(dueDate);

                String priorityString = form.getFirst("priority-" + i);
                Integer priority = Integer.parseInt(priorityString);
                task.setPriority(priority);

                tasks.add(task);
                System.out.println("task >> " + task);
                todoSvc.upsertTask(user, task);
            }
            mav.addObject("taskCount", taskCount);
            mav.addObject("username", user.getUsername());
            mav.setStatus(HttpStatus.OK);
            mav.setViewName("result");
            return mav;
        } catch (Exception e) {
            mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            mav.setViewName("error");
            mav.addObject("message", false);
            return mav;
        }
    }    
}
