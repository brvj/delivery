package ftn.sf012018.delivery.security.token;

import ftn.sf012018.delivery.security.CustomPrincipal;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;

import java.util.*;

@Component
public class TokenUtils {
    @Value("myXAuthSecret")
    private String secret;

    @Value("18000") //in seconds (5 hours)
    private Long expiration;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(this.secret)
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date(System.currentTimeMillis()));
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    public String generateToken(UserDetails userDetails) {
        CustomPrincipal principal = (CustomPrincipal) userDetails;
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("sub", principal.getUsername());
        claims.put("id", principal.getId());
        claims.put("username", principal.getUsername());
        claims.put("firstname", principal.getFirstname());
        claims.put("lastname", principal.getLastname());

        List<String> authorities = new ArrayList<>();
        if (principal.isAdmin()){
            authorities.add("ADMIN");
            claims.put("adminId", principal.getAdminId());
        }

        if (principal.isStore()) {
            authorities.add("STORE");
            claims.put("storeId", principal.getStoreId());
        }

        if (principal.isCustomer()) {
            authorities.add("CUSTOMER");
            claims.put("customerId", principal.getCustomerId());
        }

        claims.put("authorities", authorities);

        claims.put("created", new Date(System.currentTimeMillis()));
        return Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}
