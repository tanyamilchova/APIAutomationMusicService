package service;

import com.example.model.Track;
import com.example.util.TestDataReader;
import com.example.util.URLCreator;

public class TrackService extends AbstractService{

    final String endpoint = URLCreator.trackGetAllCreateURL();
    final String endpointById = URLCreator.trackGetPutDeleteByIdURL();


    public Track getTrackById(long trackId) {

        return getResourceById( Track.class, endpointById, trackId);
    }

    public Track createTrack() {

        Track track = new Track();
        track.setTitle("title");
        track.setArtist("artist");
        track.setDuration(0);
        track.setYear(0);
        track.setAlbum("album");

        return createResource(track, endpoint).getResource();
    }

    public Track updateTrackDetails(long trackId) {

        Track track = getTrackById(trackId);
        track.setTitle("NewTitle");
        track.setArtist("NewArtist");
        track.setAlbum("NewAlbum");
        track.setDuration(1);
        track.setYear(2000);
        return updateResource(track, endpointById, trackId).getResource();
    }
    public Track deleteTrackById(long userId) {

        return deleteResourceById(Track.class, endpointById, userId);
    }

    public  boolean checkIfSuccessfullyDeleted(long removedId) {
        String listData = TestDataReader.getTestData("response.tracks");
        return super.isPresentResource(endpoint, removedId, listData);
    }
}
