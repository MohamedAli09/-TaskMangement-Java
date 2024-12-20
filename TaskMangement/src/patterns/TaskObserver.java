package patterns;
import model.Task;

public interface TaskObserver {
    void update(Task task, String message);
}