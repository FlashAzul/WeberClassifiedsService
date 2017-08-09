package resource;

import annotation.AuthorizationRequired;
import application.ApplicationConstants;
import model.Listing;
import model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repository.ListingRepository;
import repository.UserRepository;
import utility.ListingUtility;
import utility.UserUtility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static application.ApplicationConstants.ID;
import static application.ApplicationConstants.TOKEN_USER_ATTRIBUTE;
import static application.ApplicationConstants.USER_LISTINGS_RESOURCE;
import static application.ApplicationConstants.USER_NAME;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(USER_LISTINGS_RESOURCE)
public class UserListingResource {

    @Autowired
    ListingRepository listingRepository;


    @AuthorizationRequired
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getUsers (@PathVariable(ID) Long userId)  {
        try {
            List<Listing> listings = new ArrayList<>();
            for (Listing listing : listingRepository.read()) {
                if (listing.getUser().getId().equals(userId)) {
                    listings.add(listing);
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(ListingUtility.buildListingSummaryRepresentation(listings));
        }
        catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(sw.toString());
        }

    }
}
