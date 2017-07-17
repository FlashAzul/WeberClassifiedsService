package utility;

import model.Listing;
import representation.ListingRepresentation;
import representation.ListingSummaryRepresentation;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inca1 on 7/16/2017.
 */
public class ListingUtils {
    public static ListingRepresentation buildListingRepresentation(Listing listing){
        ListingRepresentation listingRepresentation = new ListingRepresentation();
        listingRepresentation.setListingId(listing.getListingId());
        listingRepresentation.setUserId(listing.getUserId());
        listingRepresentation.setListingText(listing.getListingText());
        return listingRepresentation;
    }

    public static List<Listing> buildListingRepresentation(List<Listing> listingModels){
        List<Listing> returnListings = new ArrayList<>();

        for (Listing listing : listingModels) {
            returnListings.add(listing);
        }
        return returnListings;
    }

    public static ListingSummaryRepresentation buildListingSummaryRepresentation(Listing listing){
        ListingSummaryRepresentation listingSummaryRepresentation = new ListingSummaryRepresentation();
        listingSummaryRepresentation.setListingId(listing.getListingId());
        listingSummaryRepresentation.setUserId(listing.getUserId());
        listingSummaryRepresentation.setListingTextSummary(listing.getListingText());
        return listingSummaryRepresentation;

    }

    public static List<ListingSummaryRepresentation> buildListingSummaryRepresentation(List<Listing> listingModels){
        List<ListingSummaryRepresentation> returnListings = new ArrayList<>();
        for (Listing listing : listingModels) {
            ListingSummaryRepresentation lr = buildListingSummaryRepresentation(listing);
            returnListings.add(lr);
        }
        return returnListings;
    }
}
