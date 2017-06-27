package resource;

import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.UserRepository;

import java.util.List;

/**
 * Created by samuel on 5/30/17.
 */
@RestController
@RequestMapping(ResourceConstants.BASE_DOMAIN + ResourceConstants.USER_RESOURCE)
public class UserResource {

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity getUsers() {
        List<User> users = UserRepository.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @RequestMapping(path = ResourceConstants.PATH_VARIABLE_WNUMBER, method=RequestMethod.GET)
    public ResponseEntity getUser(@PathVariable("wnumber") String wNumber) {
        User user = UserRepository.getByWNumber(wNumber);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with W Number: " + wNumber + " not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity createNewUser(@RequestBody User user) {
        UserRepository.add(user);
        return ResponseEntity.status(HttpStatus.OK).body(UserRepository.getByWNumber(user.getwNumber()));
    }

    @RequestMapping(path = ResourceConstants.PATH_VARIABLE_WNUMBER, method=RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("wnumber") String wNumber){
        User user = UserRepository.getByWNumber(wNumber);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with W Number: " + wNumber + " not found");
        }
        UserRepository.deleteUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("User " + wNumber + " successfully deleted");
    }


}
