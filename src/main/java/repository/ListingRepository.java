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

}
