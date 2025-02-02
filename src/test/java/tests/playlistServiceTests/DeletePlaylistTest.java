package tests.playlistServiceTests;

import com.example.model.PlayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.PlaylistService;
import service.UserService;

import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.*;

public class DeletePlaylistTest extends AbstractPlaylistTest{
    PlaylistService service = new PlaylistService();
    private  PlayList playList;
    private UserService userService;
    private long userId;

    @BeforeEach
    public void setup(){
        userService = new UserService() ;
        userId = userService.createUser().getId();
        playList = getCreatedPlaylist(userId);
    }

    @Test
    public void deletePlaylistDetailsTest() {
        long playlistId = playList.getId();
        service.deletePlaylistById(playlistId);
        assertFalse(service.playlistIsPresent(playlistId));
    }

    @Test
    public void deleteNotFoundPLAYLISTTest() {

        long playlistIdToDelete = playList.getId();
        service.deletePlaylistById(playlistIdToDelete);
        assertThrows(RuntimeException.class, () -> service.getPlaylistById(playlistIdToDelete));
    }
}
