package utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import presentation.UserPresentation;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Calendar;

import static utility.AuthConstants.ACCESS_LEVEL_CLAIM;
import static utility.AuthConstants.HASH_ALGORITHM;
import static utility.AuthConstants.TOKEN_EXPIRATION_MINUTES;
import static utility.AuthConstants.TOKEN_ISSUER;
import static utility.AuthConstants.TOKEN_SIGNATURE_KEY;
import static utility.AuthConstants.USER_NAME_CLAIM;

/**
 * Created by samuel on 7/11/17.
 * Group of helper methods used with Authentication
 */
public class AuthenticationUtils {
    private static final int ITERATIONS = 10;
    private static final int KEY_LENGTH = 10;

    public static String getUserNameFromHeader (String authHeader) {
        String[] authInfo = getAuthInfoFromHeader(authHeader);
        if (authInfo == null || authInfo.length != 2) {
            return null;
        }
        return authInfo[0];
    }

    private static String[] getAuthInfoFromHeader (String authHeader) {
        if (StringUtils.isEmpty(authHeader) || authHeader.split(" ").length != 2) {
            return null;
        }
        try {
            String[] authInfo = (new String(Base64.getDecoder().decode(authHeader.split(" ")[1]), "UTF-8")).split(":");
            if (authInfo.length != 2) {
                return null;
            }
            return authInfo;
        }
        catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static String getUserPasswordFromHeader (String authHeader) {
        String[] authInfo = getAuthInfoFromHeader(authHeader);
        if (authInfo == null || authInfo.length != 2) {
            return null;
        }
        return authInfo[1];
    }

    public static String buildJwtToken (UserPresentation user) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, TOKEN_EXPIRATION_MINUTES);
        return Jwts.builder().setIssuer(TOKEN_ISSUER).claim(USER_NAME_CLAIM, user.getUserName()).claim
                (ACCESS_LEVEL_CLAIM, user.getAccessLevel()).setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(calendar.getTime()).signWith(SignatureAlgorithm.HS256, TOKEN_SIGNATURE_KEY).compact();
    }

    public static String hashPassword (String passwordString, String saltString) {
        try {
            char[] password = passwordString.toCharArray();
            byte[] salt = saltString.getBytes();
            SecretKeyFactory skf = SecretKeyFactory.getInstance(HASH_ALGORITHM);
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            return new String(res);
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }
}
