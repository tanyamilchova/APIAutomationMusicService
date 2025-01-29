package com.example.util;

import static com.example.util.Util.validateNullAndEmptyProperty;

public class URLCreator {
    private static final String USER_GET_UPDATE_DELETE = "user.get_put_delete_by_id";
    private static final String USER_GET_ALL_CREATE = "user.get-all_create";

    private static final String PLAYLIST_GET_ALL_CREATE = "playlist.get-all_create";
    private static final String PLAYLIST_GET_PUT_DELETE_BY_ID = "playlist.get_put_delete_by_id";
    private static final String PLAYLIST_GET_TRACKS = "playlist.get_tracks_of_playlist";
    private static final String PLAYLIST_ADD_TRACK = "playlist.add_track_to_playlist";
    private static final String PLAYLIST_REMOVE_TRACK = "playlist.remove_track_to_playlist";

    private static final String TRACK_GET_CREATE = "track.get-all_create";
    private static final String TRACK_GET_PUT_DELETE = "track_get_put_delete_by_id";


    public static String userGetUpdateDeleteURL() {
        return playlistGetURL(USER_GET_UPDATE_DELETE);
    }

    public static String userGetAllCreateURL() {
        return playlistGetURL(USER_GET_ALL_CREATE);
    }

    public static String playlistGetAllCreateURL() {
        return playlistGetURL(PLAYLIST_GET_ALL_CREATE);
    }

    public static String playlistGetPutDeleteByIdURL() {
        return playlistGetURL(PLAYLIST_GET_PUT_DELETE_BY_ID);
    }

    public static String playlistAddTrackURL() {
        return playlistGetURL(PLAYLIST_ADD_TRACK);
    }

    public static String playlistRemoveTrackURL() {
        return playlistGetURL(PLAYLIST_REMOVE_TRACK);
    }

    public static String trackGetAllCreateURL() {
        return playlistGetURL(TRACK_GET_CREATE);
    }

    public static String trackGetPutDeleteByIdURL() {
        return playlistGetURL(TRACK_GET_PUT_DELETE);
    }

    public static String playlistGetTracksURL() {
        return playlistGetURL(PLAYLIST_GET_TRACKS);
    }

    public static String playlistGetURL(String url) {
        try {
            validateNullAndEmptyProperty(TestDataReader.getTestData(url));
            return TestDataReader.getTestData(url);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get PLAYLIST_GET_TRACKS from properties: " + e.getMessage(), e);
        }
    }
}
