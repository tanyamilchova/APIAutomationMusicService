package tests.playlistServiceTests;

import com.example.model.PlayList;
import com.example.util.TestDataReader;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.PlaylistService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateAPlayListTest extends AbstractPlaylistTest {

    private final PlaylistService service = new PlaylistService(); ;
    private  PlayList playList;
    private  UserService userService;
    private long userId;
    @BeforeEach
    public void seetup(){
        userService = new UserService() ;
        userId = userService.createUser().getId();
        playList = getCreatedPlaylist(userId);
    }
    @Test
    public void createPlaylistTest() {

        assertEquals("name", playList.getName());
        assertEquals("description", playList.getDescription());
        assertEquals(userId, playList.getUserId());
    }
    @Test
    public void createPlaylistWithNotExistingUserTest() {

      userId =Integer.parseInt( TestDataReader.getTestData("resource.zeroId"));
        Response response = service.createPlaylistWrongUser(userId);

        assertEquals(404, response.getStatusCode());
        String errorMessage = response.jsonPath().getString("errorMessage");
        assertEquals("There is no user with ID - " + userId + " in the system.",
                errorMessage);
    }
}
