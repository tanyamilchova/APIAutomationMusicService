package service;

import com.example.exseption.ResourceException;
import com.example.exseption.ResourceNotFoundException;
import com.example.model.Track;
import com.example.util.TestDataReader;
import com.example.util.URLCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrackService extends AbstractService{
    Logger logger = LoggerFactory.getLogger(PlaylistService.class);

    private final String endpoint = URLCreator.trackGetAllCreateURL();
    private final String endpointById = URLCreator.trackGetPutDeleteByIdURL();
    private final String title = TestDataReader.getTestData("response.tracks_title");
    private final String artist = TestDataReader.getTestData("response.tracks_artist");
    private final int duration = Integer.parseInt(TestDataReader.getTestData("response.tracks_duration"));
    private final int year = Integer.parseInt(TestDataReader.getTestData("response.tracks_year"));
    private final String album = TestDataReader.getTestData("response.tracks_album");

    public Track getTrackById(long trackId) {
        checkIfZeroOrNegative(trackId);
        try {
            return getResourceById( Track.class, endpointById, trackId);
        } catch (Exception e) {
            logger.error("An error occurred while getting  a track with Id : {} at endpoint: {} ", trackId, endpoint, e);
            throw new RuntimeException("An error occurred while getting a track from playlist : " + e.getMessage(), e);
        }
    }

    public Track createTrack() {
    try {
        Track track = new Track();
        track.setTitle(title);
        track.setArtist(artist);
        track.setDuration(duration);
        track.setYear(year);
        track.setAlbum(album);

        return createResource(track, endpoint).getResource();
    }catch (Exception e) {
        logger.error("An error occurred while creating  new track at endpoint: {}",endpoint, e);
        throw new RuntimeException("An error occurred while creating  new track. " + e.getMessage(), e);
    }
    }

    public Track updateTrackDetails(long trackId) {
        if( !isPresentResource(endpoint, trackId)){
            throw new ResourceException("Track with Id: " + trackId + " is not found.");
        }
        try{
        Track track = getTrackById(trackId);
        track.setTitle(TestDataReader.getTestData("resource_updated_title"));
        track.setArtist(TestDataReader.getTestData("resource_updated_artist"));
        track.setAlbum(TestDataReader.getTestData("resource_updated_album"));
        track.setDuration(Integer.parseInt(TestDataReader.getTestData("resource_updated_duration")));
        track.setYear(Integer.parseInt(TestDataReader.getTestData("resource_updated_year")));

        return updateResource(track, endpointById, trackId).getResource();
        }catch (Exception e) {
            logger.error("An error occurred while updating track details at endpoint: {}",endpoint, e);
            throw new RuntimeException("An error occurred while updating track details. " + e.getMessage(), e);
        }
    }
    public void deleteTrackById(long trackId) {
        if( !isPresentResource(endpoint, trackId)){
            throw new ResourceNotFoundException("Track with Id: " + trackId + " is not found.");
        }
        try {
            deleteResourceById(Track.class, endpointById, trackId);
        } catch (Exception e) {
            logger.error("An error occurred while deleting  a track with Id : {} at endpoint: {} ", trackId, endpoint, e);
            throw new ResourceException("An error occurred while deleting a track from playlist : " + e.getMessage());
        }
    }

    public  boolean trackExists(long removedId) {
        try{
        return super.isPresentResource(endpoint, removedId);
        } catch (Exception e) {
            logger.error("An error occurred while checking if exist track with Id : {} at endpoint: {} ", removedId, endpoint, e);
            throw new ResourceException("An error occurred while checking if exist track : " + e.getMessage());
        }
    }

    public long getRandomTrackId(){
        try{
        return super.getRandomResourceId(Track.class, endpoint);
        } catch (Exception e) {
            logger.error("An error occurred while getting a random track at endpoint: {} ", endpoint, e);
            throw new ResourceException("An error occurred while getting a random track : " + e.getMessage());
        }
    }
}
