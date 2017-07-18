package resource;

import application.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repository.UserRepository;
import utility.AuthorizationUtils;

import static application.ApplicationConstants.AUTH_TOKEN_HEADER;
import static application.ApplicationConstants.REFRESH_RESOURCE;
import static application.ApplicationConstants.TOKEN_TYPE_REFRESH;

/**
 * Created by samuel on 7/13/17.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(REFRESH_RESOURCE)
public class RefreshResource {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity refreshAuthentication (@RequestHeader(AUTH_TOKEN_HEADER) String token) {
        if (AuthorizationUtils.validateUserAuthorization(token, ApplicationConstants.AccessLevel.STANDARD,
                TOKEN_TYPE_REFRESH, userRepository)) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(AuthorizationUtils.refreshAuthentication(token,
                    userRepository));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }
}
