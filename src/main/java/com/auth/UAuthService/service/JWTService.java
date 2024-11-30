package com.auth.UAuthService.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTService implements CommandLineRunner {

        @Value("${jwt.expiry}") //to read from application.properties file
        private int expiry;

        @Value("${jwt.secret}")
        private String SECRET;


    /**
     * This methos creates a brand new JWT token for us, based on the payload we get from client
     * @return
     */
    private String createToken(Map<String,Object> payload, String userName){
        Date now=new Date();
        Date expiryDate= new Date(now.getTime()+expiry*1000L); //sec->msec

        SecretKey key= Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(userName)
                //.signWith(SignatureAlgorithm.HS256, SECRET)
                .signWith(key)
                .compact(); //create a string output & serializes
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String,Object> mp= new HashMap<>();
        mp.put("name","Shankar");
        mp.put("email","abc@gmail.com");
        mp.put("id","13ABC");
        String token = createToken(mp,"MyName");
        System.out.println("generated Token is :---"+token);
    }

    /*public SecretKey generateSecretKey(){ //One more way to do
        byte[] decode = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(decode);
    }*/
}



/*
*
* After JWT recieved by cleint and sent as a part of the request:
* How to process further that JWT: All those functionality implemented here
* */