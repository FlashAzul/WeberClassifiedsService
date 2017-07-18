package utility;

import application.ApplicationConstants;
import model.Listing;
import repository.ListingRepository;
import repository.UserRepository;
import representation.ListingRepresentation;
import representation.ListingSummaryRepresentation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inca1 on 7/16/2017.
 */
public class ListingUtils {

    public static Listing buildListing (ListingRepresentation listingRepresentation, ListingRepository listingRepository, UserRepository userRepository) {
        Listing listing = listingRepository.read(listingRepresentation.getId());

        if (listing == null) {
            listing = new Listing();
        }
        else {
            listing.setId(listingRepresentation.getId());
        }

        listing.setAttachmentIds(listingRepresentation.getAttachmentIds());
        listing.setMessage(listingRepresentation.getMessage());
        listing.setPrice(listingRepresentation.getPrice());
        listing.setTitle(listingRepresentation.getTitle());
        listing.setUser(UserUtils.buildUserModel(userRepository, listingRepresentation.getUser()));
        return listing;

    }

    public static ListingRepresentation buildListingRepresentation (Listing listing) {
        ListingRepresentation listingRepresentation = new ListingRepresentation();
        listingRepresentation.setId(listing.getId());
        listingRepresentation.setUser(UserUtils.buildUserPresentation(listing.getUser()));
        listingRepresentation.setMessage(listing.getMessage());
        listingRepresentation.setTitle(listing.getTitle());
        listingRepresentation.setPrice(listing.getPrice());
        listingRepresentation.setAttachmentIds(listing.getAttachmentIds());
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
        listingSummaryRepresentation.setUser(UserUtils.buildUserPresentation(listing.getUser()));
        listingSummaryRepresentation.setPrice(listing.getPrice());
        listingSummaryRepresentation.setTitle(listing.getTitle());
        listingSummaryRepresentation.setMessageSummary(listing.getMessage().substring(0, ApplicationConstants
                .LISTING_MESSAGE_SUMMARY_DEFAULT_LENGTH));
        if (listing.getAttachmentIds() != null && listing.getAttachmentIds().size() > 0) {
            listingSummaryRepresentation.setAttachmentId(listing.getAttachmentIds().get(0));
        }
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
