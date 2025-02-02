package tests.trackServiceTests;

import com.example.model.Track;
import org.junit.jupiter.api.Test;
import service.TrackService;

import static org.testng.AssertJUnit.assertEquals;

public class UpdateTrackTest {
    private final TrackService service = new TrackService();

    @Test
    public void updateTrackDetailsTest(){
        Track newTrack = service.createTrack();
        long id = newTrack.getId();

        Track updatedTrack = service.updateTrackDetails(id);

        assertEquals("newTitle", updatedTrack.getTitle());
        assertEquals("newArtist", updatedTrack.getArtist());
        assertEquals("newAlbum", updatedTrack.getAlbum());
    }
}
