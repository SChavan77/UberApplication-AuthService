package com.auth.UAuthService.controller;

import com.auth.UAuthService.dto.AuthRequestDto;
import com.auth.UAuthService.dto.AuthResponseDto;
import com.auth.UAuthService.dto.RiderDto;
import com.auth.UAuthService.dto.RiderSignUpRequestDto;
import com.auth.UAuthService.service.AuthService;
import com.auth.UAuthService.service.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Value("${cookie.expiry}")
    private int cookieExpiry;


    private AuthService authService;
    private AuthenticationManager authenticationManager;
    private JWTService jwtService;
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    /**
     *
     * We get email & password. With that, we verify whether user is valid or not.
     *
     * @param request
     * @return
     */
    @PostMapping("/signup/rider")
    public ResponseEntity<RiderDto> signUp(@RequestBody RiderSignUpRequestDto request) {
           RiderDto response= authService.signUpRider(request);
           return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @PostMapping("/signin/rider")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response){
        Authentication authentication=authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken
                        (authRequestDto.getEmail(),authRequestDto.getPassword())); //it tells whether the user has the successful login or not
        if(authentication.isAuthenticated()){ //if authenticated, generate token
            /*Map<String, Object> payload=new HashMap<>();
            payload.put("email", authRequestDto.getEmail()); //for demo. Subject also works.
            String jwtToken= jwtService.createToken(payload, authentication.getPrincipal().toString()); //equivalent to authRequestDto.getEmail(). But better to use authenticated object.*/
            String jwtToken= jwtService.createToken(authRequestDto.getEmail());
            ResponseCookie cookie= ResponseCookie.from("JwtToken", jwtToken)
                            .httpOnly(true)
                            .secure(false) //can be sent over http request also
                            .path("/")
                    .sameSite("None")
                    .maxAge(cookieExpiry)
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE,cookie.toString());
            //response.setHeader("custom","123"); //this will come under Header section
           // response.setHeader(HttpHeaders.SET_COOKIE,"value"); //this will show under Cookie section
            //return new ResponseEntity<>("Successful Auth: "+ jwtToken,HttpStatus.CREATED);
            return new ResponseEntity<>(AuthResponseDto.builder().success(true).build(),HttpStatus.CREATED); //after adding token to cookie, remove jwtToken from response.
        }
        else
        {
            throw new UsernameNotFoundException("User not found!");
        }
           // return new ResponseEntity<>("Auth Not Successful",HttpStatus.OK);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(HttpServletRequest request){
        System.out.println("Validate In");
        for(Cookie c: request.getCookies()){
            System.out.println(c.getName()+" "+c.getValue());
        }

        return new ResponseEntity<>("validate",HttpStatus.OK);
    }

}

/**
 * PAYLOAD : was showing like this when we passed Map & authentication.getPrincipal().toString()
 * {
 *   "email": "suma@gmail.com",
 *   "iat": 1733305079,
 *   "exp": 1733308679,
 *   "sub": "Rider(name=null, phoneNumber=null, email=null, password=$2a$10$EDvdIHjucZymHhsoMr2o3OsbxxkIpk9VrNUBm99DA0scnQ9TZGzby, bookings=[])"
 * }
 *
 * PAYLOAD: on passing email param only
 * {
 *   "iat": 1733307370,
 *   "exp": 1733310970,
 *   "sub": "suma@gmail.com"
 * }
 *
 *
 * If need to print the custom message: use try-catch
 * ...
 * throw new UsernameNotFoundException("User not found!");
 * ...
 * catch (Exception ex) {
 *         // Catch authentication failure and return custom message
 *         return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
 *             "error", "User not found!",
 *             "message", ex.getMessage()
 *         ));
 *     }
 *
 * Another way: AuthenticationEntryPoint
 */