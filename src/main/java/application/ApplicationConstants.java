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
    public static final String USER_NAME = "username";

    // Path Variables
    private static final String ID_PATH_VARIABLE = OPEN_BRACE + ID + CLOSE_BRACE;

    // Headers
    public static final String AUTH_TOKEN_HEADER = "AuthToken";
    public static final String AUTHORIZATION_REQUEST_HEADER = "Authorization";
    public static final String USER_CREATION_TOKEN_HEADER = "UserCreateToken";

    // Request Attributes
    public static final String TOKEN_USER_ATTRIBUTE = "TokenUser";

    // Base Url For Service
    private static final String SERVICE_NAME = SLASH + "rest_service";

    // Resources
    public static final String AUTHENTICATION_RESOURCE = SERVICE_NAME + SLASH + "authentication";
    public static final String REFRESH_RESOURCE = SERVICE_NAME + SLASH + "refresh";
    public static final String USERS_RESOURCE = SERVICE_NAME + SLASH + "users";
    public static final String USER_RESOURCE = USERS_RESOURCE + SLASH + ID_PATH_VARIABLE;
    public static final String USER_LISTINGS_RESOURCE = USER_RESOURCE + SLASH + "listings";
    public static final String LISTINGS_RESOURCE = SERVICE_NAME + SLASH + "listings";
    public static final String LISTING_RESOURCE = LISTINGS_RESOURCE + SLASH + ID_PATH_VARIABLE;
    public static final Integer LISTING_MESSAGE_SUMMARY_DEFAULT_LENGTH = 250;
    public static final String PING_RESOURCE = SERVICE_NAME + SLASH + "ping";

    // Authentication
    public static final String TOKEN_SIGNATURE_KEY = "X317OlbkDHJd9fQadZgRXC7dZtsRIqCKB&y#0sRjqK6uuS1QXF";
    public static final String USER_CREATION_TOKEN = "3D!@Aa@Ltvz^XdC0t5vp9F$qd@dn@V#ExVu#soxY%fp08D1eCC";
    public static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA512";
    public static final String TOKEN_ISSUER = "Weber Classifieds Service";
    public static final String TOKEN_TYPE = "tokenType";
    public static final String TOKEN_TYPE_AUTH = "authorization";
    public static final String TOKEN_TYPE_REFRESH = "refresh";
    public static final String USER_NAME_CLAIM = "userName";
    public static final String ACCESS_LEVEL_CLAIM = "accessLevel";
    public static final int TOKEN_EXPIRATION_MINUTES = 2400;
    public static final int REFRESH_TOKEN_EXPIRATION_MINUTES = TOKEN_EXPIRATION_MINUTES + 10;

    /**
     * Created by samuel on 7/14/17.
     */
    public enum AccessLevel {
        ADMIN, STANDARD, USERCREATE, DISABLED
    }
}
