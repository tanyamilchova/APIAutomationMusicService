package tests.userServiceTests;

import com.example.model.User;
import com.example.util.Util;
import org.junit.jupiter.api.Test;
import service.UserService;


import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.*;

public class DeleteUserTest {
    UserService service = new UserService();
    @Test
    public void deleteUserDetailsTest(){
        User user = service.createUser();
        long id = user.getId();
        service.deleteUserById(id);

        assertFalse(service.checkIfSuccessfullyDeleted(id));
    }

    @Test
    public void deleteNotFoundUserTest() {
        User createdUser = service.createUser();
        long userIdToDelete = createdUser.getId();

        service.deleteUserById(userIdToDelete);
        assertThrows(RuntimeException.class, () -> service.getUserById(userIdToDelete));
    }
}
