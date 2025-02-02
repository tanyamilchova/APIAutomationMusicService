package service;

import com.example.exseption.ResourceNotFoundException;
import com.example.model.PlayList;
import com.example.util.TestDataReader;
import com.example.util.URLCreator;
import com.example.util.Util;
import io.restassured.response.Response;
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


    private final String name = TestDataReader.getTestData("resource_name");
    private final String description = TestDataReader.getTestData("resource_description");
    private final String updatedName = TestDataReader.getTestData("resource_updated_name");
    private final String updatedDescription = TestDataReader.getTestData("resource_updated_description");
    private final String userIdToupdate = TestDataReader.getTestData("resource_to_update");


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
        playList.setName(name);
        playList.setDescription(description);
        playList.setPublic(true);

        return createResource(playList, endpoint).getResource();
    }
    public Response createPlaylistWrongUser(int userId) {

        PlayList playList = createPlaylist(userId);
        return createResource(playList, endpoint).getRawResponse();
    }

    public PlayList updatePlaylistDetails(long playlistId) {
        if( !playlistIsPresent(playlistId)) {
            throw new ResourceNotFoundException("Playlist with Id: " + playlistId + " is not found.");
        }
        try{
        logger.info("Sending PUT request to endpoint: {} to update a playlist details with ID: {}",endpointById, playlistId);
            PlayList playList = new PlayList();
            playList.setUserId(Util.getResourceIdFromProperty());
            playList.setName(updatedName);
            playList.setDescription(updatedDescription);
            playList.setPublic(true);

            return updateResource(playList, endpointById, playlistId).getResource();
        }catch (Exception e) {
            logger.error("An error occurred while updating  a playlist details of with Id : {} at endpoint: {}",  playlistId, endpoint, e);
            throw new RuntimeException("An error occurred while updating a playlist : " + e.getMessage(), e);
        }
    }
    public Response updatePlaylistUserId(long playlistId) {
        if( !playlistIsPresent(playlistId)) {
            throw new ResourceNotFoundException("Playlist with Id: " + playlistId + " is not found.");
        }
        try {
            logger.info("Sending PUT request to endpoint: {} to update a User of playlist with ID: {}",endpointById, playlistId);
            PlayList playList = getPlaylistById(playlistId);
            playList.setTracks(null);
            playList.setUserId(Long.parseLong(userIdToupdate));
            return updateResource(playList, endpointById, playlistId).getRawResponse();
        }catch (Exception e) {
            logger.error("An error occurred while updating  a playlist with Id : {} at endpoint: {}",  playlistId, endpoint, e);
            throw new RuntimeException("An error occurred while updating a playlist : " + e.getMessage(), e);
        }
    }

    public PlayList deletePlaylistById(long playlistId) {
        if( !playlistIsPresent(playlistId)) {
            throw new ResourceNotFoundException("Playlist with Id: " + playlistId + " is not found.");
        }
        try {
            logger.info("Sending DELETE request to endpoint: {} to delete a playlist with ID: {}",endpointById, playlistId);
            return deleteResourceById(PlayList.class, endpointById, playlistId);
        }catch (Exception e) {
        logger.error("An error occurred while deleting  a playlist with Id : {} at endpoint: {}",  playlistId, endpointById, e);
        throw new RuntimeException("An error occurred while deleting a playlist : " + e.getMessage(), e);
        }
    }


    public Response addTracksToPlaylist(long playlistId, long trackId) {
        try {
            getValidateResponseResourceById(endpointById, playlistId);

            String idBody = createTrackRequestBody(trackId);
            logger.info("Sending POST request to endpoint: {} to add track with ID: {} from playlist Id: {}",endpointAddTrack, trackId, playlistId);
            return given()
                    .pathParams("id", playlistId)
                    .header("Content-Type", "application/json")
                    .body(idBody)
                    .when()
                    .post(endpointAddTrack);
        }catch (Exception e) {
            logger.error("An error occurred while adding  a resource with Id : {} at endpoint: {} in playlistId: {}",trackId, endpointAddTrack, playlistId, e);
            throw new RuntimeException("An error occurred while adding a track to playlist : " + e.getMessage(), e);
        }
    }



    public void deleteTracksFromPlaylist(long playlistId, long trackId) {
        try {
            getValidateResponseResourceById(endpointById, playlistId);

            String idBody = createTrackRequestBody(trackId);
            logger.info("Sending DELETE request to endpoint: {} to delete track with ID: {} from playlist Id: {}",endpoint, trackId, playlistId);
            given()
                    .pathParams("id", playlistId)
                    .header("Content-Type", "application/json")
                    .body(idBody)
                    .when()
                    .delete(endpointDeleteTrack);

        } catch (Exception e) {
            logger.error("An error occurred while deleting a resource Id: {} at endpoint: {} from playlistId: {}",trackId, endpoint, playlistId, e);
            throw new RuntimeException("An error occurred while removing a track from playlist: " + e.getMessage(), e);
        }
    }

    public  long getNumberOfResource(long playListId, long trackId ,String listData) {
        try {
        logger.info("Getting number of tracks with Id: {} to endpoint: {} from playlist with ID: {}  ",trackId, endpoint, playListId);
        return super.getNumberOfResources(playListId, trackId, endpointGetTracks, listData);
        } catch (Exception e) {
            logger.error("An error occurred while Getting number of tracks with Id: {} to endpoint: {} from playlist with ID: {}  ",trackId, endpoint, playListId);
            throw new RuntimeException("An error occurred while getting the number of tracks  " + e.getMessage(), e);
        }
    }


    public boolean playlistIsPresent(long playlistId) {
        String listPath = TestDataReader.getTestData("response.list_playlists_id");
        logger.info("Checking ifPresent playlist with Id: {} to endpoint: {}",playlistId, endpoint);
        return super.isPresentResource(endpoint, playlistId);
    }

    private String createTrackRequestBody(long trackId) {
        return "{\"trackId\": " + trackId + " }";
    }
}
