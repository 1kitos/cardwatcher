package kitos.cardwatcher.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kitos.cardwatcher.entities.User;
import kitos.cardwatcher.entities.UserCredentials;
import kitos.cardwatcher.repositories.UserCredentialsRepository;
import kitos.cardwatcher.repositories.UserRepository;
import java.util.Optional;

@Service
@Transactional
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserCredentialsRepository credentialsRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtService jwtService;
    
    public boolean registerUser(String username, String password) {
        
        if (userRepository.existsByUsername(username)) {
            return false;
        }
        
        User user = new User();
        user.setUsername(username);
        User savedUser = userRepository.save(user);
        
        // 3. Cria Credenciais
        UserCredentials credentials = new UserCredentials();
        credentials.setUserId(savedUser.getId());
        credentials.setEncryptedPassword(passwordEncoder.encode(password));
        credentialsRepository.save(credentials);
        
        return true;
    }
    
    public boolean validateCredentials(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return false;
        }
        
        Optional<UserCredentials> credentials = credentialsRepository.findByUserId(user.get().getId());
        if (credentials.isEmpty()) {
            return false;
        }
        
        return passwordEncoder.matches(password, credentials.get().getEncryptedPassword());
    }
    
    public Optional<Long> getUserIdFromUsername(String username) {
        return userRepository.findByUsername(username).map(User::getId);
    }
    
    public String generateToken(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return jwtService.generateToken(user);
    }
}
