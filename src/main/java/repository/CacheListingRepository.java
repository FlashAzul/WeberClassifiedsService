package repository;

import application.ApplicationConstants;
import model.Address;
import model.Listing;
import model.User;
import org.springframework.stereotype.Repository;
import utility.AuthenticationUtility;

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
        String password = AuthenticationUtility.hashPassword("password", "23423423525");
        User adminDefault = new User("admin", "w12345", "801-435-5555", "test@mail.com", "Darth", "Vadar", adr1,
                password, "23423423525", ApplicationConstants.AccessLevel.ADMIN);
        adminDefault.setId(nextAvailableId);
        Listing listing = new Listing(nextAvailableId, "This is my first listing.", "I'm really happy I have the " + "chance to list something on weber classifieds. Omg it's so cool. I'm so excited.", adminDefault, "dummy type", "dummy category", "100");
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
        try {
            return listingCache.get(id);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete (Long id) {
        try {
            listingCache.remove(id);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Listing> byPrice(String sprice, List<Listing> list) {
        List<Listing> temp = new ArrayList<>();
        Double price;
        try{
            price = Double.parseDouble(sprice);
        }catch (Exception e){
            price = 0.00;
        }
        for(Listing l : list){
            if(Double.parseDouble(l.getPrice()) < price){
                temp.add(l);
            }
        }
        return temp;
    }

    @Override
    public List<Listing> byType(String type, List<Listing> list) {
        List<Listing> temp = new ArrayList<>();
        for(Listing l : list){
            if(l.getType().equals(type)){
                temp.add(l);
            }
        }

        return temp;
    }

    @Override
    public List<Listing> byCategory(String category, List<Listing> list) {
        List<Listing> temp = new ArrayList<>();

        for(Listing l : list){
            if(l.getCategory().equals(category)){
                temp.add(l);
            }
        }

        return temp;
    }

    public List<Listing> byKeyword(String keyWord, List<Listing> list){
        List<Listing> temp = new ArrayList<>();
        CharSequence keyWords = keyWord;

        for(Listing l : list){
            if(l.getMessage().contains(keyWords)){
                temp.add(l);
            }
        }

        return temp;
    }

    @Override
    public Listing update (Listing listing) {
        try {
            listingCache.remove(listing.getId());
            listingCache.put(listing.getId(), listing);
            return listingCache.get(listing.getId());
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
