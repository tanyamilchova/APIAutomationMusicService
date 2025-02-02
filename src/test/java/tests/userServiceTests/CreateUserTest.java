package tests.userServiceTests;


import com.example.model.Track;
import com.example.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TrackService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateUserTest {
    private  UserService userService;
    private TrackService trackService;
    private Track track;
    private User user;
    @BeforeEach
    public void seetup(){
        userService = new UserService();
        trackService = new TrackService();
        track = trackService.createTrack();
    }
    @AfterEach
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
