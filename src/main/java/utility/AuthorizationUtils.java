package utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import model.User;
import repository.CacheUserRepository;
import repository.UserRepository;

import static utility.AuthConstants.TOKEN_SIGNATURE_KEY;
import static utility.AuthConstants.USER_NAME_CLAIM;

/**
 * Created by samuel on 7/6/17.
 * Group of helper methods used with Authorization
 */
public class AuthorizationUtils {

    public static Boolean validateUserAuthorization (String authToken, CacheUserRepository.AccessLevel
            accessLevelRequired, UserRepository userRepository) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(TOKEN_SIGNATURE_KEY).parseClaimsJws(authToken);
            String userName = claims.getBody().get(USER_NAME_CLAIM).toString();
            User user = userRepository.getByUserName(userName);
            return (user != null && user.getAccessLevel().equals(accessLevelRequired));
        }
        catch (Exception e) {
            return false;
        }
    }
}
