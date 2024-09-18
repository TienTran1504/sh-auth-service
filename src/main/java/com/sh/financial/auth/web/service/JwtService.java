package com.sh.financial.auth.web.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWKMatcher;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sh.financial.auth.config.JwtConstant;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConstant jwtConstant;
    public JWTClaimsSet decodeJwtToken(String token) throws Exception {

        RSAPublicKey publicKey = convertStringToRSAPublicKey(jwtConstant.getPublicKey());
        // Parse the JWT token
        SignedJWT signedJWT = SignedJWT.parse(token);

        // Create a verifier with the RSA public key
        JWSVerifier verifier = new RSASSAVerifier(publicKey);

        // Verify the token
        if (!signedJWT.verify(verifier)) {
            throw new Exception("JWT verification failed");
        }

        // Return the claims
        return signedJWT.getJWTClaimsSet();
    }

    public RSAPublicKey convertStringToRSAPublicKey(String key) throws Exception {
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