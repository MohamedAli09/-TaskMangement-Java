// patterns package
package patterns;
import java.util.ArrayList;
import java.util.List;
import model.Task;

public class TaskManager {
    private static TaskManager instance;
    private List<Task> tasks;
    private TaskManager() {
        tasks = new ArrayList<>();
    }
    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }
    public void addTask(Task task) {
        tasks.add(task);
    }
    public List<Task> getAllTasks() {
        return tasks;
    }
        public Task findTaskByTitle(String title) {
        for (Task task : tasks) {
            if (task.getTitle().equals(title)) {
                return task;
            }
        }
        return null; // Task not found
    }
    public void removeTask(Task task) {
            tasks.remove(task);
    }
     public List<Task> filterTasks(TaskFilterStrategy filterStrategy) {
        return filterStrategy.filter(tasks);
    }
}