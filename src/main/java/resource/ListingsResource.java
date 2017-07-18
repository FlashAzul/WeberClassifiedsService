package resource;

import model.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import repository.ListingRepository;
import representation.ListingRepresentation;
import utility.ListingUtils;

import java.util.List;

import static application.ApplicationConstants.LISTINGS_RESOURCE;
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

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getListingsSummary () {
        List<Listing> listings = listingRepository.read();
        return ResponseEntity.status(HttpStatus.OK).body(ListingUtils.buildListingSummaryRepresentation(listings));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createListing (@RequestHeader() ListingRepresentation postedListingRepresentation) {
        Listing newListing = ListingUtils.buildListing(postedListingRepresentation, listingRepository);
        listingRepository.create(newListing);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(listingRepository.read(newListing.getId()));
    }

}
