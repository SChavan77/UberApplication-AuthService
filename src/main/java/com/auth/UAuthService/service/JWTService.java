package com.auth.UAuthService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTService /*implements CommandLineRunner*/ {

        @Value("${jwt.expiry}") //to read from application.properties file
        private int expiry;

        @Value("${jwt.secret}")
        private String SECRET;


    /**
     * This methos creates a brand new JWT token for us, based on the payload we get from client
     * @return
     */
    public String createToken(Map<String,Object> payload, String email){
        Date now=new Date();
        Date expiryDate= new Date(now.getTime()+expiry*1000L); //sec->msec

        //SecretKey key= Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(email)
                //.signWith(SignatureAlgorithm.HS256, SECRET)
                .signWith(getSecretKey())
                .compact(); //create a string output & serializes
    }

    //Overriden: Create token with empty hashMap
    public String createToken( String email){
        return createToken(new HashMap<>(),email);
    }


    /*public SecretKey generateSecretKey(){ //One more way to do
        byte[] decode = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(decode);
    }*/


    public Claims extractPayloads(String payload){
       return Jwts
                    .parser()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(payload)
                    .getBody();
    }


    public Key getSecretKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public Boolean validateToken(String token, String email){
        final String userEmailFetchedFromToken =extractEmail(token);
        return (userEmailFetchedFromToken.equals(email) && !isTokenExpired(token));
    }

    public <T> T extractClaims(String token, Function<Claims,T> claimResolver){
        Claims claims= extractPayloads(token);
        return claimResolver.apply(claims); //calls getSubject() internally.

    }

    public Date extractExpirationFromPayload(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    /**
     * This method checks if the token expiry was before the current time stamp or not?
     * @param token JWT Token
     * @return true if token is expired else false
     */
    public boolean isTokenExpired(String token){
            return extractExpirationFromPayload(token).before(new Date());
    }

  //public String extractUserName(String token){
  public String extractEmail(String token){
        return extractClaims(token,Claims::getSubject);
    }

    public String extractPhoneNumber(String token){
        Claims claim=extractPayloads(token);
        String phNumber= (String)claim.get("phoneNumber");
        return phNumber;
    }

    /**
     * More Generic Method to extract particular field k->value
     * @param token Access token received
     * @param payloadKey Which field k
     * @return Values of that payload field
     *
     * For Object type: See Map decl. We can type cast and return as toStrng() also.
     * Change the method type as String that time.
     */
    public Object extractPayloadField(String token, String payloadKey){
        Claims claim=extractPayloads(token);
        return (Object)claim.get(payloadKey);
    }

   /* @Override
    public void run(String... args) throws Exception {
        Map<String,Object> mp= new HashMap<>();
        mp.put("email","abc@gmail.com");
        mp.put("phoneNumber","0123456789");
        mp.put("id","13ABC");
        String token = createToken(mp,"MyName");
        System.out.println("generated Token is :---"+token);
        System.out.println(extractPhoneNumber(token));
    }*/

}

/*
*
* After JWT recieved by cleint and sent as a part of the request:
* How to process further that JWT: All those functionality implemented here
*
*
* Earlier: we made UserName as our Subject. Nowlet's make Email as our subject. And validate our tokens on that basis of email now.
* */