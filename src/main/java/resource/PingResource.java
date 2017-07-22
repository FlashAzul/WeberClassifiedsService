package resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static application.ApplicationConstants.PING_RESOURCE;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(PING_RESOURCE)
public class PingResource {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity pingService () {
        return ResponseEntity.status(HttpStatus.OK).body("Yup, the server is up...");
    }

}
