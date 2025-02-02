package tests.playlistServiceTests;

import com.example.model.PlayList;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.PlaylistService;
import service.UserService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdatePlaylistAttributesTest extends AbstractPlaylistTest {

    private  PlaylistService playlistService;
    private  PlayList testPlayList;

    private UserService userService;
    private long userId;

    @BeforeEach
    public void setup(){
        userService = new UserService();
        playlistService = new PlaylistService();

        userId = userService.createUser().getId();
        testPlayList = getCreatedPlaylist(userId);
    }
    @Test
    public void updatePlaylistAttributesTest() {
        long newPlaylistId = testPlayList.getId();

        PlayList updatedPlaylist = playlistService.updatePlaylistDetails(newPlaylistId);

        assertEquals(userId, updatedPlaylist.getUserId());
        assertEquals("updated_name", updatedPlaylist.getName());
        assertEquals("updated_description", updatedPlaylist.getDescription());
        assertEquals(new ArrayList<>(), updatedPlaylist.getTracks());

    }
    @Test
    public void updatePlaylistUserIdTest() {
        long newPlaylistId = testPlayList.getId();
        Response response = playlistService.updatePlaylistUserId(newPlaylistId);

        assertEquals(400, response.getStatusCode());

        String errorMessage = response.jsonPath().getString("errorMessage");
        assertEquals("Unable to change the owner of playlist. Original ownerID - " + userId + ".", errorMessage);
    }
}
