package com.auth.UAuthService.service;

import com.auth.UAuthService.helpers.AuthRiderDetails;
import com.auth.UAuthService.model.Rider;
import com.auth.UAuthService.repository.RiderRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * This class is responsilble for loading the Rider in the form of Userdetails obejct for Auth
 * This helps when the spring wants to fetch the userdetails by a unique identifier (here it is email)
 * email passed through username variable.
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final RiderRepository riderRepository; //To fetch the details from Db: we need RiderRepo

    public UserDetailsServiceImpl(RiderRepository riderRepository) {
        this.riderRepository = riderRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<Rider> rider= riderRepository.findRiderByEmail(username); //here it is email
            if(rider.isPresent())
                return new AuthRiderDetails(rider.get());                 //convert the ojbect rider to userdetails object
            else
                throw new UsernameNotFoundException("No login found for the Rider email! Please try Sign Up");
    }
}