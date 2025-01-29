package com.example.util;

import static com.example.util.Util.validateProperty;

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

    //URLCreator
    //
    //Redundant try-catch: Each method effectively does the same thing - validates and retrieves property data but
    // wraps it with a try-catch that rethrows as a RuntimeException.
    // Consider refactoring to reduce redundancy and enhance clarity.
    public static String userGetUpdateDeleteURL() {
        try {
            validateProperty(TestDataReader.getTestData(USER_GET_UPDATE_DELETE));
            return TestDataReader.getTestData(USER_GET_UPDATE_DELETE);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get USER_GET_ADD_DELETE from properties: " + e.getMessage(), e);
        }
    }

    public static String userGetAllCreateURL() {
        try {
            validateProperty(TestDataReader.getTestData(USER_GET_ALL_CREATE));
            return TestDataReader.getTestData(USER_GET_ALL_CREATE);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get USER_GET_ALL from properties: " + e.getMessage(), e);
        }
    }

    public static String playlistGetAllCreateURL() {
        try {
            validateProperty(TestDataReader.getTestData(PLAYLIST_GET_ALL_CREATE));
            return TestDataReader.getTestData(PLAYLIST_GET_ALL_CREATE);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get PLAYLIST_GET_ALL_CREATE from properties: " + e.getMessage(), e);
        }
    }

    public static String playlistGetPutDeleteByIdURL() {
        try {
            validateProperty(TestDataReader.getTestData(PLAYLIST_GET_PUT_DELETE_BY_ID));
            return TestDataReader.getTestData(PLAYLIST_GET_PUT_DELETE_BY_ID);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get PLAYLIST_GET_PUT_DELETE_BY_ID from properties: " + e.getMessage(), e);
        }
    }

    public static String playlistAddTrackURL() {
        try {
            validateProperty(TestDataReader.getTestData(PLAYLIST_ADD_TRACK));
            return TestDataReader.getTestData(PLAYLIST_ADD_TRACK);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get PLAYLIST_ADD_TRACK from properties: " + e.getMessage(), e);
        }
    }

    public static String playlistRemoveTrackURL() {
        try {
            validateProperty(TestDataReader.getTestData(PLAYLIST_REMOVE_TRACK));
            return TestDataReader.getTestData(PLAYLIST_REMOVE_TRACK);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get PLAYLIST_REMOVE_TRACK from properties: " + e.getMessage(), e);
        }
    }

    public static String trackGetAllCreateURL() {
        try {
            validateProperty(TestDataReader.getTestData(TRACK_GET_CREATE));
            return TestDataReader.getTestData(TRACK_GET_CREATE);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get TRACK_GET_CREATE from properties: " + e.getMessage(), e);
        }
    }

    public static String trackGetPutDeleteByIdURL() {
        try {
            validateProperty(TestDataReader.getTestData(TRACK_GET_PUT_DELETE));
            return TestDataReader.getTestData(TRACK_GET_PUT_DELETE);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get TRACK_GET_PUT_DELETE from properties: " + e.getMessage(), e);
        }
    }

    public static String playlistGetTracksURL() {
        try {
            validateProperty(TestDataReader.getTestData(PLAYLIST_GET_TRACKS));
            return TestDataReader.getTestData(PLAYLIST_GET_TRACKS);
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to get PLAYLIST_GET_TRACKS from properties: " + e.getMessage(), e);
        }
    }
}
