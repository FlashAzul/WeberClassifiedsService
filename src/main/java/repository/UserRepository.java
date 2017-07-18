package repository;

import model.User;

import java.util.List;

/**
 * Created by samuel on 5/30/17.
 * Interface representing a data-store for users.
 */
public interface UserRepository {

    User create (User user);

    User update (User user);

    List<User> read ();

    User read (Long id);

    void delete (Long id);

    User getByUserName (String userName);

}
