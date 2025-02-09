package tests.playlistServiceTests;

import com.example.model.Track;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.PlaylistService;
import service.TrackService;

import static org.testng.AssertJUnit.assertEquals;

public class DeleteTrackFromPlaylist extends AbstractPlaylistTest {
    private final PlaylistService service = new PlaylistService();
    private final TrackService trackService = new TrackService();
    Track newTrack ;

    @BeforeMethod
    public void setup(){
        newTrack = trackService.createTrack();
    }

    @Test
    public void deleteTracksFromPlaylistTest(){
        long trackId = newTrack.getId();
        logger.info("New track with id: {} created", trackId);
        Response response = service.addTracksToPlaylist(idResource, trackId);

        long lastAddedId = getLastAddedTrackId(response);
        long numOfTrack = service.getNumberOfResource(idResource, lastAddedId, idResourceStr);

        assertEquals(200, response.getStatusCode());
        assertEquals(numOfTrack, service.getNumberOfResource(idResource, trackId, idResourceStr));

        service.deleteTracksFromPlaylist(idResource, trackId);

        numOfTrack -= 1;
        assertEquals(numOfTrack, service.getNumberOfResource(idResource, trackId, idResourceStr));
    }
}
