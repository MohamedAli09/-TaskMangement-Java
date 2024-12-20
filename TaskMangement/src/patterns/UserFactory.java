package patterns;
import model.User;
import model.UserRole;
public class UserFactory {
     public User createUser(String name, UserRole role) {
        return new User(name, role);
    }
}