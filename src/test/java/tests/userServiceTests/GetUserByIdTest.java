package tests.userServiceTests;

import com.example.model.User;
import com.example.util.Util;
import org.junit.jupiter.api.Test;
import service.UserService;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUserByIdTest {


    @Test
    public void getUserByIdTest() {
        UserService userService = new UserService();
        long id = Util.getResourceIdFromProperty();
        User user = userService.getUserById(id);


        assertEquals(id, user.getId());
        assertEquals("Samantha", user.getFirstName());
        assertEquals("Young", user.getLastName());
        assertEquals("Samantha_Young@gmail.com", user.getEmail());
    }
}
