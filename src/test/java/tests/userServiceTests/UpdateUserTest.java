package tests.userServiceTests;

import com.example.util.Util;
import org.junit.jupiter.api.Test;
import service.UserService;

public class UpdateUserTest {
    UserService service = new UserService();
    @Test
    public void updateUserDetailsTest(){
        long id = Util.getResourceToUpdate();
        service.updateUserDetails(id);
    }
}
