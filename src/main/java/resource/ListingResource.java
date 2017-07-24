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
import utility.AuthorizationUtility;
import utility.ListingUtility;

import java.io.PrintWriter;
import java.io.StringWriter;

import static application.ApplicationConstants.AUTH_TOKEN_HEADER;
import static application.ApplicationConstants.AccessLevel;
import static application.ApplicationConstants.ID;
import static application.ApplicationConstants.LISTING_RESOURCE;
import static application.ApplicationConstants.TOKEN_TYPE_AUTH;

/**
 * Created by Bryan Fritz on 7/15/2017.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(LISTING_RESOURCE)
public class ListingResource {

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getListing (@PathVariable(ID) Long id, @RequestHeader(AUTH_TOKEN_HEADER) String token) {

        try {

            if (AuthorizationUtility.validateUserAuthorization(token, AccessLevel.STANDARD, TOKEN_TYPE_AUTH,
                    userRepository)) {
                Listing returnListing = listingRepository.read(id);
                if (returnListing != null) {
                    ListingRepresentation listingRepresentation = ListingUtility.buildListingRepresentation
                            (returnListing);
                    return ResponseEntity.status(HttpStatus.OK).body(listingRepresentation);
                }
                else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested listing with id '" + id +
                            "' " + "does not exist.");
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
    public ResponseEntity updateListing (@RequestBody ListingRepresentation listingRepresentation, @RequestHeader
            (AUTH_TOKEN_HEADER) String token) {

        try {

            if (AuthorizationUtility.validateUserAuthorization(token, AccessLevel.STANDARD, TOKEN_TYPE_AUTH,
                    userRepository)) {
                Listing listing = ListingUtility.buildListing(listingRepresentation, listingRepository, userRepository);
                if (listing.getId() != null) {
                    listingRepository.update(listing);
                    return ResponseEntity.status(HttpStatus.OK).body(ListingUtility.buildListingRepresentation
                            (listingRepository.read(listing.getId())));
                }
                else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This listing does not exist");
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
    public ResponseEntity deleteListing (@PathVariable(ID) Long id, @RequestHeader(AUTH_TOKEN_HEADER) String token) {

        try {

            if (AuthorizationUtility.validateUserAuthorization(token, AccessLevel.STANDARD, TOKEN_TYPE_AUTH,
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
