package tests.trackServiceTests;

import com.example.model.Track;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import service.TrackService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetTrackById {

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
    public void getTrackByIdTest() {
        long id = track.getId();

        Track track = service.getTrackById(id);

        assertEquals(id, track.getId());
    }
}
