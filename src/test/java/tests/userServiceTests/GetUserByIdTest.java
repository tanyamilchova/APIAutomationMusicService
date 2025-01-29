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
        assertEquals("Addison", user.getFirstName());
        assertEquals("Perez", user.getLastName());
        assertEquals("Addison_Perez@aol.com", user.getEmail());
    }
}
