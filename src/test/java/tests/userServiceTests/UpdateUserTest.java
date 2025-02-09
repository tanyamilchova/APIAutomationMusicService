package tests.userServiceTests;

import com.example.model.User;
import com.example.util.Util;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.UserService;

public class UpdateUserTest {
    private  UserService userService;

    private User user;
    @BeforeMethod
    public void setup(){
        userService = new UserService();
        user = userService.createUser();
    }
    @AfterMethod
    public void tearDown(){
        userService.deleteUserById(user.getId());
    }
    @Test
    public void updateUserDetailsTest(){
        long id = Util.getResourceToUpdate();
        userService.updateUserDetails(id);
    }
}
