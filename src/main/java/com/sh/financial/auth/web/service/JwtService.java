package com.sh.financial.auth.web.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.sh.financial.auth.web.utility.JwtConstant;
import io.jsonwebtoken.ExpiredJwtException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sh.financial.auth.exception.AuthAPIException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConstant jwtConstant;
    public JWTClaimsSet decodeJwtToken(String token, String strPublicKey) throws Exception {
        try{
            RSAPublicKey publicKey = convertStringToRSAPublicKey(strPublicKey);
            // Parse the JWT token
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Create a verifier with the RSA public key
            JWSVerifier verifier = new RSASSAVerifier(publicKey);

            // Verify the token
            if (!signedJWT.verify(verifier)) {
                throw new AuthAPIException(HttpStatus.BAD_REQUEST,"JWT verification failed");
            }

            // Return the claims
            return signedJWT.getJWTClaimsSet();
        }
        catch (MalformedJwtException e) {
            throw new AuthAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new AuthAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException e) {
            throw new AuthAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new AuthAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }

    }

    private RSAPublicKey convertStringToRSAPublicKey(String key) throws Exception {
        // Remove any whitespace or newlines
        key = key.replaceAll("\\s+", "");

        // Decode the base64 encoded string
        byte[] keyBytes = Base64.getDecoder().decode(key);

        // Create a KeyFactory for RSA
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        // Create an X509EncodedKeySpec with the decoded key bytes
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // Generate the public key
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }


}