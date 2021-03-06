package resource;

import model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repository.UserRepository;
import utility.AuthenticationUtility;
import utility.UserUtility;

import java.io.PrintWriter;
import java.io.StringWriter;

import static application.ApplicationConstants.AUTHENTICATION_RESOURCE;
import static application.ApplicationConstants.AUTHORIZATION_REQUEST_HEADER;

/**
 * Created by samuel on 7/6/17.
 * Class Representing the /authentication endpoint of the service.
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(AUTHENTICATION_RESOURCE)
public class AuthenticationResource {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity authenticateUser (@RequestHeader(AUTHORIZATION_REQUEST_HEADER) String authHeader) {

        try {
            String userName = AuthenticationUtility.getUserNameFromHeader(authHeader);
            String password = AuthenticationUtility.getUserPasswordFromHeader(authHeader);

            if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both a Username and Password are " +
                        "required...");
            }

            User user = userRepository.getByUserName(userName);
            if (user != null) {
                if (user.getHashedPassword().equals(AuthenticationUtility.hashPassword(password, user.getSalt()))) {
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(AuthenticationUtility
                            .buildAuthenticationRepresentation(UserUtility.buildUserRepresentation(user)));
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The username/password combination was " +
                    "rejected...");

        }
        catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sw.toString());
        }
    }
}
