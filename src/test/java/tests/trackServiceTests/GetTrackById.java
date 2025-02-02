package tests.trackServiceTests;

import com.example.model.Track;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.TrackService;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetTrackById {

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
    public void getTrackByIdTest() {
        long id = track.getId();

        Track track = service.getTrackById(id);

        assertEquals(id, track.getId());
    }
}
