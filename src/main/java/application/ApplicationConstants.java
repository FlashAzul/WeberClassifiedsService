package application;

/**
 * Created by samuel on 7/15/17.
 */
public class ApplicationConstants {

    // Generic
    private static final String OPEN_BRACE = "{";
    private static final String CLOSE_BRACE = "}";
    private static final String SLASH = "/";

    // Variable Names
    public static final String ID = "id";

    // Path Variables
    private static final String ID_PATH_VARIABLE = OPEN_BRACE + ID + CLOSE_BRACE;

    // Headers
    public static final String AUTH_TOKEN_HEADER = "AuthToken";
    public static final String AUTHORIZATION_REQUEST_HEADER = "Authorization";

    // Base Url For Service
    private static final String SERVICE_NAME = SLASH + "weberclassifieds";

    // Resources
    public static final String AUTHENTICATION_RESOURCE = SERVICE_NAME + SLASH + "authentication";
    public static final String REFRESH_RESOURCE = SERVICE_NAME + SLASH + "refresh";
    public static final String USERS_RESOURCE = SERVICE_NAME + SLASH + "users";
    public static final String USER_RESOURCE = USERS_RESOURCE + SLASH + ID_PATH_VARIABLE;
    public static final String LISTING_RESOURCE = SERVICE_NAME + SLASH + "listing";

    // Authentication
    public static final String TOKEN_SIGNATURE_KEY = "X317OlbkDHJd9fQadZgRXC7dZtsRIqCKB&y#0sRjqK6uuS1QXF";
    public static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA512";
    public static final String TOKEN_ISSUER = "Weber Classifieds Service";
    public static final String TOKEN_TYPE = "tokenType";
    public static final String TOKEN_TYPE_AUTH = "authorization";
    public static final String TOKEN_TYPE_REFRESH = "refresh";
    public static final String USER_NAME_CLAIM = "userName";
    public static final String ACCESS_LEVEL_CLAIM = "accessLevel";
    public static final int TOKEN_EXPIRATION_MINUTES = 30;
    static final int REFRESH_TOKEN_EXPIRATION_MINUTES = TOKEN_EXPIRATION_MINUTES + 10;

    /**
     * Created by samuel on 7/14/17.
     */
    public enum AccessLevel {
        ADMIN, STANDARD, DISABLED
    }
}
