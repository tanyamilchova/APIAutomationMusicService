package tests.trackServiceTests;

import com.example.model.Track;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TrackService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateTrackTest {
   private  TrackService service = new TrackService();
   private Track track;
    @BeforeEach
    public void seetup(){
        service = new TrackService();
        track = service.createTrack();
    }
    @AfterEach
    public void tearDown(){
       service.deleteTrackById(track.getId());
    }

    @Test
    public void createTrackTest() {

        assertEquals("title",track.getTitle());
        assertEquals("artist",track.getArtist());
        assertEquals("album",track.getAlbum());
        assertEquals(0,track.getDuration());
        assertEquals(0,track.getYear());
    }
}
