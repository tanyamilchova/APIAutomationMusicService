package service;

import com.example.exseption.ResourceException;
import com.example.exseption.ResourceNotFoundException;
import com.example.model.User;
import com.example.util.TestDataReader;
import com.example.util.URLCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService extends AbstractService{
    Logger logger = LoggerFactory.getLogger(UserService.class);

    String endpoint = URLCreator.userGetAllCreateURL();
    String endpointById =  URLCreator.userGetUpdateDeleteURL();
    String firstName = TestDataReader.getTestData("resource_firstName");
    String lastName = TestDataReader.getTestData("resource_lastName");
    String email = TestDataReader.getTestData("resource_email");
    String updated_firstName = TestDataReader.getTestData("resource_updated_firstName");
    String updated_lastName = TestDataReader.getTestData("resource_updated_lastName");
    String updated_email = TestDataReader.getTestData("resource_updated_email");
    public User getUserById(long id) {
        return getResourceById(User.class, endpointById, id);
    }

    public User createUser() {
    try {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        return createResource(user, endpoint).getResource();
    }catch (Exception e) {
        logger.error("An error occurred while creating  new user at endpoint: {}",endpoint, e);
        throw new ResourceException("An error occurred while creating  new user. " + e.getMessage());
    }

    }
    public User updateUserDetails(long userId) {
        if( !isPresentResource( endpoint,userId)){
            throw new ResourceNotFoundException("User with Id: " + userId + " is not found.");
        }
        try {
        User user = getUserById(userId);
        user.setFirstName(updated_firstName);
        user.setLastName(updated_lastName);
        user.setEmail(updated_email);
        return updateResource(user, endpointById, userId).getResource();
        }catch (Exception e) {
            logger.error("An error occurred while updating user with ID: {} at endpoint: {}",userId, endpoint, e);
            throw new ResourceException("An error occurred while updating user. " + e.getMessage());
        }
    }
    public User deleteUserById(long userId) {
        if( !isPresentResource( endpoint,userId)){
            throw new ResourceNotFoundException("User with Id: " + userId + " is not found.");
        }
        try{
            return deleteResourceById(User.class, endpointById, userId);
        }catch (Exception e) {
            logger.error("An error occurred while deleting user with ID: {} at endpoint: {}",userId, endpoint, e);
            throw new ResourceException("An error occurred while deleting user. " + e.getMessage());
        }
    }

    public  boolean checkIfSuccessfullyDeleted(long removedId) {
        try {
        return super.isPresentResource(endpoint, removedId);
        }catch (Exception e) {
            logger.error("An error occurred while checking if successfully deleted user with ID: {} at endpoint: {}",removedId, endpoint, e);
            throw new ResourceException("An error occurred while checking if successfully deleted user. " + e.getMessage());
        }
    }
}
