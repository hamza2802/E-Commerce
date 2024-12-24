package com.techlabs.capstone.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techlabs.capstone.entity.User;
import com.techlabs.capstone.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Collect all roles of the user into a set of GrantedAuthority
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            // Assuming the role has a field 'roleName' that holds the role string (e.g., "ROLE_USER")
            authorities.add(new SimpleGrantedAuthority(role.getRole())); 
        });

        // Return a Spring Security User with email, password, and authorities
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
