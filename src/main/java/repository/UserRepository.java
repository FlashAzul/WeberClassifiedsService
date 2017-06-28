package repository;

import model.User;

import java.util.List;

/**
 * Created by samuel on 5/30/17.
 */
public interface UserRepository {

    void addUser(User user);

    void deleteUser(User user);

    User getByWNumber(String wNumber);

    List<User> getAll();
}
