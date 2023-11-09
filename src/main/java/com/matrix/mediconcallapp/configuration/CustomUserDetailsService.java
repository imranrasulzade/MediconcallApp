package com.matrix.mediconcallapp.configuration;

import com.matrix.mediconcallapp.entity.Authority;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.exception.child.UserIsNotActiveException;
import com.matrix.mediconcallapp.enums.UserStatus;
import com.matrix.mediconcallapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow();
        checkUserProfileStatus(user);
        List<String> roles = new ArrayList<>();
        Set<Authority> authorities = user.getAuthorities();
        for (Authority authority : authorities) {
            roles.add(authority.getName());
        }
        UserDetails userDetails;
        userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(roles.toArray(new String[0]))
                .build();
        return userDetails;
    }

        private void checkUserProfileStatus(User user) throws UserIsNotActiveException {
        if(user.getStatus() != UserStatus.ACTIVE){
            throw new UserIsNotActiveException(user.getUsername() + " is not active");
        }
    }
}