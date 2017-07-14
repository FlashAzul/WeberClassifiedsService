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
import utility.AuthenticationUtils;
import utility.UserUtils;

import static resource.ResourceConstants.AUTHENTICATION_RESOURCE;
import static resource.ResourceConstants.AUTHORIZATION_REQUEST_HEADER;

/**
 * Created by samuel on 7/6/17.
 * Class Representing the /authentication endpoint of the service.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(ResourceConstants.WEBER_CLASSIFIEDS + AUTHENTICATION_RESOURCE)
public class AuthenticationResource {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity authenticateUser (@RequestHeader(AUTHORIZATION_REQUEST_HEADER) String authHeader) {
        String userName = AuthenticationUtils.getUserNameFromHeader(authHeader);
        String password = AuthenticationUtils.getUserPasswordFromHeader(authHeader);

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("UserName and Password required...");
        }

        User user = userRepository.getByUserName(userName);
        if (user != null) {
            if (user.getHashedPassword().equals(AuthenticationUtils.hashPassword(password, user.getSalt()))) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(AuthenticationUtils.buildJwtToken(UserUtils
                        .buildUserPresentation(user)));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your username and password were rejected...");
    }
}
