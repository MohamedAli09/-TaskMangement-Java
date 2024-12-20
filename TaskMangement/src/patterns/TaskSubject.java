package patterns;
import model.Task;
public interface TaskSubject {
    void attach(TaskObserver observer);
    void detach(TaskObserver observer);
    void notifyObservers(Task task, String message);
}