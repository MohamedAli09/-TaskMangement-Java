package patterns;
import model.Task;
import java.util.List;
import java.util.stream.Collectors;

public class CompletedTasksFilter implements TaskFilterStrategy {
     @Override
    public List<Task> filter(List<Task> tasks) {
        return tasks.stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
    }
}