package mpti.common.security;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    @Value("${app.auth.tokenSecret:}")
    private String SECRET_KEY;


    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        logger.info("토큰 겁사필터 시작");
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            logger.info("유효한 jwt access tocken 입니다");
            return true;
        } catch (SignatureException ex) {
            logger.error("유효하지 않은 jwt access tocken 서명입니다"); //"Invalid JWT signature"
        } catch (MalformedJwtException ex) {
            logger.error("유효하지 않은 jwt access tocken 입니다"); // Invalid JWT token
        } catch (ExpiredJwtException ex) {
            logger.error("만료된 jwt access tocken 입니다"); // Expired JWT token
        } catch (UnsupportedJwtException ex) {
            logger.error("지원하지 않는 형식의 jwt access tocken 입니다"); // Unsupported JWT token
        } catch (IllegalArgumentException ex) {
            logger.error("jwt access tocken의 claims이 비어 있습니다"); // JWT claims string is empty.
        }
        return false;
    }

}
