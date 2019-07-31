package com.unifun.voice.jwt;
import io.jsonwebtoken.*;
import lombok.NoArgsConstructor;

import javax.xml.bind.DatatypeConverter;
import java.sql.Timestamp;
import java.util.Date;

@NoArgsConstructor
public class TokenManager {
    public boolean verifyIfTokenIsValid(String jwt, String secretKey) {
        Claims claims = decodeJWT(jwt, secretKey);
        if (claims == null) {
            return false;
        }
        return !verifyIfTokenIsExpired(claims);

    }

    private Claims decodeJWT(String jwt, String secretKey) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey((secretKey))
                    .parseClaimsJws(jwt).getBody();
            return claims;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            System.out.println("invalid token");
            return null;
        }
    }

    private boolean verifyIfTokenIsExpired(Claims claim) {
        Date expiration = claim.getExpiration();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        Date currentDate = new Date(ts.getTime());

        if (currentDate.after(expiration)) {
            System.out.println("Expired Token");
            return true;
        }
        return false;
    }
}
