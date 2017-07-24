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
public class AuthorizationUtility {

    public static Boolean validateAuthorization (String token, String tokenTypeRequired, Long userBeingAccessedId,
            UserRepository userRepository) {
        try {
            Jws<Claims> claimsJws = getClaimsFromToken(token);
            String userName = getClaimFromClaims(claimsJws, USER_NAME_CLAIM);
            String authType = getClaimFromClaims(claimsJws, TOKEN_TYPE);
            User tokenUser = userRepository.getByUserName(userName);
            if (tokenUser != null && authType.equals(tokenTypeRequired)) {
                if (userBeingAccessedId != null) {
                    User userBeingAccessed = userRepository.read(userBeingAccessedId);
                    return validateUserAuthorization(tokenUser, userBeingAccessed);
                }
                return true;
            }
            return false;
        }
        catch (Exception e) {
            return false;
        }

    }

    public static User getUserFromToken (String token, UserRepository userRepository) {
        Jws<Claims> claimsJws = getClaimsFromToken(token);
        String userName = getClaimFromClaims(claimsJws, USER_NAME_CLAIM);
        return userRepository.getByUserName(userName);
    }

    private static Boolean validateUserAuthorization (User tokenUser, User userBeingAccessed) {
        return tokenUser.getId().equals(userBeingAccessed.getId()) || tokenUser.getAccessLevel().equals
                (ApplicationConstants.AccessLevel.ADMIN);
    }

    public static AuthenticationRepresentation refreshAuthentication (String token, UserRepository userRepository) {
        Jws<Claims> claimsJws = getClaimsFromToken(token);
        String userName = getClaimFromClaims(claimsJws, USER_NAME_CLAIM);
        User user = userRepository.getByUserName(userName);
        return AuthenticationUtility.buildAuthenticationRepresentation(UserUtility.buildUserRepresentation(user));

    }

    private static Jws<Claims> getClaimsFromToken (String token) {
        return Jwts.parser().setSigningKey(TOKEN_SIGNATURE_KEY).parseClaimsJws(token);
    }

    private static String getClaimFromClaims (Jws<Claims> claimsJws, String claimName) {
        return claimsJws.getBody().get(claimName).toString();
    }
}
