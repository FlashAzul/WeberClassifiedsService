package resource;

/**
 * Created by samuel on 6/22/17.
 * Container For Constants used in Resource classes.
 */
class ResourceConstants {
    private static final String OPEN_BRACE = "{";
    private static final String CLOSE_BRACE = "}";
    private static final String SLASH = "/";

    static final String ID = "id";
    static final String AUTH_TOKEN_HEADER = "AuthToken";
    static final String AUTHORIZATION_REQUEST_HEADER = "Authorization";
    static final String WEBER_CLASSIFIEDS = SLASH + "weberclassifieds";
    static final String USERS_RESOURCE = SLASH + "users";
    static final String ID_PATH_VARIABLE = OPEN_BRACE + ID + CLOSE_BRACE;
    static final String USER_RESOURCE = USERS_RESOURCE + SLASH + ID_PATH_VARIABLE;
    static final String AUTHENTICATION_RESOURCE = SLASH + "authentication";

}
