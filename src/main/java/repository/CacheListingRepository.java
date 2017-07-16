package repository;

import model.Listing;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Inca1 on 7/15/2017.
 */
public class CacheListingRepository {

    private static Set<Listing> allListings;
    private static CacheListingRepository instance = null;
    private int listingNumber;

    protected CacheListingRepository() {
        // Exists only to defeat instantiation.
    }
    public static CacheListingRepository getInstance() {
        if(instance == null) {
            instance = new CacheListingRepository();
            instance.setListingNumber(0);
            allListings = new HashSet<>();
        }
        return instance;
    }
    public Set<Listing> getAllListings() {
        return allListings;
    }

    public void addListing(Listing listing) {
        allListings.add(listing);
        listingNumber++;
    }
    public int getListingNumber() {
        return listingNumber;
    }

    protected void setListingNumber(int listingNumber) {
        this.listingNumber = listingNumber;
    }
}
