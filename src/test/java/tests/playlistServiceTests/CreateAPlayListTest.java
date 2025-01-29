package tests.playlistServiceTests;

import com.example.model.PlayList;
import com.example.util.Util;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import service.PlaylistService;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateAPlayListTest extends AbstractPlaylistTest {

    private final PlayList playList = getCreatedPlaylist();

    @Test
    public void createPlaylistTest() {
        long userId = Util.getResourceIdFromProperty();

        assertEquals("name", playList.getName());
        assertEquals("description", playList.getDescription());
        assertEquals(userId, playList.getUserId());
    }
    @Test
    public void createPlaylistWithNotExistingUserTest() {
        PlaylistService service = new PlaylistService();
        int userId = 0;
        Response response = service.createPlaylistWrongUser(userId);

        assertEquals(404, response.getStatusCode());
        String errorMessage = response.jsonPath().getString("errorMessage");
        assertEquals("There is no user with ID - " + userId + " in the system.",
                errorMessage);
    }
}
