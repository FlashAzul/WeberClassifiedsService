package repository;

import model.Listing;

import java.util.List;

/**
 * Created by Inca1 on 7/16/2017.
 */
public interface ListingRepository {

    Listing create (Listing listing);

    Listing update (Listing listing);

    Listing read (Long id);

    List<Listing> read ();

    void delete (Long id);

    List<Listing> byPrice(String price, List<Listing> list);

    List<Listing> byType(String type, List<Listing> list);

    List<Listing> byCategory(String category, List<Listing> list);

    List<Listing> byKeyword(String keyWord, List<Listing> list);





}
