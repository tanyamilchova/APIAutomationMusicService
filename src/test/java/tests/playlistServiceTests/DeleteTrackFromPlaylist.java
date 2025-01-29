package tests.playlistServiceTests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import service.PlaylistService;

import static org.testng.AssertJUnit.assertEquals;

public class DeleteTrackFromPlaylist extends AbstractPlaylistTest {
    private final PlaylistService service = new PlaylistService();


    @Test
    public void deleteTracksFromPlaylistTest(){
        Response response = service.addTracksToPlaylist(idResource, trackToDelete);

        long lastAddeddId = getLastAddedTrackId(response);
        long numOfTrack = service.getNumberOfResource(idResource, lastAddeddId, idResourceStr);

        assertEquals(200, response.getStatusCode());
        assertEquals(numOfTrack, service.getNumberOfResource(idResource, trackToDelete, idResourceStr));

        service.removeTracksFromPlaylist(idResource, trackToDelete);
        numOfTrack -= 1;
        assertEquals(numOfTrack, service.getNumberOfResource(idResource, trackToDelete, idResourceStr));
    }
}
