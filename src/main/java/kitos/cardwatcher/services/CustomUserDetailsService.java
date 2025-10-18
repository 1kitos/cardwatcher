package kitos.cardwatcher.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import kitos.cardwatcher.entities.User;
import kitos.cardwatcher.entities.UserCredentials;
import kitos.cardwatcher.repositories.UserRepository;
import kitos.cardwatcher.repositories.UserCredentialsRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        UserCredentials credentials = userCredentialsRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Credentials not found for user: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(credentials.getEncryptedPassword())
                .roles("USER") // ou roles vazias se não tens roles
                .build();
    }
}