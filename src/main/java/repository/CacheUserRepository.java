package repository;

import model.Address;
import model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by samuel on 6/27/17.
 */
@Repository("userRepository")
public class CacheUserRepository implements UserRepository {

    private static Map<String, User> userMap = new ConcurrentHashMap<>();

    static {
        Address adr1 = new Address("20 s state", "Slc", "Ut", "84101");
        User userSam = new User("w12345", "test@mail.com", "Samuel", "Doria", adr1, "12345");
        userMap.put(userSam.getwNumber(), userSam);
        Address adr2 = new Address("200 west Circle dr.", "Slc", "Ut", "84101");
        User userDarthVadar = new User("w66", "test2@mail.com", "Darth", "Vadar", adr2, "123456");
        userMap.put(userDarthVadar.getwNumber(), userDarthVadar);
    }

    public void addUser(User user) {
        userMap.put(user.getwNumber(), user);
    }

    public void deleteUser(User user){
        userMap.remove(user.getwNumber());
    }

    public User getByWNumber(String wNumber) {
        return userMap.get(wNumber);
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>(userMap.values());
        return users;
    }
}
