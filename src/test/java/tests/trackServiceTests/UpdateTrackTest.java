package tests.trackServiceTests;

import com.example.model.Track;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.TrackService;

import static org.testng.AssertJUnit.assertEquals;

public class UpdateTrackTest {

    private  TrackService service;
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
    public void updateTrackDetailsTest(){
        Track newTrack = service.createTrack();
        long id = newTrack.getId();

        Track updatedTrack = service.updateTrackDetails(id);

        assertEquals("newTitle", updatedTrack.getTitle());
        assertEquals("newArtist", updatedTrack.getArtist());
        assertEquals("newAlbum", updatedTrack.getAlbum());
    }
}
