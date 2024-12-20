package patterns;

import model.Task;
import java.util.List;

public interface TaskFilterStrategy {
    List<Task> filter(List<Task> tasks);
}