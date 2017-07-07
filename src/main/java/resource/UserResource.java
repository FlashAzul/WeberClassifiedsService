package resource;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.UserRepository;
import utility.AuthorizationUtils;
import utility.UserUtils;

import static resource.ResourceConstants.SLASH;
import static resource.ResourceConstants.WNUMBER;

/**
 * Created by samuel on 5/30/17.
 */
@RestController
@RequestMapping(SLASH + ResourceConstants.BASE_DOMAIN + SLASH + ResourceConstants.USER_RESOURCE)
public class UserResource {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody User user, @RequestHeader("AuthToken") String authToken) {
        if (AuthorizationUtils.validateUserAuthorization(authToken)) {
            userRepository.addUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(UserUtils.buildUserPresentation(userRepository.getByWNumber(user.getwNumber())));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity getUsers(@RequestHeader("AuthToken") String authToken) {
        if (AuthorizationUtils.validateUserAuthorization(authToken)) {
            return ResponseEntity.status(HttpStatus.OK).body(UserUtils.buildUserPresentation(userRepository.getAll()));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }

    @RequestMapping(path = ResourceConstants.PATH_VARIABLE_WNUMBER, method=RequestMethod.GET)
    public ResponseEntity getUserByWNumber(@PathVariable(WNUMBER) String wNumber,
                                           @RequestHeader("AuthToken") String authToken) {

        if (AuthorizationUtils.validateUserAuthorization(authToken)) {
            User user = userRepository.getByWNumber(wNumber);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with W Number: " + wNumber + " not found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(UserUtils.buildUserPresentation(user));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }

    }

    @RequestMapping(path = ResourceConstants.PATH_VARIABLE_WNUMBER, method=RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable(WNUMBER) String wNumber,
                                     @RequestHeader("AuthToken") String authToken) {

        if (AuthorizationUtils.validateUserAuthorization(authToken)) {
            User user = userRepository.getByWNumber(wNumber);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with W Number: " + wNumber + " not found");
            }
            userRepository.deleteUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("User " + wNumber + " successfully deleted");
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }
}
