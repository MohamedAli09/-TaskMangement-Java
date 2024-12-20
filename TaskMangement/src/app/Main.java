package app;

import model.*;
import patterns.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class Main extends JFrame {

    private JTextField titleField, descriptionField, dueDateField, nameField;
    private JComboBox<TaskType> taskTypeCombo;
    private JComboBox<UserRole> userRoleCombo;
    private JTextArea taskDisplayArea;
    private JComboBox<String> taskSelector;
    private JComboBox<User> userSelector;
    private JButton createTaskButton, addUserButton, markCompleteButton, removeTaskButton, loadTasksButton, completedFilterButton, typeFilterButton;
    private JComboBox<TaskType> filterTypeCombo;
    private TaskManager taskManager;
    private UserFactory userFactory;
    private TaskFactory taskFactory;
    private NotificationSystem notificationSystem;
    private ConsoleObserver consoleObserver;
    private List<User> users;

    public Main() {
        taskManager = TaskManager.getInstance();
        userFactory = new UserFactory();
        taskFactory = new TaskFactory();
        notificationSystem = NotificationSystem.getInstance();
        consoleObserver = new ConsoleObserver();
        users = new ArrayList<>();
        setupUI();
        loadTasks();
    }

    private void setupUI() {
        setTitle("Simple Task Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Task Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Due Date:"));
        dueDateField = new JTextField();
        inputPanel.add(dueDateField);

        inputPanel.add(new JLabel("Task Type:"));
        taskTypeCombo = new JComboBox<>(TaskType.values());
        inputPanel.add(taskTypeCombo);

        inputPanel.add(new JLabel("User Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("User Role:"));
        userRoleCombo = new JComboBox<>(UserRole.values());
        inputPanel.add(userRoleCombo);

        inputPanel.add(new JLabel(" "));
        addUserButton = new JButton("Add User");
        inputPanel.add(addUserButton);
        addUserButton.addActionListener(e -> addUser());

        inputPanel.add(new JLabel("Assign To:"));
        userSelector = new JComboBox<>();
        userSelector.setRenderer(new UserListRenderer()); // Set custom renderer
        inputPanel.add(userSelector);


        inputPanel.add(new JLabel(" "));
        createTaskButton = new JButton("Create Task");
        inputPanel.add(createTaskButton);
        createTaskButton.addActionListener(e -> createTask());

        inputPanel.add(new JLabel("Task:"));
        taskSelector = new JComboBox<>();
        inputPanel.add(taskSelector);

        inputPanel.add(new JLabel(" "));
        markCompleteButton = new JButton("Mark Complete");
        inputPanel.add(markCompleteButton);
        markCompleteButton.addActionListener(e -> markCompleteTask());

        inputPanel.add(new JLabel(" "));
        removeTaskButton = new JButton("Remove Task");
        inputPanel.add(removeTaskButton);
        removeTaskButton.addActionListener(e -> removeTask());

        inputPanel.add(new JLabel(" "));
        completedFilterButton = new JButton("Filter Completed");
        inputPanel.add(completedFilterButton);
        completedFilterButton.addActionListener(e -> filterCompletedTasks());

        inputPanel.add(new JLabel("Filter Type:"));
        filterTypeCombo = new JComboBox<>(TaskType.values());
        inputPanel.add(filterTypeCombo);

        inputPanel.add(new JLabel(" "));
        typeFilterButton = new JButton("Filter By Type");
        inputPanel.add(typeFilterButton);
        typeFilterButton.addActionListener(e -> filterByType());


        // Task Display Area
        taskDisplayArea = new JTextArea();
        taskDisplayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taskDisplayArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        loadTasksButton = new JButton("Load Tasks");
        buttonPanel.add(loadTasksButton);
        loadTasksButton.addActionListener(e -> loadTasks());
        add(buttonPanel, BorderLayout.SOUTH);


        setVisible(true);
    }
    private void filterCompletedTasks() {
        TaskFilterStrategy filter = new CompletedTasksFilter();
        List<Task> filteredTasks = taskManager.filterTasks(filter);
        displayTasks(filteredTasks);
    }

    private void filterByType() {
        TaskType selectedType = (TaskType) filterTypeCombo.getSelectedItem();
        TaskFilterStrategy filter = new TaskTypeFilter(selectedType);
        List<Task> filteredTasks = taskManager.filterTasks(filter);
        displayTasks(filteredTasks);
    }

    private void displayTasks(List<Task> tasks) {
        taskDisplayArea.setText("");
        for (Task task : tasks) {
            addTaskToDisplayArea(task);
        }
    }

    private void addTaskToDropdown(Task task) {
        taskSelector.addItem(task.getTitle());
    }

    private void addTaskToDisplayArea(Task task) {
        taskDisplayArea.append(task.toString());
    }

    private void addUserToDropdown(User user) {
        userSelector.addItem(user);
    }

    private void removeTask() {
        String selectedTaskTitle = (String) taskSelector.getSelectedItem();
        Task taskToRemove = taskManager.findTaskByTitle(selectedTaskTitle);
        if (taskToRemove != null) {
            taskManager.removeTask(taskToRemove);
            taskSelector.removeItem(selectedTaskTitle);
            taskDisplayArea.setText("");
            loadTasks();
            notificationSystem.taskRemovedNotification(taskToRemove);
        }
    }

    private void markCompleteTask() {
        String selectedTaskTitle = (String) taskSelector.getSelectedItem();
        Task taskToMark = taskManager.findTaskByTitle(selectedTaskTitle);
        if (taskToMark != null) {
            ObservableTask observableTask = new ObservableTask(taskToMark);
            observableTask.attach(consoleObserver);
            observableTask.markComplete(true);
            taskDisplayArea.setText("");
            loadTasks();
        }
    }

     private void addUser() {
        String name = nameField.getText().trim();
        UserRole role = (UserRole) userRoleCombo.getSelectedItem();

        if (!name.isEmpty()) {
            User user = userFactory.createUser(name, role);
            users.add(user);
            addUserToDropdown(user);
            JOptionPane.showMessageDialog(this, "User '" + user.getName() + "' created with role " + user.getRole());
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a user name.");
        }
        nameField.setText("");
    }


    private void createTask() {
        String title = titleField.getText().trim();
        String description = descriptionField.getText().trim();
        TaskType type = (TaskType) taskTypeCombo.getSelectedItem();
        String dueDate = dueDateField.getText().trim();
        User assignedUser = (User) userSelector.getSelectedItem();


        if (!title.isEmpty() && !description.isEmpty() && !dueDate.isEmpty() && assignedUser != null) {
            Task task = taskFactory.createTask(title, description, type, assignedUser, dueDate);
            taskManager.addTask(task);
            addTaskToDropdown(task);
            addTaskToDisplayArea(task);
            notificationSystem.taskAddedNotification(task);

            titleField.setText("");
            descriptionField.setText("");
            dueDateField.setText("");


        } else {
            JOptionPane.showMessageDialog(this, "Please fill all task fields.");
        }
    }

    private void loadTasks() {
        taskDisplayArea.setText("");
        taskSelector.removeAllItems();
        for (Task task : taskManager.getAllTasks()) {
            addTaskToDropdown(task);
            addTaskToDisplayArea(task);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main());
    }
}

// Custom renderer to display user's name (and role)
class UserListRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof User) {
            User user = (User) value;
            setText(user.getName() + " (" + user.getRole() + ")");
        }
        return this;
    }
}