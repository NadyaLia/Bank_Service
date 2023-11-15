package com.example.nadyalia.bankservice.user.controllers;

import com.example.nadyalia.bankservice.role.entity.Role;
import com.example.nadyalia.bankservice.role.repository.RoleRepository;
import com.example.nadyalia.bankservice.user.dto.LoginDTO;
import com.example.nadyalia.bankservice.user.dto.SignUpDTO;
import com.example.nadyalia.bankservice.user.entity.User;
import com.example.nadyalia.bankservice.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication controller", description = "This controller manages the initial user/admin registration " +
        "process and handles user authentication. It provides endpoints for registering the first admin user and for " +
        "user login.")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    public AuthController() {

    }

    @PostMapping("/signin")
    @Operation(
            summary = "User Sign-in",
            description = "Authenticates a user and allows sign-in. Returns a success message upon successful authentication."
    )
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);
    }

    @PostMapping("/signup")
    @Operation(
            summary = "Admin registration",
            description = "Registers a new admin user. Registration is restricted if an admin user already exists."
    )
    public ResponseEntity<?> registerUser(@RequestBody SignUpDTO signUpDto){

        if(userRepository.existsByType("admin")){
            return new ResponseEntity<>("Registration is not allowed. An admin user already exists.",
                    HttpStatus.FORBIDDEN);
        }

        /*if(userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }*/


        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(encoder.encode(signUpDto.getPassword()));
        user.setType("admin");

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}