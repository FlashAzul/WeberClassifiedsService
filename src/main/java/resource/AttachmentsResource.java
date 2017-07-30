package resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static application.ApplicationConstants.AUTHENTICATION_RESOURCE;

/**
 * Created by samuel on 7/16/17.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(AUTHENTICATION_RESOURCE)
public class AttachmentsResource {

}
