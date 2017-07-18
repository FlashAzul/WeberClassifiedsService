package resource;

import model.Listing;
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
import repository.ListingRepository;
import repository.UserRepository;
import representation.ListingRepresentation;
import utility.AuthorizationUtils;
import utility.ListingUtils;

import static application.ApplicationConstants.AUTH_TOKEN_HEADER;
import static application.ApplicationConstants.AccessLevel;
import static application.ApplicationConstants.ID;
import static application.ApplicationConstants.LISTING_RESOURCE;
import static application.ApplicationConstants.TOKEN_TYPE_AUTH;

/**
 * Created by Bryan Fritz on 7/15/2017.
 */

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(LISTING_RESOURCE)
public class ListingResource {

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getListing (@PathVariable(ID) Long id, @RequestHeader(AUTH_TOKEN_HEADER) String token) {

        if (AuthorizationUtils.validateUserAuthorization(token, AccessLevel.STANDARD, TOKEN_TYPE_AUTH,
                userRepository)) {
            Listing returnListing = listingRepository.read(id);
            if (returnListing != null) {
                ListingRepresentation listingRepresentation = ListingUtils.buildListingRepresentation(returnListing);
                return ResponseEntity.status(HttpStatus.OK).body(listingRepresentation);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested listing with id: '" + id + "' " + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "" + "does" + " " + "not exist.");
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }

    /*Validate the token*/
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateListing (@RequestBody ListingRepresentation listing, @PathVariable(ID) Long id,
            @RequestHeader(AUTH_TOKEN_HEADER) String token) {

        if (AuthorizationUtils.validateUserAuthorization(token, AccessLevel.STANDARD, TOKEN_TYPE_AUTH,
                userRepository)) {
            Listing returnListing = listingRepository.read(id);
            if (returnListing != null) {
                returnListing.setMessage(listing.getMessage());
                ListingRepresentation listingRepresentation = ListingUtils.buildListingRepresentation(returnListing);
                return ResponseEntity.status(HttpStatus.OK).body(listingRepresentation);
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This listing does not exist");
            }

        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }

    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteListing (@PathVariable(ID) Long id, @RequestHeader(AUTH_TOKEN_HEADER) String token) {

        if (AuthorizationUtils.validateUserAuthorization(token, AccessLevel.STANDARD, TOKEN_TYPE_AUTH,
                userRepository)) {
            Listing returnListing = listingRepository.read(id);
            if (returnListing != null) {
                listingRepository.delete(returnListing.getId());
                return ResponseEntity.status(HttpStatus.OK).body("Listing " + id + " successfully deleted");
            }
            else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Listing" + id + " does not exist");
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }

}
