package resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static resource.ResourceConstants.*;

/**
 * Created by samuel on 7/6/17.
 */
@RestController
@RequestMapping(SLASH + ResourceConstants.BASE_DOMAIN + SLASH + RESOURCE_AUTHENTICATION)
public class AuthenticationResource {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity authenticateUser(@RequestHeader("Authorization") String authHeader) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(TOKEN);
    }
}
