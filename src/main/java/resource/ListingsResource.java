package resource;

import annotation.AuthorizationRequired;
import application.ApplicationConstants;
import model.Listing;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import java.util.ArrayList;
import java.util.List;

import static application.ApplicationConstants.LISTINGS_RESOURCE;
import static application.ApplicationConstants.TOKEN_USER_ATTRIBUTE;
//test

/**
 * Created by samuel on 7/15/17.
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(LISTINGS_RESOURCE)
public class ListingsResource {

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    UserRepository userRepository;

    @AuthorizationRequired
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getListingsSummary () {
        try {
            List<Listing> listings = listingRepository.read();
            if (listings == null) {
                listings = new ArrayList<>();
            }
            return ResponseEntity.status(HttpStatus.OK).body(ListingUtility.buildListingSummaryRepresentation
                    (listings));
        }
        catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sw.toString());
        }
    }

    @AuthorizationRequired
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createListing (@RequestBody ListingRepresentation postedListingRepresentation,
            @RequestAttribute(TOKEN_USER_ATTRIBUTE) User tokenUser) {

        try {
            if (!tokenUser.getId().equals(postedListingRepresentation.getUser().getId()) && !tokenUser.getAccessLevel
                    ().equals(ApplicationConstants.AccessLevel.ADMIN)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested " +
                        "Action");
            }
            Listing newListing = ListingUtility.buildListing(postedListingRepresentation, listingRepository,
                    userRepository);
            listingRepository.create(newListing);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ListingUtility.buildListingRepresentation
                    (listingRepository.read(newListing.getId())));
        }
        catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sw.toString());
        }
    }

}
