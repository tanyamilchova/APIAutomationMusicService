package tests.userServiceTests;


import com.example.model.User;
import org.junit.jupiter.api.Test;
import service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateUserTest {
    private final UserService service = new UserService();

    @Test
    public void createUserTest() {

        User user = service.createUser();

        assertEquals("firstName", user.getFirstName());
        assertEquals("lastName", user.getLastName());
        assertEquals("email", user.getEmail());
    }
}
