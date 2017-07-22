package utility;

import application.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import model.User;
import repository.UserRepository;
import representation.AuthenticationRepresentation;

import static application.ApplicationConstants.TOKEN_SIGNATURE_KEY;
import static application.ApplicationConstants.TOKEN_TYPE;
import static application.ApplicationConstants.USER_NAME_CLAIM;

/**
 * Created by samuel on 7/6/17.
 * Group of helper methods used with Authorization
 */
public class AuthorizationUtils {

    public static Boolean validateUserAuthorization (String token, ApplicationConstants.AccessLevel
            minimumAccessLevelRequired, String tokenType, UserRepository userRepository) {
        try {
            Jws<Claims> claimsJws = getClaimsFromToken(token);
            String userName = getClaimFromClaims(claimsJws, USER_NAME_CLAIM);
            String authType = getClaimFromClaims(claimsJws, TOKEN_TYPE);
            User user = userRepository.getByUserName(userName);
            return (user != null && user.getAccessLevel().ordinal() <= minimumAccessLevelRequired.ordinal() &&
                    authType.equals(tokenType));
        }
        catch (Exception e) {
            return false;
        }
    }

    public static AuthenticationRepresentation refreshAuthentication (String token, UserRepository userRepository) {
        Jws<Claims> claimsJws = getClaimsFromToken(token);
        String userName = getClaimFromClaims(claimsJws, USER_NAME_CLAIM);
        User user = userRepository.getByUserName(userName);
        return AuthenticationUtils.buildAuthenticationRepresentation(UserUtils.buildUserRepresentation(user));

    }

    private static Jws<Claims> getClaimsFromToken (String token) {
        return Jwts.parser().setSigningKey(TOKEN_SIGNATURE_KEY).parseClaimsJws(token);
    }

    private static String getClaimFromClaims (Jws<Claims> claimsJws, String claimName) {
        return claimsJws.getBody().get(claimName).toString();
    }
}
