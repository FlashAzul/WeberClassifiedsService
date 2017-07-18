package resource;

import application.ApplicationConstants;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repository.UserRepository;
import representation.UserRepresentation;
import utility.AuthorizationUtils;
import utility.UserUtils;

import static application.ApplicationConstants.AUTH_TOKEN_HEADER;
import static application.ApplicationConstants.ID;
import static application.ApplicationConstants.TOKEN_TYPE_AUTH;
import static application.ApplicationConstants.USER_RESOURCE;

/**
 * Created by samuel on 5/30/17.
 * Class Representing the /user endpoint of the service.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(USER_RESOURCE)
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getUserById (@PathVariable(ID) Long userId, @RequestHeader(AUTH_TOKEN_HEADER) String token) {

        if (AuthorizationUtils.validateUserAuthorization(token, ApplicationConstants.AccessLevel.ADMIN,
                TOKEN_TYPE_AUTH, userRepository)) {
            User user = userRepository.read(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with Id: " + userId + " not " + "found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(UserUtils.buildUserPresentation(user));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateUser (@RequestBody UserRepresentation user, @RequestHeader(AUTH_TOKEN_HEADER) String
            token) {
        if (AuthorizationUtils.validateUserAuthorization(token, ApplicationConstants.AccessLevel.ADMIN,
                TOKEN_TYPE_AUTH, userRepository)) {
            if (!userRepository.getByUserName(user.getUserName()).getId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with username '" + user.getUserName()
                        + "' already exists.");
            }
            User updatedUserModel = UserUtils.buildUserModel(userRepository, user);
            if (updatedUserModel == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with Id: " + user.getId() + " not " +
                        "found");
            }
            userRepository.update(updatedUserModel);
            return ResponseEntity.status(HttpStatus.OK).body(UserUtils.buildUserPresentation(userRepository.read
                    (updatedUserModel.getId())));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteUser (@PathVariable(ID) Long userId, @RequestHeader(AUTH_TOKEN_HEADER) String token) {

        if (AuthorizationUtils.validateUserAuthorization(token, ApplicationConstants.AccessLevel.ADMIN,
                TOKEN_TYPE_AUTH, userRepository)) {
            User user = userRepository.read(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with Id: " + userId + " not " + "found");
            }
            userRepository.delete(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User " + userId + " successfully deleted");
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }
}
