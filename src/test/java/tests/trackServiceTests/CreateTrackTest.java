package tests.trackServiceTests;

import com.example.model.Track;
import org.junit.jupiter.api.Test;
import service.TrackService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateTrackTest {
   private final TrackService service = new TrackService();

    @Test
    public void createTrackTest() {

        Track track = service.createTrack();

        assertEquals("title",track.getTitle());
        assertEquals("artist",track.getArtist());
        assertEquals("album",track.getAlbum());
        assertEquals(0,track.getDuration());
        assertEquals(0,track.getYear());
    }
}
