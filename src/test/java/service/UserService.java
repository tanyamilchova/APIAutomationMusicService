package service;

import com.example.model.User;
import com.example.util.TestDataReader;
import com.example.util.URLCreator;

public class UserService extends AbstractService{

    String endpoint = URLCreator.userGetAllCreateURL();
    String endpointById =  URLCreator.userGetUpdateDeleteURL();

    public User getUserById(long id) {
        return getResourceById(User.class, endpointById, id);
    }

    public User createUser() {

        User user = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("email");

        return createResource(user, endpoint).getResource();

    }
    public User updateUserDetails(long userId) {

        User user = getUserById(userId);
        user.setFirstName("NewName");
        user.setLastName("NewLastName");
        user.setEmail("NewEmail");
        return updateResource(user, endpointById, userId).getResource();

    }
    public User deleteUserById(long userId) {
        return deleteResourceById(User.class, endpointById, userId);
    }

    public  boolean checkIfSuccessfullyDeleted(long removedId) {
        String listData = TestDataReader.getTestData("response.list_users_id");
        return super.isPresentResource(endpoint, removedId, listData);
    }
}
