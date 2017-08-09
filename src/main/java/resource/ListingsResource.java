package resource;

import annotation.AuthorizationRequired;
import application.ApplicationConstants;
import model.Listing;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity getListingsSummary (@RequestParam (value = "minPrice", defaultValue = "") String minPrice, @RequestParam (value = "maxPrice", defaultValue = "") String maxPrice,
                                              @RequestParam (value = "type", defaultValue = "") String type, @RequestParam (value = "category", defaultValue = "") String category,
                                              @RequestParam (value = "keyWord", defaultValue = "") String keyWord, @RequestParam (value = "state", defaultValue = "") String state,
                                              @RequestParam (value = "city", defaultValue = "") String city, @RequestParam (value = "searchTime", defaultValue = "") String searchTime) {
        try {
            List<Listing> listings = listingRepository.read();

            if (listings == null) {
                listings = new ArrayList<>();
            }
            if(!minPrice.isEmpty() | !maxPrice.isEmpty()){
                listings = listingRepository.byPrice(minPrice,maxPrice, listings);
            }
            if(!type.isEmpty()){
                listings = listingRepository.byType(type, listings);
            }
            if(!category.isEmpty()){
                listings = listingRepository.byCategory(category, listings);
            }
            if(!keyWord.isEmpty()){
                listings = listingRepository.byKeyword(keyWord, listings);
            }
            if(!state.isEmpty() || !city.isEmpty()){
                listings = listingRepository.byCityOrState(city,state,listings);
            }
            if(!searchTime.isEmpty()){
                if(!searchTime.equals("")){
                    listings = listingRepository.byDate(searchTime, listings);
                }
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
