package resource;

import application.ApplicationConstants;
import model.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import java.util.ArrayList;
import java.util.List;

import static application.ApplicationConstants.AUTH_TOKEN_HEADER;
import static application.ApplicationConstants.LISTINGS_RESOURCE;
import static application.ApplicationConstants.TOKEN_TYPE_AUTH;
//test

/**
 * Created by samuel on 7/15/17.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(LISTINGS_RESOURCE)
public class ListingsResource {

    @Autowired
    ListingRepository listingRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getListingsSummary (@RequestHeader(AUTH_TOKEN_HEADER) String token) {
        if (AuthorizationUtils.validateUserAuthorization(token, ApplicationConstants.AccessLevel.STANDARD,
                TOKEN_TYPE_AUTH, userRepository)) {
            List<Listing> listings = listingRepository.read();
            if (listings == null) {
                listings = new ArrayList<>();
            }
            return ResponseEntity.status(HttpStatus.OK).body(ListingUtils.buildListingSummaryRepresentation(listings));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createListing (@RequestBody ListingRepresentation postedListingRepresentation,
            @RequestHeader(AUTH_TOKEN_HEADER) String token) {

        if (AuthorizationUtils.validateUserAuthorization(token, ApplicationConstants.AccessLevel.STANDARD,
                TOKEN_TYPE_AUTH, userRepository)) {
            Listing newListing = ListingUtils.buildListing(postedListingRepresentation, listingRepository,
                    userRepository);
            listingRepository.create(newListing);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(ListingUtils.buildListingRepresentation
                    (listingRepository.read(newListing.getId())));
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Unauthorized To Perform Requested Action");
        }

    }

}
