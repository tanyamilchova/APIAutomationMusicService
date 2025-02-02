package tests.playlistServiceTests;

import com.example.model.PlayList;
import com.example.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.PlaylistService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.Assert.assertThrows;

public class GetPlaylistById extends AbstractPlaylistTest {

    private  PlayList testPlayList;
    private  PlaylistService service;
    private UserService userService;
    private long userId;

    @BeforeEach
    public void setup(){
        service = new PlaylistService();
        userService = new UserService();
        userId = userService.createUser().getId();
        testPlayList = getCreatedPlaylist(userId);
    }
    @Test
    public void getPlaylistByIdTest() {

        assertEquals(userId, testPlayList.getUserId());

        long newPlaylistId = testPlayList.getId();
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
