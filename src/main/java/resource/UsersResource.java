package resource;

import application.ApplicationConstants;
import model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.UserRepository;
import representation.UserRepresentation;
import utility.AuthorizationUtility;
import utility.UserUtility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static application.ApplicationConstants.AUTH_TOKEN_HEADER;
import static application.ApplicationConstants.TOKEN_TYPE_AUTH;
import static application.ApplicationConstants.USERS_RESOURCE;
import static application.ApplicationConstants.USER_NAME;

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
    public ResponseEntity getUsers (@RequestHeader(AUTH_TOKEN_HEADER) String authToken, @RequestParam(value = USER_NAME,
            defaultValue = "") String userName) {

        try {

            List<User> users = new ArrayList<>();

            if (AuthorizationUtility.validateUserAuthorization(authToken, ApplicationConstants.AccessLevel.STANDARD,
                    TOKEN_TYPE_AUTH, userRepository)) {
                if (StringUtils.isNotEmpty(userName)) {
                    User user = userRepository.getByUserName(userName);
                    if (user != null) {
                        users.add(user);
                    }
                }
                else {
                    users.addAll(userRepository.read());
                }
                return ResponseEntity.status(HttpStatus.OK).body(UserUtility.buildUserRepresentation(users));
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createUser (@RequestBody UserRepresentation user, @RequestHeader(AUTH_TOKEN_HEADER) String
            token) {

        try {

            if (AuthorizationUtility.validateUserAuthorization(token, ApplicationConstants.AccessLevel.STANDARD,
                    TOKEN_TYPE_AUTH, userRepository)) {
                if (userRepository.getByUserName(user.getUserName()) != null) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("A user with username '" + user
                            .getUserName() + "' already exists.");
                }
                User newUser = UserUtility.buildUserModel(userRepository, user);
                userRepository.create(newUser);
                return ResponseEntity.status(HttpStatus.OK).body(UserUtility.buildUserRepresentation(userRepository
                        .getByUserName(user.getUserName())));
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
