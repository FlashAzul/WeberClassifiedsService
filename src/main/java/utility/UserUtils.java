package utility;

import model.User;
import org.springframework.util.StringUtils;
import presentation.UserPresentation;
import repository.UserRepository;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuel on 7/6/17.
 * Group of helper methods used with Users
 */
public class UserUtils {

    public static List<UserPresentation> buildUserPresentation (List<User> userModels) {
        List<UserPresentation> userPresentations = new ArrayList<>();
        for (User userModel : userModels) {
            UserPresentation userPresentation = buildUserPresentation(userModel);
            userPresentations.add(userPresentation);
        }
        return userPresentations;
    }

    public static UserPresentation buildUserPresentation (User userModel) {
        UserPresentation userPresentation = new UserPresentation();
        userPresentation.setId(userModel.getId());
        userPresentation.setUserName(userModel.getUserName());
        userPresentation.setAddress(userModel.getAddress());
        userPresentation.setEmail(userModel.getEmail());
        userPresentation.setFirstName(userModel.getFirstName());
        userPresentation.setLastName(userModel.getLastName());
        userPresentation.setwNumber(userModel.getwNumber());
        userPresentation.setAccessLevel(userModel.getAccessLevel());
        return userPresentation;
    }

    public static User buildUserModel (UserRepository userRepository, UserPresentation userPresentation) {
        User userModel = userRepository.getById(userPresentation.getId());
        if (userModel == null) {
            userModel = new User();
        }
        if (StringUtils.isEmpty(userModel.getSalt()) && !StringUtils.isEmpty(userPresentation.getPassword())) {
            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[20];
            random.nextBytes(bytes);
            String salt = new String(bytes);
            userModel.setSalt(salt);
            userModel.setHashedPassword(AuthenticationUtils.hashPassword(userPresentation.getPassword(), salt));
        }
        userModel.setAccessLevel(userPresentation.getAccessLevel());
        userModel.setAddress(userPresentation.getAddress());
        userModel.setEmail(userPresentation.getEmail());
        userModel.setFirstName(userPresentation.getFirstName());
        userModel.setLastName(userPresentation.getLastName());
        userModel.setUserName(userPresentation.getUserName());
        userModel.setwNumber(userPresentation.getwNumber());
        return userModel;
    }
}
