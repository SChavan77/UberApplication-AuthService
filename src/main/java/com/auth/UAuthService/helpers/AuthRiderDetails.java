package com.auth.UAuthService.helpers;

import com.auth.UAuthService.model.Rider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Spring security works on userDetails polymorphic type for Authentication.
 * So inherited UserDetails.
 * Here, we have implemented authentication against email.
 */
public class AuthRiderDetails extends Rider implements UserDetails  {

    /**
     * this is the unique field. but username is the standard word they use. We can pass email/username or whichever
     * field we consider as unique identifier in our application.
     */
    private String username;

    private String password;

    public AuthRiderDetails(Rider rider) {
        this.username = rider.getEmail();
        this.password = rider.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
