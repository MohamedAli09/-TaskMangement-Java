package patterns;
import model.Task;

public class NotificationSystem {
    private static NotificationSystem instance;
    private NotificationSystem() {}

    public static NotificationSystem getInstance() {
        if (instance == null) {
            instance = new NotificationSystem();
        }
        return instance;
    }
    public void sendNotification(String message) {
        System.out.println("Notification: " + message); // For simplicity, prints to console
    }

    public void taskAddedNotification(Task task){
        sendNotification("Task added: " + task.getTitle() );
    }
     public void taskRemovedNotification(Task task){
        sendNotification("Task removed: " + task.getTitle() );
    }
     public void taskUpdatedNotification(Task task){
        sendNotification("Task updated: " + task.getTitle() );
    }
}