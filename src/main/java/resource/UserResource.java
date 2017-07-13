package resource;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import presentation.UserPresentation;
import repository.CacheUserRepository;
import repository.UserRepository;
import utility.AuthorizationUtils;
import utility.UserUtils;

import static resource.ResourceConstants.AUTH_TOKEN_HEADER;
import static resource.ResourceConstants.ID;
import static resource.ResourceConstants.USER_RESOURCE;
import static resource.ResourceConstants.WEBER_CLASSIFIEDS;

/**
 * Created by samuel on 5/30/17.
 * Class Representing the /user endpoint of the service.
 */
@RestController
@RequestMapping(WEBER_CLASSIFIEDS + USER_RESOURCE)
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getUserById (@PathVariable(ID) Long userId, @RequestHeader(AUTH_TOKEN_HEADER) String
            authToken) {

        if (AuthorizationUtils.validateUserAuthorization(authToken, CacheUserRepository.AccessLevel.ADMIN,
                userRepository)) {
            User user = userRepository.getById(userId);
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
    public ResponseEntity updateUser (@RequestBody UserPresentation user, @RequestHeader(AUTH_TOKEN_HEADER) String
            authToken) {
        if (AuthorizationUtils.validateUserAuthorization(authToken, CacheUserRepository.AccessLevel.ADMIN,
                userRepository)) {
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
            return ResponseEntity.status(HttpStatus.OK).body(UserUtils.buildUserPresentation(userRepository.getById
                    (updatedUserModel.getId())));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteUser (@PathVariable(ID) Long userId, @RequestHeader(AUTH_TOKEN_HEADER) String
            authToken) {

        if (AuthorizationUtils.validateUserAuthorization(authToken, CacheUserRepository.AccessLevel.ADMIN,
                userRepository)) {
            User user = userRepository.getById(userId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with Id: " + userId + " not " + "found");
            }
            userRepository.delete(user);
            return ResponseEntity.status(HttpStatus.OK).body("User " + userId + " successfully deleted");
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }
}
