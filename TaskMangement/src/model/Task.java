package model;

public class Task {
    private String title;
    private String description;
    private TaskType type;
    private User assignedUser;
    private String dueDate;
    private boolean completed;

    public Task(String title, String description, TaskType type, User assignedUser, String dueDate) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.assignedUser = assignedUser;
        this.dueDate = dueDate;
        this.completed = false;
    }
     public TaskType getType() {
        return type;
    }

     public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public User getAssignedUser() {
        return assignedUser;
    }
    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

     public void setCompleted(boolean completed) {
        this.completed = completed;
    }
        @Override
        public String toString() {
            return "Title: " + title + "\nDescription: " + description + "\nType: " + type +
                    "\nAssigned To: " + (assignedUser != null ? assignedUser.getName() + " (" + assignedUser.getRole() + ")" : "Unassigned")
                    + "\nDue Date: " + dueDate + "\nCompleted: " + (completed ? "Yes" : "No")+"\n\n" ;
        }

}