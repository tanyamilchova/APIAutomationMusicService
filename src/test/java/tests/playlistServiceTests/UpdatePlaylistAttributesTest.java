package tests.playlistServiceTests;

import com.example.model.PlayList;
import com.example.util.Util;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import service.PlaylistService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UpdatePlaylistAttributesTest extends AbstractPlaylistTest {

    private final PlayList playList = getCreatedPlaylist();
    private final PlaylistService service = new PlaylistService();

    @Test
    public void updatePlaylistAttributesTest() {

        long userid = Util.getResourceIdFromProperty();
        long newPlaylistId = playList.getId();

        PlayList updatedPlaylist = service.updatePlaylistDetails(newPlaylistId);

        assertEquals(userid, updatedPlaylist.getUserId());
        assertEquals("updated_name", updatedPlaylist.getName());
        assertEquals("updated_description", updatedPlaylist.getDescription());
        assertEquals(new ArrayList<>(), updatedPlaylist.getTracks());

    }
    @Test
    public void updatePlaylistUserIdTest() {
        long newPlaylistId = playList.getId();
        Response response = service.updatePlaylistUserId(newPlaylistId);

        assertEquals(400, response.getStatusCode());

        String errorMessage = response.jsonPath().getString("errorMessage");
        assertEquals("Unable to change the owner of playlist. Original ownerID - 1.", errorMessage);
    }
}
