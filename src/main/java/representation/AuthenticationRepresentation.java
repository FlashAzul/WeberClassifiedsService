package representation;

/**
 * Created by samuel on 7/14/17.
 * Representation of the Authentication information returned to the user from the Authentication Resource
 */
public class AuthenticationRepresentation {
    private String authenticationToken;
    private String refreshToken;

    public AuthenticationRepresentation (String authenticationToken, String refreshToken) {
        this.authenticationToken = authenticationToken;
        this.refreshToken = refreshToken;
    }

    public AuthenticationRepresentation () {
    }

    public String getAuthenticationToken () {
        return authenticationToken;
    }

    public void setAuthenticationToken (String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    public String getRefreshToken () {
        return refreshToken;
    }

    public void setRefreshToken (String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
