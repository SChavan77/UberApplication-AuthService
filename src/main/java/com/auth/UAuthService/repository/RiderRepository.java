package com.auth.UAuthService.repository;

//import com.auth.UAuthService.model.Rider;
import com.library.models.Rider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiderRepository extends JpaRepository<Rider,Long> {

    Optional<Rider>  findRiderByEmail(String email);

}
