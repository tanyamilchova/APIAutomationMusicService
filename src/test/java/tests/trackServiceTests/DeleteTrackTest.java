package tests.trackServiceTests;

import com.example.model.Track;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import service.PlaylistService;
import service.TrackService;

import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.*;

public class DeleteTrackTest {
    TrackService service = new TrackService();
    PlaylistService playlistService = new PlaylistService();

    private long getNewTrackId(TrackService service) {
        Track track = service.createTrack();
        return track.getId();
    }

    @Test
    public void deleteTrackDetailsTest(){
        long id = getNewTrackId(service);

        service.deleteTrackById(id);
        assertFalse(service.trackExists(id));
    }



    @Test
    public void deleteNotFoundTrackTest() {
        long trackIdToDelete = getNewTrackId(service);

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
