package utility;

import model.User;
import org.springframework.util.StringUtils;
import repository.UserRepository;
import representation.UserRepresentation;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuel on 7/6/17.
 * Group of helper methods used with Users
 */
public class UserUtility {

    public static List<UserRepresentation> buildUserRepresentation (List<User> userModels) {
        List<UserRepresentation> userRepresentations = new ArrayList<>();
        for (User userModel : userModels) {
            UserRepresentation userRepresentation = buildUserRepresentation(userModel);
            userRepresentations.add(userRepresentation);
        }
        return userRepresentations;
    }

    public static UserRepresentation buildUserRepresentation (User userModel) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setId(userModel.getId());
        userRepresentation.setUserName(userModel.getUserName());
        userRepresentation.setAddress(userModel.getAddress());
        userRepresentation.setEmail(userModel.getEmail());
        userRepresentation.setFirstName(userModel.getFirstName());
        userRepresentation.setLastName(userModel.getLastName());
        userRepresentation.setPhone(userModel.getPhone());
        userRepresentation.setwNumber(userModel.getwNumber());
        userRepresentation.setAccessLevel(userModel.getAccessLevel());
        return userRepresentation;
    }

    public static User buildUserModel (UserRepository userRepository, UserRepresentation userRepresentation) {
        User userModel = userRepository.read(userRepresentation.getId());
        if (userModel == null) {
            userModel = new User();
        }
        else {
            userModel.setId(userRepresentation.getId());
        }
        if (StringUtils.isEmpty(userModel.getSalt()) || !StringUtils.isEmpty(userRepresentation.getPassword())) {
            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[20];
            random.nextBytes(bytes);
            String salt = new String(bytes);
            userModel.setSalt(salt);
            userModel.setHashedPassword(AuthenticationUtility.hashPassword(userRepresentation.getPassword(), salt));
        }
        if (userRepresentation.getAccessLevel() != null) {
            userModel.setAccessLevel(userRepresentation.getAccessLevel());
        }
        if (userRepresentation.getAddress() != null) {
            userModel.setAddress(userRepresentation.getAddress());
        }
        if (userRepresentation.getEmail() != null) {
            userModel.setEmail(userRepresentation.getEmail());
        }
        if (userRepresentation.getFirstName() != null) {
            userModel.setFirstName(userRepresentation.getFirstName());
        }
        if (userRepresentation.getLastName() != null) {
            userModel.setLastName(userRepresentation.getLastName());
        }
        if (userRepresentation.getPhone() != null) {
            userModel.setPhone(userRepresentation.getPhone());
        }
        if (userRepresentation.getUserName() != null) {
            userModel.setPhone(userRepresentation.getUserName());
        }
        if (userRepresentation.getwNumber() != null) {
            userModel.setwNumber(userRepresentation.getwNumber());
        }
        return userModel;
    }
}
