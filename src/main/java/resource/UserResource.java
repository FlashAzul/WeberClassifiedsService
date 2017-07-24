package resource;

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
import utility.AuthorizationUtility;
import utility.UserUtility;

import java.io.PrintWriter;
import java.io.StringWriter;

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

        try {

            if (AuthorizationUtility.validateAuthorization(token, TOKEN_TYPE_AUTH, userId, userRepository)) {
                User user = userRepository.read(userId);
                if (user != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(UserUtility.buildUserRepresentation(user));
                }
                else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with Id: " + userId + " not " +
                            "found");
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested " +
                        "Action");
            }

        }
        catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sw.toString());
        }

    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateUser (@RequestBody UserRepresentation user, @RequestHeader(AUTH_TOKEN_HEADER) String
            token) {

        try {

            if (AuthorizationUtility.validateAuthorization(token, TOKEN_TYPE_AUTH, user.getId(), userRepository)) {

                User userToUpdate = UserUtility.buildUserModel(userRepository, user);
                if (userToUpdate.getId() != null) {
                    if (!user.getUserName().equals(userToUpdate.getUserName()) && userRepository.getByUserName(user
                            .getUserName()) != null) {
                        return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with username '" + user
                                .getUserName() + "' already exists.");
                    }
                    userRepository.update(userToUpdate);
                    return ResponseEntity.status(HttpStatus.OK).body(UserUtility.buildUserRepresentation(userRepository.read(userToUpdate.getId())));
                }
                else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with Id: " + user.getId() + " not "
                            + "found");
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested " +
                        "Action");
            }

        }
        catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sw.toString());
        }

    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteUser (@PathVariable(ID) Long userId, @RequestHeader(AUTH_TOKEN_HEADER) String token) {

        try {

            if (AuthorizationUtility.validateAuthorization(token, TOKEN_TYPE_AUTH, userId, userRepository)) {
                User user = userRepository.read(userId);
                if (user != null) {
                    userRepository.delete(userId);
                    return ResponseEntity.status(HttpStatus.OK).body("User " + userId + " successfully deleted");
                }
                else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with Id: " + userId + " not " +
                            "found");
                }
            }
            else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested " +
                        "Action");
            }

        }
        catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sw.toString());
        }

    }
}
