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
public class UserUtils {

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
        if (StringUtils.isEmpty(userModel.getSalt()) && !StringUtils.isEmpty(userRepresentation.getPassword())) {
            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[20];
            random.nextBytes(bytes);
            String salt = new String(bytes);
            userModel.setSalt(salt);
            userModel.setHashedPassword(AuthenticationUtils.hashPassword(userRepresentation.getPassword(), salt));
        }
        userModel.setAccessLevel(userRepresentation.getAccessLevel());
        userModel.setAddress(userRepresentation.getAddress());
        userModel.setEmail(userRepresentation.getEmail());
        userModel.setFirstName(userRepresentation.getFirstName());
        userModel.setLastName(userRepresentation.getLastName());
        userModel.setPhone(userRepresentation.getPhone());
        userModel.setUserName(userRepresentation.getUserName());
        userModel.setwNumber(userRepresentation.getwNumber());
        return userModel;
    }
}
