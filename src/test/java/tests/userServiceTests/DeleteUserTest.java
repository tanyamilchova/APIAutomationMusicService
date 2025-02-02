package tests.userServiceTests;

import com.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.UserService;


import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.*;

public class DeleteUserTest {
    private  UserService userService;
    private User user;

    @BeforeEach
    public void seetup(){
        userService = new UserService();
    }

    @Test
    public void deleteUserDetailsTest(){
        user = userService.createUser();
        long id = user.getId();
        userService.deleteUserById(id);

        assertFalse(userService.checkIfSuccessfullyDeleted(id));
    }

    @Test
    public void deleteNotFoundUserTest() {
        User createdUser = userService.createUser();
        long userIdToDelete = createdUser.getId();

        userService.deleteUserById(userIdToDelete);
        assertThrows(RuntimeException.class, () -> userService.getUserById(userIdToDelete));
    }
}
