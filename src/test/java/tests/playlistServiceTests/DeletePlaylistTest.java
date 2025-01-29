package tests.playlistServiceTests;

import com.example.model.PlayList;
import com.example.util.Util;
import org.junit.jupiter.api.Test;
import service.PlaylistService;

import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.*;

public class DeletePlaylistTest extends AbstractPlaylistTest{
    PlaylistService service = new PlaylistService();
    long userId = Util.getResourceIdFromProperty();

    @Test
    public void deletePlaylistDetailsTest() {
        PlayList playlist = service.createPlaylist(userId);
        long playlistId = playlist.getId();
    service.deletePlaylistById(playlistId);
        assertFalse(service.resourceIsPresent(playlistId));
    }

    @Test
    public void deleteNotFoundPLAYLISTTest() {
        PlayList createdPlayList = getCreatedPlaylist();

        long playlistIdToDelete = createdPlayList.getId();
        service.deletePlaylistById(playlistIdToDelete);
        assertThrows(RuntimeException.class, () -> service.getPlaylistById(playlistIdToDelete));
    }
}
