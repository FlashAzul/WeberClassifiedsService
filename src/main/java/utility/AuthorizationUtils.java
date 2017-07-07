package utility;

import static resource.ResourceConstants.TOKEN;

/**
 * Created by samuel on 7/6/17.
 */
public class AuthorizationUtils {

    public static Boolean validateUserAuthorization(String authToken) {
        if (TOKEN.equals(authToken)) {
            return true;
        }
        else {
            return false;
        }
    }
}
