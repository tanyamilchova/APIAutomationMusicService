package tests.userServiceTests;

import com.example.model.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.UserService;

import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertFalse;

public class DeleteUserTest {
    private  UserService userService;
    private User user;

    @BeforeMethod
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
