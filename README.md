# Test Automation Project

This project contains automated API tests for the **User Service** and **Playlist Service** using **TestNG**.

## Project Structure

| Service             | Test Classes                                                                                     |
|---------------------|--------------------------------------------------------------------------------------------------|
| User Service         | CreateUserTest, DeleteUserTest, GetUserByIdTest, UpdateUserTest                                  |
| Playlist Service     | AddTracksToPlaylistTest, CreateAPlayListTest, DeletePlaylistTest, DeleteTrackFromPlaylist, GetPlaylistById, UpdatePlaylistAttributesTest |

## Technologies Used
- Java
- TestNG
- Rest Assured
- Maven

## How to Run the Tests

You can run the test suites from your IDE or using Maven from the command line.

### Run User Service Tests
- TestNG XML: `userServiceTests.xml`
- Tests included:
  - createUserTest
  - deleteUserDetailsTest
  - deleteNotFoundUserTest
  - getUserByIdTest
  - updateUserDetailsTest

### Run Playlist Service Tests
- TestNG XML: `playlistServiceTests.xml`
- Tests included:
  - addTracksToPlaylistTest
  - createPlaylistTest
  - createPlaylistWithNotExistingUserTest
  - deletePlaylistDetailsTest
  - deleteNotFoundPLAYLISTTest
  - deleteTracksFromPlaylistTest
  - getPlaylistByIdTest
  - getIllegalIdPlaylistTest
  - updatePlaylistAttributesTest
  - updatePlaylistUserIdTest

## Run with Maven

```bash
# Run User Service Tests
mvn clean test -DsuiteXmlFile=userServiceTests.xml

# Run Playlist Service Tests
mvn clean test -DsuiteXmlFile=playlistServiceTests.xml
```

## Notes
- Some user-related tests are commented out in the `playlistServiceTests.xml`.
- Authorization tokens are required for making API calls.
- Negative test cases are included for better coverage.

