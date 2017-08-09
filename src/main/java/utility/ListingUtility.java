package utility;

import application.ApplicationConstants;
import model.Listing;
import repository.ListingRepository;
import repository.UserRepository;
import representation.ListingRepresentation;
import representation.ListingSummaryRepresentation;
import representation.UserRepresentation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inca1 on 7/16/2017.
 */
public class ListingUtility {

    public static Listing buildListing (ListingRepresentation listingRepresentation, ListingRepository
            listingRepository, UserRepository userRepository) {

        Listing listing = listingRepository.read(listingRepresentation.getId());
        if (listing == null) {
            listing = new Listing();
        }
        else {
            listing.setId(listingRepresentation.getId());
        }

        if (listingRepresentation.getMessage() != null) {
            listing.setMessage(listingRepresentation.getMessage());
        }
        if (listingRepresentation.getPrice() != null) {
            listing.setPrice(listingRepresentation.getPrice());
        }
        if (listingRepresentation.getTitle() != null) {
            listing.setTitle(listingRepresentation.getTitle());
        }
        if (listingRepresentation.getCategory() != null) {
            listing.setCategory(listingRepresentation.getCategory());
        }
        if (listingRepresentation.getType() != null) {
            listing.setType(listingRepresentation.getType());
        }
        if (listingRepresentation.getUser().getId() != null) {
            listing.setUser(userRepository.read(listingRepresentation.getUser().getId()));
        }
        return listing;
    }

    public static ListingRepresentation buildListingRepresentation (Listing listing) {
        ListingRepresentation listingRepresentation = new ListingRepresentation();
        listingRepresentation.setId(listing.getId());
        UserRepresentation userRepresentation = UserUtility.buildUserRepresentation(listing.getUser());
        userRepresentation.setPassword("");
        userRepresentation.setAccessLevel(null);
        listingRepresentation.setUser(userRepresentation);
        listingRepresentation.setMessage(listing.getMessage());
        listingRepresentation.setTitle(listing.getTitle());
        listingRepresentation.setPrice(listing.getPrice());
        listingRepresentation.setCategory(listing.getCategory());
        listingRepresentation.setType(listing.getType());
        //listingRepresentation.setListingCreationDate(listing.getListingCreationDate());
        listingRepresentation.setListingCreationDateString(listing.getListingCreationDateString());
        return listingRepresentation;
    }

    public static List<ListingRepresentation> buildListingRepresentation (List<Listing> listings) {
        List<ListingRepresentation> returnListings = new ArrayList<>();
        for (Listing listing : listings) {
            returnListings.add(buildListingRepresentation(listing));
        }
        return returnListings;
    }

    public static ListingSummaryRepresentation buildListingSummaryRepresentation (Listing listing) {
        ListingSummaryRepresentation listingSummaryRepresentation = new ListingSummaryRepresentation();
        listingSummaryRepresentation.setId(listing.getId());
        UserRepresentation userRepresentation = UserUtility.buildUserRepresentation(listing.getUser());
        userRepresentation.setPassword("");
        userRepresentation.setAccessLevel(null);
        listingSummaryRepresentation.setUser(userRepresentation);
        listingSummaryRepresentation.setPrice(listing.getPrice());
        listingSummaryRepresentation.setTitle(listing.getTitle());
        Integer summaryMessageLength = listing.getMessage().length() > ApplicationConstants
                .LISTING_MESSAGE_SUMMARY_DEFAULT_LENGTH ? listing.getMessage().length() - ApplicationConstants
                .LISTING_MESSAGE_SUMMARY_DEFAULT_LENGTH : listing.getMessage().length();
        listingSummaryRepresentation.setMessageSummary(listing.getMessage().substring(0, summaryMessageLength));
        listingSummaryRepresentation.setCategory(listing.getCategory());
        listingSummaryRepresentation.setType(listing.getType());
        //listingSummaryRepresentation.setListingCreationDate(listing.getListingCreationDate());
        listingSummaryRepresentation.setListingCreationDateString(listing.getListingCreationDateString());
        return listingSummaryRepresentation;
    }

    public static List<ListingSummaryRepresentation> buildListingSummaryRepresentation (List<Listing> listings) {
        List<ListingSummaryRepresentation> returnListings = new ArrayList<>();
        for (Listing listing : listings) {
            ListingSummaryRepresentation listingSummaryRepresentation = buildListingSummaryRepresentation(listing);
            returnListings.add(listingSummaryRepresentation);
        }
        return returnListings;
    }
}
