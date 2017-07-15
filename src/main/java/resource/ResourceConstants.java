package resource;

/**
 * Created by samuel on 6/22/17.
 * Container For Constants used in Resource classes.
 */
class ResourceConstants {
    // Generic
    private static final String OPEN_BRACE = "{";
    private static final String CLOSE_BRACE = "}";
    private static final String SLASH = "/";

    // Base Url For Service
    static final String SERVICE_NAME = SLASH + "weberclassifieds";

    // Variable Names
    static final String ID = "id";

    // Path Variables
    static final String ID_PATH_VARIABLE = OPEN_BRACE + ID + CLOSE_BRACE;

    // Resources
    static final String AUTHENTICATION_RESOURCE = SERVICE_NAME + SLASH + "authentication";
    static final String USERS_RESOURCE = SERVICE_NAME + SLASH + "users";
    static final String USER_RESOURCE = USERS_RESOURCE + SLASH + ID_PATH_VARIABLE;

    // Headers
    static final String AUTH_TOKEN_HEADER = "AuthToken";
    static final String AUTHORIZATION_REQUEST_HEADER = "Authorization";



}
