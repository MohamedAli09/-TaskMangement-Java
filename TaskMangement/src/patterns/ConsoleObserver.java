package patterns;
import model.Task;

public class ConsoleObserver implements TaskObserver {
     @Override
    public void update(Task task, String message) {
         System.out.println("Console Log: Task '" + task.getTitle() + "' - " + message);
     }
}