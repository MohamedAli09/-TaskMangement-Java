package patterns;

import model.Task;
import java.util.ArrayList;
import java.util.List;

public class ObservableTask implements TaskSubject {
    private Task task;
    private List<TaskObserver> observers = new ArrayList<>();

    public ObservableTask(Task task) {
        this.task = task;
    }
    public Task getTask(){
        return task;
    }

    @Override
    public void attach(TaskObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(TaskObserver observer) {
        observers.remove(observer);
    }
    @Override
    public void notifyObservers(Task task, String message) {
        for (TaskObserver observer : observers) {
            observer.update(task, message);
        }
    }
    public void markComplete(boolean complete) {
        task.setCompleted(complete);
        notifyObservers(task, "Completion status changed to " + (complete ? "Complete" : "Incomplete"));
    }
}