package tests.trackServiceTests;

import com.example.model.Track;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.PlaylistService;
import service.TrackService;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertFalse;

public class DeleteTrackTest {
    TrackService service ;
    PlaylistService playlistService = new PlaylistService();
    private Track track;

    @BeforeMethod
    public void seetup(){
        service = new TrackService();
        track = service.createTrack();
    }

    @Test
    public void deleteTrackDetailsTest(){
        long id = track.getId();

        service.deleteTrackById(id);
        assertFalse(service.trackExists(id));
    }

    @Test
    public void deleteNotFoundTrackTest() {
        long trackIdToDelete = track.getId();

        service.deleteTrackById(trackIdToDelete);

        assertThrows(RuntimeException.class, () -> service.getTrackById(trackIdToDelete));
    }

    @Test
    public void deleteTrackFromAllPlaylistsTest() {
        long trackIdToDelete = service.getRandomTrackId();
        service.deleteTrackById(trackIdToDelete);

        Response response = playlistService.getAllPlaylists();
        List<Map<String, Object>> playListLists = response.jsonPath().getList("playlists");
        for (Map<String, Object> playlist : playListLists) {
            List<Map<String, Object>> tracks = (List<Map<String, Object>>) playlist.get("tracks");
            if (tracks != null) {
                for (Map<String, Object> track : tracks) {
                    assertNotEquals(trackIdToDelete, track.get("id"));
                }
            }
        }
    }
}
