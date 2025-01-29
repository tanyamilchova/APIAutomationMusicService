package tests.playlistServiceTests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import service.PlaylistService;

import static org.testng.AssertJUnit.*;

public class AddTracksToPlaylistTest extends AbstractPlaylistTest {

    private final PlaylistService service = new PlaylistService();


    @Test
    public void addTracksToPlaylistTest(){

       Response response = service.addTracksToPlaylist(idResource, track);
       long lastAddedTrackId = getLastAddedTrackId(response);
       long numOfTrack = service.getNumberOfResource(idResource, lastAddedTrackId, idResourceStr);


       assertEquals(200, response.getStatusCode());
       assertEquals(numOfTrack, service.getNumberOfResource(idResource, track, idResourceStr));

       service.addTracksToPlaylist(idResource, track);
       numOfTrack+= 1;
       assertEquals(numOfTrack, service.getNumberOfResource(idResource, track, idResourceStr));
    }
}
