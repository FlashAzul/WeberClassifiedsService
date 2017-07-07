package utility;

import model.User;
import presentation.UserPresentation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samuel on 7/6/17.
 */
public class UserUtils {

    public static UserPresentation buildUserPresentation (User userModel) {
        UserPresentation userPresentation = new UserPresentation();
        userPresentation.setAddress(userModel.getAddress());
        userPresentation.setEmail(userModel.getEmail());
        userPresentation.setFirstName(userModel.getFirstName());
        userPresentation.setLastName(userModel.getLastName());
        userPresentation.setwNumber(userModel.getwNumber());
        return userPresentation;
    }

    public static List<UserPresentation> buildUserPresentation(List<User> userModels) {
        List<UserPresentation> userPresentations = new ArrayList<>();
        for (User userModel : userModels) {
            UserPresentation userPresentation = buildUserPresentation(userModel);
            userPresentations.add(userPresentation);
        }
        return userPresentations;
    }
}
