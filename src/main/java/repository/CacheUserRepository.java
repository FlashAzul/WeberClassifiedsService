package repository;

import application.ApplicationConstants;
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
    private static Long nextAvailableId = 1L;

    static {
        Address adr1 = new Address("20 s state", "Slc", "Ut", "84101");
        String password = AuthenticationUtils.hashPassword("password", "23423423525");
        User adminDefault = new User("admin", "w12345", "test@mail.com", "Darth", "Vadar", adr1, password,
                "23423423525", ApplicationConstants.AccessLevel.ADMIN);
        adminDefault.setId(nextAvailableId);
        nextAvailableId++;
        userCache.put(adminDefault.getId(), adminDefault);
    }

    @Override
    public User create (User user) {
        user.setId(nextAvailableId);
        nextAvailableId++;
        userCache.put(user.getId(), user);
        return userCache.get(user.getId());
    }

    @Override
    public List<User> read () {
        return new ArrayList<>(userCache.values());
    }

    @Override
    public User read (Long id) {
        return userCache.get(id);
    }

    @Override
    public User update (User user) {
        userCache.remove(user.getId());
        userCache.put(user.getId(), user);
        return userCache.get(user.getId());
    }

    @Override
    public void delete (Long id) {
        userCache.remove(id);
    }

    @Override
    public User getByUserName (String userName) {
        for (User user : userCache.values()) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }

}
