package tests.trackServiceTests;

import com.example.model.Track;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TrackService;

import static org.testng.AssertJUnit.assertEquals;

public class UpdateTrackTest {

    private  TrackService service;
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
    public void updateTrackDetailsTest(){
        Track newTrack = service.createTrack();
        long id = newTrack.getId();

        Track updatedTrack = service.updateTrackDetails(id);

        assertEquals("newTitle", updatedTrack.getTitle());
        assertEquals("newArtist", updatedTrack.getArtist());
        assertEquals("newAlbum", updatedTrack.getAlbum());
    }
}
