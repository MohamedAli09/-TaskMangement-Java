package patterns;
import model.Task;
import model.TaskType;
import model.User;

public class TaskFactory {
    public Task createTask(String title, String description, TaskType type, User assignedUser, String dueDate) {
        return new Task(title, description, type, assignedUser, dueDate);
    }
}