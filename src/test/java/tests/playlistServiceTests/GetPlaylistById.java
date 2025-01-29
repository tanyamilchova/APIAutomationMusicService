package tests.playlistServiceTests;

import com.example.model.PlayList;
import com.example.util.Util;
import org.junit.jupiter.api.Test;
import service.PlaylistService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.Assert.assertThrows;

public class GetPlaylistById extends AbstractPlaylistTest {

    private final PlayList createdPlayList =getCreatedPlaylist();
    private final PlaylistService service = new PlaylistService();

    @Test
    public void getPlaylistByIdTest() {

        long id = Util.getResourceIdFromProperty();
        assertEquals(id, createdPlayList.getUserId());

        long newPlaylistId = createdPlayList.getId();
        PlayList retrivedPlaylist =service.getPlaylistById(newPlaylistId);
        assertEquals(newPlaylistId, retrivedPlaylist.getId());
    }

    @Test
    public void getIllegalIdPlaylistTest() {
        PlaylistService service = new PlaylistService();
        long illegalPlaylistId = Util.getIllegalResourceIdFromProperty();
        assertThrows(RuntimeException.class, () -> service.getPlaylistById(illegalPlaylistId));
    }
}
