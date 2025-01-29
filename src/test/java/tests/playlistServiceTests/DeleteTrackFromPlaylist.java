package tests.playlistServiceTests;

import com.example.model.Track;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import service.PlaylistService;
import service.TrackService;

import static org.testng.AssertJUnit.assertEquals;

public class DeleteTrackFromPlaylist extends AbstractPlaylistTest {
    private final PlaylistService service = new PlaylistService();
    private final TrackService trackService = new TrackService();


    @Test
    public void deleteTracksFromPlaylistTest(){
        Track newTrack = trackService.createTrack();
        long trackId = newTrack.getId();
        logger.info("New track with id: {} created", trackId);
        Response response = service.addTracksToPlaylist(idResource, trackId);

        long lastAddedId = getLastAddedTrackId(response);
        long numOfTrack = service.getNumberOfResource(idResource, lastAddedId, idResourceStr);

        assertEquals(200, response.getStatusCode());
        assertEquals(numOfTrack, service.getNumberOfResource(idResource, trackId, idResourceStr));

        service.removeTracksFromPlaylist(idResource, trackId);

        numOfTrack -= 1;
        assertEquals(numOfTrack, service.getNumberOfResource(idResource, trackId, idResourceStr));
    }
}
