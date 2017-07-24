package interceptor;

import annotation.AuthorizationRequired;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static application.ApplicationConstants.AUTH_TOKEN_HEADER;
import static application.ApplicationConstants.TOKEN_SIGNATURE_KEY;
import static application.ApplicationConstants.TOKEN_TYPE;
import static application.ApplicationConstants.TOKEN_USER_ATTRIBUTE;
import static application.ApplicationConstants.USER_NAME_CLAIM;

@Component
@RequestMapping("/**")
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws
            Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            if (handlerMethod.getMethod().isAnnotationPresent(AuthorizationRequired.class)) {
                String tokenTypeRequired = handlerMethod.getMethod().getAnnotation(AuthorizationRequired.class)
                        .tokenTypeRequired();

                String token = request.getHeader(AUTH_TOKEN_HEADER);
                if (StringUtils.isEmpty(token)) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authorization containing token " +
                            "required...");
                    return false;
                }
                User user = getUserFromToken(token);
                String tokenType = getTokenTypeFromToken(token);
                if (user == null || tokenType == null || !tokenType.equals(tokenTypeRequired)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "User not authorized to perform action...");
                    return false;
                }
                request.setAttribute(TOKEN_USER_ATTRIBUTE, user);
            }

        }
        return true;
    }

    @Override
    public void postHandle (HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {
        System.out.println("Interceptor post handle");

    }

    @Override
    public void afterCompletion (HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) throws Exception {
        System.out.println("Interceptor after completion");

    }

    private User getUserFromToken (String token) {
        try {
            Jws<Claims> claimsJws = getClaimsFromToken(token);
            String userName = getClaimFromClaims(claimsJws, USER_NAME_CLAIM);
            return userRepository.getByUserName(userName);
        }
        catch (Exception e) {
            return null;
        }
    }

    private String getTokenTypeFromToken (String token) {
        try {
            Jws<Claims> claimsJws = getClaimsFromToken(token);
            return getClaimFromClaims(claimsJws, TOKEN_TYPE);
        }
        catch (Exception e) {
            return null;
        }
    }

    private static Jws<Claims> getClaimsFromToken (String token) {
        return Jwts.parser().setSigningKey(TOKEN_SIGNATURE_KEY).parseClaimsJws(token);
    }

    private static String getClaimFromClaims (Jws<Claims> claimsJws, String claimName) {
        return claimsJws.getBody().get(claimName).toString();
    }

}
