package repository;

import model.Listing;
import java.util.List;


/**
 * Created by Inca1 on 7/16/2017.
 */
public interface ListingRepository {

    List<Listing> getAllListings();

    Listing getListing(int listingNumber);

    void addListing(Listing listing);

    int getListingNumber();

    void deleteListing(int listingId);
}
