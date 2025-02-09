package tests.trackServiceTests;

import com.example.model.Track;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.TrackService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateTrackTest {
   private  TrackService service = new TrackService();
   private Track track;
    @BeforeMethod
    public void seetup(){
        service = new TrackService();
        track = service.createTrack();
    }
    @AfterMethod
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
