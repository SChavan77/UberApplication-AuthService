package com.auth.UAuthService.filters;

import com.auth.UAuthService.service.JWTService;
import com.auth.UAuthService.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    public final RequestMatcher requestMatcher=new AntPathRequestMatcher("/api/v1/auth/validate", HttpMethod.GET.name());

    public JwtAuthFilter(JWTService jwtService, UserDetailsServiceImpl userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       //METHOD 1
        /*String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/v1/auth/signin") || requestURI.startsWith("/api/v1/auth/signup")) {
            filterChain.doFilter(request, response); // Allow request to pass through
            return;
        }*/

        String token=null;
        if(null!=request.getCookies()){
            for(Cookie cookie: request.getCookies()){
                if(cookie.getName().equals("JwtToken")){
                        token=cookie.getValue();
                }
            }
        }
        if(token==null){ //if user not provided any jwtToken, request to be rejected, not move forward
           response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           return;
        }
        //if token is actually present
        String email= jwtService.extractEmail(token);
        if(email!=null){
            UserDetails userDetails=userDetailsService.loadUserByUsername(email);
            if(jwtService.validateToken(token,userDetails.getUsername())){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(userDetails, null); //here, authentication is not required, so only userDetails as principal is passed here.
                //usernamePasswordAuthenticationToken.setDetails(userDetails);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                //asking spring security context to remember the details of already authenticated user further, so that we can access it anywhere at anytime
            }
        }
        System.out.println("Forwarding the request");
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        filterChain.doFilter(request,response); //goes to next filter
    }

    //METHOD 2
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        RequestMatcher matcher= new NegatedRequestMatcher(requestMatcher);
        return matcher.matches(request);
    }
}

/*
Here
UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                        new UsernamePasswordAuthenticationToken(userDetails, null);
 Just to get the internal already implemented few mechanisms in our code. Just to avoid any self authentication.
 Refer it's constructor-
 Only valid token, successful user will have access.
 Only valid token, successful user & valid role will have access.

 We are not doing authentication here.
 Essentially, this wraps the userDetails object into an authentication token.

WebAuthenticationDetailsSource: Helps in converting http request into Spring boot compatiable request
 */