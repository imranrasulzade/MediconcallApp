package com.matrix.mediconcallapp.service.utility;

import com.matrix.mediconcallapp.entity.Authority;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.exception.UserNotFoundException;
import com.matrix.mediconcallapp.repository.UserRepository;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private final UserRepository userRepository;

//    @Value("${application.security.jwt.secret-key}")
    private final String secret_key = "QmFzZTY0IGVuY29kaW5nIHNjaGVtZXMgYXJlIGNvbW1vbmx5IHVzZWQgd2hlbiB0aGVyZSBpcyBhIG5lZWQgdG8gZW5jb2RlIGJpbmFyeSBkYXRhLCBlc3BlY2lhbGx5IHdoZW4gdGhhdCBkYXRhIG5lZWRzIHRvIGJlIHN0b3JlZCBhbmQgdHJhbnNmZXJyZWQgb3ZlciBtZWRpYSB0aGF0IGFyZSBkZXNpZ25lZCB0byBkZWFsIHdpdGggdGV4dC4gVGhpcyBlbmNvZGluZyBoZWxwcyB0byBlbnN1cmUgdGhhdCB0aGUgZGF0YSByZW1haW5zIGludGFjdCB3aXRob3V0IG1vZGlmaWNhdGlvbiBkdXJpbmcgdHJhbnNwb3J0LiBCYXNlNjQgaXMgdXNlZCBjb21tb25seSBpbiBhIG51bWJlciBvZiBhcHBsaWNhdGlvbnMgaW5jbHVkaW5nIGVtYWlsIHZpYSBNSU1FLCBhcyB3ZWxsIGFzIHN0b3JpbmcgY29tcGxleCBkYXRh";
//    @Value("${application.security.jwt.expiration}")
    private final long accessTokenValidity = 86400000;
    private final JwtParser jwtParser;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";
    private static Key key;
    public JwtUtil(UserRepository userRepository){
        this.userRepository = userRepository;
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    public Key initializeKey(){
        byte[] keyBytes;
        keyBytes = Decoders.BASE64.decode(secret_key);
        key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }
    public String createToken(User user) {
        key = initializeKey();
        user = userRepository.findByUsername(user.getUsername()).orElseThrow(UserNotFoundException::new);
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        Set<Authority> authorities = user.getAuthorities();
        List<String> roles = new ArrayList<>();
        for (Authority authority : authorities) {
            roles.add(authority.getName());
        }
        Map<String, Object> claimsMap = new HashMap<>();

        claimsMap.put("authorities",roles);
        claimsMap.put("username", user.getUsername());
        claimsMap.put("user_id", user.getId());

        //claimsMap gonderilecek claime. prosta databazadan cekmeliyem useri. cunki bidene username var.
//        claims.put("studentName",student.getName());
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
        final JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(tokenValidity)
                //claims gondermeliyem metodun basinda almaliyam claimleri
                .addClaims(claimsMap)
                .signWith(key, SignatureAlgorithm.HS512);

              return jwtBuilder.compact();
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

    public String getUsername(Claims claims) {
        return claims.getSubject();
    }

    public List<String> getRoles(Claims claims){
        return (List<String>) claims.get("authorities");
    }

    public Integer getUserId(Claims claims){
        return (Integer) claims.get("user_id");
    }


//    public List<String> getRoles2(Claims claims) {
//        Object authoritiesObj = claims.get("authorities");
//        if (authoritiesObj instanceof List<?> authoritiesList) {
//            List<String> roles = new ArrayList<>();
//            for (Object authority : authoritiesList) {
//                if (authority instanceof String) {
//                    roles.add((String) authority);
//                }
//            }
//            return roles;
//        }
//
//        return Collections.emptyList();
//    }

}
