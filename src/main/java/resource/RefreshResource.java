package resource;

import annotation.AuthorizationRequired;
import application.ApplicationConstants;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repository.UserRepository;
import utility.AuthenticationUtility;

import java.io.PrintWriter;
import java.io.StringWriter;

import static application.ApplicationConstants.REFRESH_RESOURCE;
import static application.ApplicationConstants.TOKEN_USER_ATTRIBUTE;

/**
 * Created by samuel on 7/13/17.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(REFRESH_RESOURCE)
public class RefreshResource {

    @Autowired
    UserRepository userRepository;

    @AuthorizationRequired(tokenTypeRequired = ApplicationConstants.TOKEN_TYPE_REFRESH)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity refreshAuthentication (@RequestAttribute(TOKEN_USER_ATTRIBUTE) User tokenUser) {

        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(AuthenticationUtility.refreshAuthentication
                    (tokenUser));
        }
        catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sw.toString());
        }
    }

}
