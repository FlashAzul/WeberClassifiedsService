package repository;

import model.User;

import java.util.List;

/**
 * Created by samuel on 5/30/17.
 * Interface representing a data-store for users.
 */
public interface UserRepository {

    void create (User user);

    void delete (User user);

    void update (User user);

    User getById (Long id);

    User getByUserName (String userName);

    List<User> getAll ();
}
