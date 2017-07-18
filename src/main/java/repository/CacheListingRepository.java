package repository;

import application.ApplicationConstants;
import model.Address;
import model.Listing;
import model.User;
import org.springframework.stereotype.Repository;
import utility.AuthenticationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Inca1 on 7/15/2017.
 */
@Repository("listingRepository")
public class CacheListingRepository implements ListingRepository {

    private static Map<Long, Listing> listingCache = new ConcurrentHashMap<>();
    private static Long nextAvailableId = 1L;

    static {
        Address adr1 = new Address("20 s state", "Slc", "Ut", "84101");
        String password = AuthenticationUtils.hashPassword("password", "23423423525");
        User adminDefault = new User("admin", "w12345", "test@mail.com", "Darth", "Vadar", adr1, password,
                "23423423525", ApplicationConstants.AccessLevel.ADMIN);
        adminDefault.setId(nextAvailableId);
        Listing listing = new Listing(nextAvailableId, "This is my first listing.", "I'm really happy I have the " +
                "chance to list something on weber classifieds. Omg it's so cool. I'm so excited.", adminDefault);
        nextAvailableId++;
        listingCache.put(listing.getId(), listing);
    }

    @Override
    public Listing create (Listing listing) {
        listing.setId(nextAvailableId);
        nextAvailableId++;
        listingCache.put(listing.getId(), listing);
        return listingCache.get(listing.getId());
    }

    @Override
    public List<Listing> read () {
        return new ArrayList<>(listingCache.values());
    }

    @Override
    public Listing read (Long id) {
        return listingCache.get(id);
    }

    @Override
    public void delete (Long id) {
        listingCache.remove(id);
    }

    @Override
    public Listing update (Listing listing) {
        listingCache.remove(listing.getId());
        listingCache.put(listing.getId(), listing);
        return listingCache.get(listing.getId());
    }
}
