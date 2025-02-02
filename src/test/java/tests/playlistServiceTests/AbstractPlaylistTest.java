package tests.playlistServiceTests;

import com.example.model.PlayList;
import com.example.util.TestDataReader;
import com.example.util.Util;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.PlaylistService;

import java.util.List;
import java.util.Map;

@Slf4j
public class AbstractPlaylistTest {

    static Logger logger = LoggerFactory.getLogger(AbstractPlaylistTest.class);
    protected long idResource = Util.getResourceIdFromProperty();
    protected long track = Util.getResourceIdFromProperty();
    protected String idResourceStr = TestDataReader.getTestData("response.list.path_id");
    public String tracksResourceStr = TestDataReader.getTestData("response.tracks");


    protected  PlayList getCreatedPlaylist(long userId){
        PlaylistService service = new PlaylistService();
        logger.info("Playlist creation for user: {}", userId);
        return service.createPlaylist(userId);
    }

    public  long getLastAddedTrackId(Response response) {
        try {
            if (response == null) {
                throw new IllegalArgumentException("Response cannot be null");
            }

            List<Map<String, Object>> allTracks = response.jsonPath().getList(tracksResourceStr);

            if (allTracks == null || allTracks.isEmpty()) {
                throw new IllegalStateException("No tracks found in the response");
            }

            Map<String, Object> lastTrack = allTracks.get(allTracks.size() - 1);

            if (lastTrack == null || lastTrack.isEmpty()) {
                throw new IllegalStateException("Last track data is missing or invalid");
            }

            String idPath = TestDataReader.getTestData("response.list.path_id");
            if (idPath == null || idPath.isBlank()) {
                throw new IllegalArgumentException("ID path from test data cannot be null or blank");
            }

            Object idValue = lastTrack.get(idPath);
            if (!(idValue instanceof Number)) {
                throw new IllegalStateException("Invalid ID value: " + idValue);
            }

            return ((Number) idValue).longValue();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid argument provided", e);
            throw e;
        } catch (IllegalStateException e) {
            logger.error("Invalid state encountered while retrieving the last added track ID", e);
            throw e;
        } catch (Exception e) {
            logger.error("An unexpected error occurred while retrieving the last added track ID", e);
            throw new RuntimeException("Failed to retrieve the last added track ID: " + e.getMessage(), e);
        }
    }
}
