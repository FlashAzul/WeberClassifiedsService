package utility;

/**
 * Created by samuel on 7/11/17.
 * Class containing Constants used by Auth Services
 */
class AuthConstants {
    static String TOKEN_SIGNATURE_KEY = "X317OlbkDHJd9fQadZgRXC7dZtsRIqCKB&y#0sRjqK6uuS1QXF";
    static String HASH_ALGORITHM = "PBKDF2WithHmacSHA512";
    static String TOKEN_ISSUER = "Weber Classifieds Service";
    static String USER_NAME_CLAIM = "userName";
    static String ACCESS_LEVEL_CLAIM = "accessLevel";
    static int TOKEN_EXPIRATION_MINUTES = 30;
}
