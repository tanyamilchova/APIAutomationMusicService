package tests.userServiceTests;


import com.example.model.Track;
import com.example.model.User;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.TrackService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateUserTest {
    private  UserService userService;
    private TrackService trackService;
    private Track track;
    private User user;

    @BeforeMethod
    public void seetup(){
        userService = new UserService();
        trackService = new TrackService();
        track = trackService.createTrack();
    }

    @AfterMethod
    public void tearDown(){
        trackService.deleteTrackById(track.getId());
        userService.deleteUserById(user.getId());
    }
    @Test
    public void createUserTest() {

        user = userService.createUser();

        assertEquals("firstName", user.getFirstName());
        assertEquals("lastName", user.getLastName());
        assertEquals("email", user.getEmail());
    }
}
