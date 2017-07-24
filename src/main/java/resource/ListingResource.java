package resource;

import annotation.AuthorizationRequired;
import application.ApplicationConstants;
import model.Listing;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repository.ListingRepository;
import repository.UserRepository;
import representation.ListingRepresentation;
import utility.ListingUtility;

import java.io.PrintWriter;
import java.io.StringWriter;

import static application.ApplicationConstants.ID;
import static application.ApplicationConstants.LISTING_RESOURCE;
import static application.ApplicationConstants.TOKEN_USER_ATTRIBUTE;

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

    @AuthorizationRequired
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getListing (@PathVariable(ID) Long id) {

        try {
            Listing returnListing = listingRepository.read(id);
            if (returnListing == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The requested listing with id '" + id + "' "
                        + "does not exist.");
            }
            ListingRepresentation listingRepresentation = ListingUtility.buildListingRepresentation(returnListing);
            return ResponseEntity.status(HttpStatus.OK).body(listingRepresentation);
        }
        catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sw.toString());
        }
    }

    @AuthorizationRequired
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateListing (@RequestBody ListingRepresentation listingRepresentation, @RequestAttribute
            (TOKEN_USER_ATTRIBUTE) User tokenUser) {

        try {
            Listing listing = listingRepository.read(listingRepresentation.getId());
            if (listing == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This listing does not exist");
            }
            else if (!tokenUser.equals(listing.getUser()) || !tokenUser.getAccessLevel().equals(ApplicationConstants
                    .AccessLevel.ADMIN)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not authorized to perform " +
                        "requested action");
            }
            Listing updatedListing = ListingUtility.buildListing(listingRepresentation, listingRepository,
                    userRepository);
            listingRepository.update(updatedListing);
            return ResponseEntity.status(HttpStatus.OK).body(ListingUtility.buildListingRepresentation
                    (listingRepository.read(updatedListing.getId())));

        }
        catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sw.toString());
        }
    }

    @AuthorizationRequired
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteListing (@PathVariable(ID) Long id, @RequestAttribute(TOKEN_USER_ATTRIBUTE) User
            tokenUser) {

        try {
            Listing listingToDelete = listingRepository.read(id);
            if (listingToDelete == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Listing" + id + " does not exist");
            }
            else if (!tokenUser.equals(listingToDelete.getUser()) || !tokenUser.getAccessLevel().equals
                    (ApplicationConstants.AccessLevel.ADMIN)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not authorized to perform " +
                        "requested" + " " + "action");
            }
            listingRepository.delete(listingToDelete.getId());
            return ResponseEntity.status(HttpStatus.OK).body("Listing " + id + " successfully deleted");
        }
        catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sw.toString());
        }
    }

}
