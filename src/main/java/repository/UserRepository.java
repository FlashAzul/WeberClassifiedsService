package repository;

import representation.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by samuel on 5/30/17.
 */
public class UserRepository {
    private static Map<String, User> userMap = new ConcurrentHashMap<>();

    static {
        User userSam = new User("w12345", "Samuel", "Doria");
        userMap.put(userSam.getwNumber(), userSam);
        User userDarthVadar = new User("w66", "Darth", "Vadar");
        userMap.put(userDarthVadar.getwNumber(), userDarthVadar);
    }

    private UserRepository() {}

    public static void add(User user) {
        userMap.put(user.getwNumber(), user);
    }

    public static User getByWNumber(String wNumber) {
        return userMap.get(wNumber);
    }

    public static List<User> getAll() {
        List<User> users = new ArrayList<>(userMap.values());
        return users;
    }
}
