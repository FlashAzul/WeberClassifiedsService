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


        Address adr2 = new Address("2045 s state", "Murray", "Ut", "84107");
        String password2 = AuthenticationUtility.hashPassword("password", "23423423525");
        User adminDefault2 = new User("admin2", "w12346", "801-435-6666", "test2@mail.com", "Luke", "Skywalker", adr2,
                password, "23423423525", ApplicationConstants.AccessLevel.ADMIN);
        adminDefault2.setId(nextAvailableId);
        Listing listing2 = new Listing(nextAvailableId, "This is my second listing.", "I'm really happy I have the second" + "chance to list something on weber classifieds. 1mg it's so cool. I'm so thrilled.", adminDefault2, "cool type", "cool category", "10");
        //long listing2Time = (8*24*1000*60*60);
        listing2.setListingCreationDate(System.currentTimeMillis() - (long)8*24*1000*60*60);
        nextAvailableId++;
        listingCache.put(listing2.getId(), listing2);


        Address adr3 = new Address("6720 s state", "boise", "Idaho", "83701");
        String password3 = AuthenticationUtility.hashPassword("password", "23423423525");
        User adminDefault3 = new User("admin", "w12347", "801-435-7777", "test3@mail.com", "Obi", "Wan-Kenobi", adr3,
                password, "23423423525", ApplicationConstants.AccessLevel.ADMIN);
        adminDefault3.setId(nextAvailableId);
        Listing listing3 = new Listing(nextAvailableId, "This is my third listing.", "I'm really happy I have the " + "chance to list something on weber classifieds. 3mg it's so cool. I'm so bored.", adminDefault3, "car", "car category", "50.25");
        listing3.setListingCreationDate(System.currentTimeMillis() - (long)31*24*1000*60*60);
        nextAvailableId++;
        listingCache.put(listing3.getId(), listing3);
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
    public List<Listing> byPrice(String i_minPrice,String i_maxPrice, List<Listing> list) {
        List<Listing> temp = new ArrayList<>();
        Double minPrice = 0.00;
        Double maxPrice = 0.00;
        try{
            if(!i_minPrice.isEmpty()){
                minPrice = Double.parseDouble(i_minPrice);
            }
            if(!i_maxPrice.isEmpty()){
                maxPrice = Double.parseDouble(i_maxPrice);
            }

        }catch (Exception e){
            minPrice = 0.00;
            maxPrice = 0.00;
        }
        if(!i_minPrice.isEmpty() && !i_maxPrice.isEmpty()){
            for(Listing l : list){
                Double thisListing = Double.parseDouble(l.getPrice());
                if(thisListing > minPrice && thisListing < maxPrice){
                    temp.add(l);
                }
            }
        }else if(!i_minPrice.isEmpty()){
            for(Listing l : list){
                Double thisListing = Double.parseDouble(l.getPrice());
                if(thisListing > minPrice){
                    temp.add(l);
                }
            }
        }else{
            for(Listing l : list){
                Double thisListing = Double.parseDouble(l.getPrice());
                if(thisListing < maxPrice){
                    temp.add(l);
                }
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

        for(Listing l : list){
            if(l.getMessage().toLowerCase().equalsIgnoreCase(keyWord.toLowerCase()) || l.getTitle().toLowerCase().contains(keyWord.toLowerCase())){
                temp.add(l);
            }
        }

        return temp;
    }

    @Override
    public List<Listing> byCityOrState(String i_city, String i_state, List<Listing> list) {
        List<Listing> temp = new ArrayList<>();

        if (!i_city.isEmpty() && !i_state.isEmpty()){
            for(Listing l : list){
                User thisListingUser = l.getUser();

                if(thisListingUser.getAddress().getCity().equalsIgnoreCase(i_city) && thisListingUser.getAddress().getState().equalsIgnoreCase(i_state)){
                    temp.add(l);
                }
            }
        }else if (!i_city.isEmpty()){
            for(Listing l : list){
                User thisListingUser = l.getUser();
                if(thisListingUser.getAddress().getCity().equalsIgnoreCase(i_city)){
                    temp.add(l);
                }
            }
        }else{
            for(Listing l : list){
                User thisListingUser = l.getUser();
                if(thisListingUser.getAddress().getState().equalsIgnoreCase(i_state)){
                    temp.add(l);
                }
            }
        }

        return temp;
    }

    @Override
    public List<Listing> byDate(String date, List<Listing> list) {
        List<Listing> temp = new ArrayList<>();
        long currentTime = System.currentTimeMillis();

        for(Listing l : list){
            long compareListingTime = l.getListingCreationDate().getTime();
            if(date.equals("Last Hour")){
                compareListingTime = (1000*60*60);
            }else if(date.equals("Last 24 Hours")){
                compareListingTime = (24*1000*60*60);
            }else if(date.equals("Last 7 Days")){
                compareListingTime = (7*24*1000*60*60);
            }else if(date.equals("Last 30 days")){
                compareListingTime = (30*24*1000*60*60);
            }
            if((currentTime - compareListingTime) > l.getListingCreationDate().getTime()){
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
