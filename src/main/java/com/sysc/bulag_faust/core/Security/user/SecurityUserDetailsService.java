package com.sysc.bulag_faust.core.Security.user;

import com.sysc.bulag_faust.user.entities.User;
import com.sysc.bulag_faust.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public @NotNull UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        log.debug("Attempting to load user with email: {}", username);
        User user = userRepository.findByEmail(username).orElseThrow(()-> {
            log.warn("User not found with email: {},", username);
           return new UsernameNotFoundException("User not found with email: " + username);

        });
        //TODO: check if user is enable
        log.debug("Successfully loaded user: {}", user.getEmail());
        return SecurityUserDetails.buildUserDetails(user);

    }
}
