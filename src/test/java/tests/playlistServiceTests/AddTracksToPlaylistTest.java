package tests.playlistServiceTests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import service.PlaylistService;


import static org.testng.AssertJUnit.*;

public class AddTracksToPlaylistTest extends AbstractPlaylistTest {

    private final PlaylistService playlistService = new PlaylistService();

    @Test
    public void addTracksToPlaylistTest(){

       Response response = playlistService.addTracksToPlaylist(idResource, track);
       long lastAddedTrackId = getLastAddedTrackId(response);
       long numOfTrack = playlistService.getNumberOfResource(idResource, lastAddedTrackId, idResourceStr);


       assertEquals(200, response.getStatusCode());
       assertEquals(numOfTrack, playlistService.getNumberOfResource(idResource, track, idResourceStr));

       playlistService.addTracksToPlaylist(idResource, track);
       numOfTrack+= 1;
       assertEquals(numOfTrack, playlistService.getNumberOfResource(idResource, track, idResourceStr));
        }
}
