 package patterns;
 import model.Task;
 import model.TaskType;

 import java.util.List;
 import java.util.stream.Collectors;
 public class TaskTypeFilter implements TaskFilterStrategy{
     private TaskType type;

     public TaskTypeFilter(TaskType type) {
         this.type = type;
     }
     @Override
     public List<Task> filter(List<Task> tasks) {
         return tasks.stream()
                 .filter(task -> task.getType().equals(type))
                 .collect(Collectors.toList());
     }
 }