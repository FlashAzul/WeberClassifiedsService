package resource;

import application.ApplicationConstants;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import static application.ApplicationConstants.TOKEN_TYPE_AUTH;
import static application.ApplicationConstants.USERS_RESOURCE;

/**
 * Created by samuel on 7/12/17.
 * Class Representing the /users endpoint of the service.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(USERS_RESOURCE)
public class UsersResource {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getUsers (@RequestHeader(AUTH_TOKEN_HEADER) String authToken) {
        if (AuthorizationUtils
                .validateUserAuthorization(authToken, ApplicationConstants.AccessLevel.ADMIN, TOKEN_TYPE_AUTH,
                        userRepository)) {
            return ResponseEntity.status(HttpStatus.OK).body(UserUtils.buildUserPresentation(userRepository.getAll()));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser (@RequestBody UserRepresentation user,
                                      @RequestHeader(AUTH_TOKEN_HEADER) String token) {

        if (AuthorizationUtils.validateUserAuthorization(token, ApplicationConstants.AccessLevel.ADMIN, TOKEN_TYPE_AUTH,
                userRepository)) {
            if (userRepository.getByUserName(user.getUserName()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("A user with username '" + user.getUserName() + "' already exists.");
            }
            User newUser = UserUtils.buildUserModel(userRepository, user);
            userRepository.create(newUser);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(UserUtils.buildUserPresentation(userRepository.getByUserName(user.getUserName())));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }
}
