package service;

import com.example.model.PlayList;
import com.example.util.TestDataReader;
import com.example.util.URLCreator;
import com.example.util.Util;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.restassured.RestAssured.given;


public class PlaylistService extends AbstractService{
    Logger logger = LoggerFactory.getLogger(PlaylistService.class);

    private TrackService trackService = new TrackService();

    private final String endpoint = URLCreator.playlistGetAllCreateURL();
    private final String endpointGetTracks = URLCreator.playlistGetTracksURL();
    private final String endpointById = URLCreator.playlistGetPutDeleteByIdURL();
    private final String endpointTrackById = URLCreator.trackGetPutDeleteByIdURL();
    private final String endpointAddTrack = URLCreator.playlistAddTrackURL();
    private final String endpointDeleteTrack = URLCreator.playlistRemoveTrackURL();



    public Response getAllPlaylists() {
        logger.info("Try to get all playlist");
        return given()
                .when()
                .get(endpoint);

    }
    public PlayList getPlaylistById(long playlistId) {
        logger.info("Try to get playlist by Id");
            return getResourceById( PlayList.class, endpointById, playlistId);
    }


    public PlayList createPlaylist(long userId) {

        PlayList playList = new PlayList();
        playList.setUserId(userId);
        playList.setName("name");
        playList.setDescription("description");
        playList.setPublic(true);

        return createResource(playList, endpoint).getResource();
    }
    public Response createPlaylistWrongUser(int userId) {

        PlayList playList = createPlaylist(userId);
        return createResource(playList, endpoint).getRawResponse();
    }

    public PlayList updatePlaylistDetails(long playlistId) {
        PlayList playList = new PlayList();
        playList.setUserId(Util.getResourceIdFromProperty());
        playList.setName("UpdatedName");
        playList.setDescription("UpdatedDescription");
        playList.setPublic(true);

        return updateResource(playList, endpointById, playlistId).getResource();
    }
    public Response updatePlaylistUserId(long playlistId) {
       PlayList playList = getPlaylistById(playlistId);
        playList.setTracks(null);
        playList.setUserId(5);
        return  updateResource(playList, endpointById, playlistId).getRawResponse();
    }

        public PlayList deletePlaylistById(long playlistId) {

            return deleteResourceById(PlayList.class, endpointById, playlistId);
        }


    public Response addTracksToPlaylist(long playlistId, long trackId) {
        getValidateResponseResourceById(endpointById, playlistId);

        String idBody = createTrackRequestBody(playlistId, trackId);

        return given()
                .pathParams("id",playlistId)
                .header("Content-Type", "application/json")
                .body(idBody)
                .when()
                .post(endpointAddTrack);
    }



    public Response removeTracksFromPlaylist(long playlistId, long trackId) {
        try {
            getValidateResponseResourceById(endpointById, playlistId);

            String idBody = createTrackRequestBody(playlistId, trackId);

            return given()
                    .pathParams("id", playlistId)
                    .header("Content-Type", "application/json")
                    .body(idBody)
                    .when()
                    .delete(endpointDeleteTrack);

        } catch (Exception e) {
            logger.error("An error occurred while deleting a resource is present at endpoint: {} with resourceId: {}", endpoint, trackId, e);
            throw new RuntimeException("An error occurred while deleting a resource is present: " + e.getMessage(), e);
        }
    }

    public  long getNumberOfResource(long playListId, long trackId ,String listData) {

        return super.getNumberOfResources(playListId, trackId, endpointGetTracks, listData);
    }

    public boolean resourceIsPresent(long resourceId) {
        String listPath = TestDataReader.getTestData("response.list_playlists_id");

        return super.isPresentResource(endpoint, resourceId, listPath);
    }

    private String createTrackRequestBody(long playlistId, long trackId) {

        getValidateResponseResourceById(endpointById, playlistId);
        ValidatableResponse response = getValidateResponseResourceById(endpointTrackById, trackId);
        int trackIdExtracted = response.extract().jsonPath().getInt("id");

        return "{\"trackId\": " + trackIdExtracted + " }";
    }
}
