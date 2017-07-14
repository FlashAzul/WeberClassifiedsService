package resource;

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
import presentation.UserPresentation;
import repository.CacheUserRepository;
import repository.UserRepository;
import utility.AuthorizationUtils;
import utility.UserUtils;

import static resource.ResourceConstants.AUTH_TOKEN_HEADER;
import static resource.ResourceConstants.USERS_RESOURCE;
import static resource.ResourceConstants.WEBER_CLASSIFIEDS;

/**
 * Created by samuel on 7/12/17.
 * Class Representing the /users endpoint of the service.
 */
@CrossOrigin
@RestController
@RequestMapping(WEBER_CLASSIFIEDS + USERS_RESOURCE)
public class UsersResource {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getUsers (@RequestHeader(AUTH_TOKEN_HEADER) String authToken) {
        if (AuthorizationUtils.validateUserAuthorization(authToken, CacheUserRepository.AccessLevel.ADMIN,
                userRepository)) {
            return ResponseEntity.status(HttpStatus.OK).body(UserUtils.buildUserPresentation(userRepository.getAll()));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser (@RequestBody UserPresentation user, @RequestHeader(AUTH_TOKEN_HEADER) String
            authToken) {

        if (AuthorizationUtils.validateUserAuthorization(authToken, CacheUserRepository.AccessLevel.ADMIN,
                userRepository)) {
            if (userRepository.getByUserName(user.getUserName()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with username '" + user.getUserName()
                        + "' already exists.");
            }
            User newUser = UserUtils.buildUserModel(userRepository, user);
            userRepository.create(newUser);
            return ResponseEntity.status(HttpStatus.OK).body(UserUtils.buildUserPresentation(userRepository
                    .getByUserName(user.getUserName())));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }
}
