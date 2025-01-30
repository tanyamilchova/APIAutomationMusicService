package tests.trackServiceTests;

import com.example.model.Track;
import com.example.util.Util;
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
//        Track track = service.createTrack();
//        long id = track.getId();
        long id = getNewTrackId(service);

        service.deleteTrackById(id);
        assertFalse(service.checkIfSuccessfullyDeleted(id));
    }



    @Test
    public void deleteNotFoundTrackTest() {
//        Track createdTrack = service.createTrack();
        long trackIdToDelete = getNewTrackId(service);

        service.deleteTrackById(trackIdToDelete);

        assertThrows(RuntimeException.class, () -> service.getTrackById(trackIdToDelete));
    }
    @Test
    public void deleteTrackFromAllPlaylistsTest() {

        long trackIdToDelete = Util.getResourceToDelete();

        service.deleteTrackById(trackIdToDelete);

        Response response = playlistService.getAllPlaylists();
        List<Map<String, Object>> playListLists = response.jsonPath().getList("playlists");
        for (Map<String, Object> playlist : playListLists) {
            List<Map<String, Object>> tracks = (List<Map<String, Object>>) playlist.get("tracks");
            if (tracks != null) {
                for (Map<String, Object> track : tracks) {
                    System.out.println("Playlist: "+ playlist.get("id") + "Track ID: " + track.get("id"));
                    assertNotEquals(trackIdToDelete, track.get("id"));
                }
            }
        }
    }
}
