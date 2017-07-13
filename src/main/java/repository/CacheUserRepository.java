package repository;

import model.Address;
import model.User;
import org.springframework.stereotype.Repository;
import utility.AuthenticationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by samuel on 6/27/17.
 * DataStore from which users can be saved and retrieved.
 */
@Repository("userRepository")
public class CacheUserRepository implements UserRepository {

    private static Map<Long, User> userCache = new ConcurrentHashMap<>();

    static {
        Address adr1 = new Address("20 s state", "Slc", "Ut", "84101");
        String password = AuthenticationUtils.hashPassword("password", "23423423525");
        User adminDefault = new User("admin", "w12345", "test@mail.com", "Darth", "Vadar", adr1, password,
                "23423423525", AccessLevel.ADMIN);
        adminDefault.setId(1L);
        userCache.put(adminDefault.getId(), adminDefault);
    }

    public void create (User user) {
        Long newUserId = (long) (userCache.size() + 1);
        user.setId(newUserId);
        userCache.put(newUserId, user);
    }

    public void delete (User user) {
        userCache.remove(user.getId());
    }

    public void update (User user) {
        userCache.remove(user.getId());
        userCache.put(user.getId(), user);
    }

    public User getById (Long id) {
        if (id != null) {
            return userCache.get(id);
        }
        return null;
    }

    public User getByUserName (String userName) {
        for (User user : userCache.values()) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAll () {
        return new ArrayList<>(userCache.values());
    }

    public enum AccessLevel {
        ADMIN, STANDARD
    }
}
