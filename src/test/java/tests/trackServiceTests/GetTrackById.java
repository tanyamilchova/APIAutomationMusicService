package tests.trackServiceTests;

import com.example.model.Track;
import com.example.util.Util;
import org.junit.jupiter.api.Test;
import service.TrackService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetTrackById {
    @Test
    public void getTrackByIdTest() {
        TrackService trackService = new TrackService();
        long id = Util.getResourceIdFromProperty();

        Track track = trackService.getTrackById(id);
        System.out.println(track.getId());

        assertEquals(id, track.getId());
    }
}
