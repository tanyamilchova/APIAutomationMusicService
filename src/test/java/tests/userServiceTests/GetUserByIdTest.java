package tests.userServiceTests;

import com.example.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import service.UserService;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetUserByIdTest {

    private  UserService userService;

    private User user;
    @BeforeEach
    public void seetup(){
        userService = new UserService();
        user = userService.createUser();
    }
    @AfterEach
    public void tearDown(){
        userService.deleteUserById(user.getId());
    }
    @Test
    public void getUserByIdTest() {
        long id = user.getId();


        assertEquals(id, user.getId());
        assertEquals("firstName", user.getFirstName());
        assertEquals("lastName", user.getLastName());
        assertEquals("email", user.getEmail());
    }
}
