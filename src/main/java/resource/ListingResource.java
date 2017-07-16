package resource;

import repository.CacheListingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import representation.UserRepresentation;

import static application.ApplicationConstants.LISTING_RESOURCE;
import model.Listing;
/**
 * Created by Bryan Fritz on 7/15/2017.
 */

@RestController
@RequestMapping(LISTING_RESOURCE)
public class ListingResource {

    @RequestMapping(method = RequestMethod.GET)
    public Listing getAllListings (@PathVariable() int wNumber, @RequestHeader() String token) {

        return null;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity addListing (@RequestBody UserRepresentation user,
                                      @RequestHeader() String listingText) {

        CacheListingRepository.getInstance().addListing(new Listing(listingText, user.getwNumber(), CacheListingRepository.getInstance().getListingNumber()));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("New Listing Created");
    }
}
